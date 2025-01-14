package com.dobby.backend.infrastructure.database.entity.member

import com.dobby.backend.domain.model.member.Participant
import com.dobby.backend.infrastructure.database.entity.enum.GenderType
import com.dobby.backend.infrastructure.database.entity.enum.MatchType
import com.dobby.backend.infrastructure.database.entity.enum.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enum.areaInfo.Region
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "participant")
class ParticipantEntity (
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "member_id", nullable = false)
    val member: MemberEntity,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "participant_id")
    val id: Long,

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: GenderType,

    @Column(name = "birth_date", nullable = false)
    val birthDate : LocalDate,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "region", column = Column(name = "basic_region", nullable = false)),
        AttributeOverride(name = "area", column = Column(name = "basic_area", nullable = false))
    )
    val basicAddressInfo: AddressInfo,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "region", column = Column(name = "optional_region", nullable = true)),
        AttributeOverride(name = "area", column = Column(name = "optional_area", nullable = true))
    )
    val additionalAddressInfo: AddressInfo?,

    @Column(name = "prefer_type", nullable = true)
    @Enumerated(EnumType.STRING)
    var preferType: MatchType?,
) {

    fun toDomain() = Participant(
        id = id,
        member = member.toDomain(),
        gender = gender,
        birthDate = birthDate,
        basicAddressInfo = basicAddressInfo.toDomain(),
        additionalAddressInfo = additionalAddressInfo?.toDomain(),
        preferType = preferType
    )

    companion object {
        fun fromDomain(participant: Participant) = with(participant) {
            ParticipantEntity(
                id = id,
                member = MemberEntity.fromDomain(member),
                gender = gender,
                birthDate = birthDate,
                basicAddressInfo = AddressInfo.fromDomain(basicAddressInfo), // 수정: AddressInfo.fromDomain() 사용
                additionalAddressInfo = additionalAddressInfo?.let { AddressInfo.fromDomain(it) }, // 수정
                preferType = preferType
            )
        }
    }
}

@Embeddable
data class AddressInfo(
    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    val region: Region,

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false)
    val area: Area
) {
    fun toDomain() = Participant.AddressInfo(
        region = region,
        area = area
    )

    companion object {
        fun fromDomain(addressInfo: Participant.AddressInfo) = AddressInfo(
            region = addressInfo.region,
            area = addressInfo.area
        )
    }
}
