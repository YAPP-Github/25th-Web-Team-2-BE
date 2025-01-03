package com.dobby.backend.infrastructure.database.entity

import com.dobby.backend.infrastructure.database.entity.enum.GenderType
import com.dobby.backend.infrastructure.database.entity.enum.MatchType
import com.dobby.backend.infrastructure.database.entity.enum.ProviderType
import com.dobby.backend.infrastructure.database.entity.enum.RoleType
import com.dobby.backend.infrastructure.database.entity.enum.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enum.areaInfo.Region
import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "participant")
@DiscriminatorValue("PARTICIPANT")
<<<<<<< HEAD:src/main/kotlin/com/dobby/backend/infrastructure/database/entity/ParticipantEntity.kt
class ParticipantEntity (
=======
class Participant (
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

>>>>>>> dc4d52e ([YS-31] feat: 구글 OAuth 로그인 구현 (#13)):src/main/kotlin/com/dobby/backend/infrastructure/database/entity/Participant.kt
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: GenderType,

    @Column(name = "basic_region", nullable = false)
    @Enumerated(EnumType.STRING)
    var basicRegion: Region,

    @Column(name = "basic_area", nullable = false)
    @Enumerated(EnumType.STRING)
    var basicArea : Area,

    @Column(name = "optional_region", nullable = true)
    @Enumerated(EnumType.STRING)
    var optionalRegion: Region?,

    @Column(name = "optional_area", nullable = true)
    @Enumerated(EnumType.STRING)
    var optionalArea: Area?,

    @Column(name = "prefer_type", nullable = true)
    @Enumerated(EnumType.STRING)
    var preferType: MatchType?,

    id: Long,
    oauthEmail: String,
    provider: ProviderType,
    contactEmail: String,
    name: String,
    birthDate: LocalDate
) : MemberEntity(
    id= id,
    oauthEmail = oauthEmail,
    provider = provider,
    contactEmail= contactEmail,
    role = RoleType.PARTICIPANT,
    name = name,
    birthDate = birthDate
)
