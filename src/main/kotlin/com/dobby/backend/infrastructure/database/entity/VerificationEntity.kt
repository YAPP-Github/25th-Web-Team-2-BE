package com.dobby.backend.infrastructure.database.entity

import com.dobby.backend.domain.model.Verification
import com.dobby.backend.infrastructure.database.entity.enums.VerificationStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "verification")
class VerificationEntity (
    @Id
    @Column(name = "verification_id", columnDefinition = "CHAR(13)")
    val id: String,

    @Column(name = "univ_email", nullable = false, unique = true)
    val univEmail : String,

    @Column(name = "verification_code", length = 6, nullable = false, unique = true)
    var verificationCode: String,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: VerificationStatus= VerificationStatus.HOLD,

    @Column(name = "expires_at", nullable = false)
    var expiresAt : LocalDateTime ? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime ? = null,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime ? = null,
) {
    @PrePersist
    fun prePersist() {
        createdAt ?: LocalDateTime.now()
        updatedAt ?: LocalDateTime.now()
        if(expiresAt == null)
            expiresAt = LocalDateTime.now().plusMinutes(10)
    }

    fun toDomain() : Verification {
        return Verification(
            id = id,
            univEmail = univEmail,
            verificationCode = verificationCode,
            status = status,
            expiresAt = expiresAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    companion object {
        fun fromDomain(model: Verification) = with(model) {
            VerificationEntity(
                id =  id,
                univEmail = univEmail,
                verificationCode = verificationCode,
                status = status,
                expiresAt = expiresAt,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
