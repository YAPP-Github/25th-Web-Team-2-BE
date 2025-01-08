package com.dobby.backend.presentation.api.dto.response

import com.dobby.backend.domain.model.member.Member
import com.dobby.backend.infrastructure.database.entity.enum.ProviderType
import com.dobby.backend.infrastructure.database.entity.enum.RoleType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자 DTO")
data class MemberResponse(
    @Schema(description = "사용자 ID", example = "1")
    val memberId: Long?,

    @Schema(description = "이름", example = "야뿌")
    val name: String?,

    @Schema(description = "OAuth 이메일", example = "dlawotn3@naver.com")
    val oauthEmail: String?,

    @Schema(description = "OAuth 제공자", example = "GOOGLE")
    val provider: ProviderType?,

    @Schema(description = "역할", example = "RESEARCHER")
    val role: RoleType?,
) {

    companion object {
        fun fromDomain(member: Member): MemberResponse = with(member) {
            MemberResponse(
                memberId = memberId,
                oauthEmail = oauthEmail,
                provider = provider,
                role = role,
                name = name
            )
        }
    }
}
