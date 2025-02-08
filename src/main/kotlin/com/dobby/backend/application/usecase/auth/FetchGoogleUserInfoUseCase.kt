package com.dobby.backend.application.usecase.auth

import com.dobby.backend.application.usecase.UseCase
import com.dobby.backend.domain.exception.MemberRoleMismatchException
import com.dobby.backend.domain.gateway.member.MemberGateway
import com.dobby.backend.domain.gateway.auth.TokenGateway
import com.dobby.backend.domain.gateway.auth.GoogleAuthGateway
import com.dobby.backend.infrastructure.database.entity.enums.member.MemberStatus
import com.dobby.backend.infrastructure.database.entity.enums.member.ProviderType
import com.dobby.backend.infrastructure.database.entity.enums.member.RoleType

class FetchGoogleUserInfoUseCase(
    private val googleAuthGateway: GoogleAuthGateway,
    private val memberGateway: MemberGateway,
    private val jwtTokenGateway: TokenGateway
) : UseCase<FetchGoogleUserInfoUseCase.Input, FetchGoogleUserInfoUseCase.Output> {

    data class Input(
        val authorizationCode: String,
        val role: RoleType
    )

    data class Output(
        val isRegistered: Boolean,
        val accessToken: String?,
        val refreshToken: String?,
        val memberId: String?,
        val name: String?,
        val oauthEmail: String,
        val role: RoleType?,
        val provider: ProviderType,
        val contactEmail: String?
    )

    override fun execute(input: Input): Output {
        val oauthToken = googleAuthGateway.getAccessToken(input.authorizationCode).accessToken
        val userInfo = googleAuthGateway.getUserInfo(oauthToken)
        val email = userInfo.email
        val member = email.let { memberGateway.findByOauthEmailAndStatus(it, MemberStatus.ACTIVE) }

        return if (member != null) {
            val jwtAccessToken = jwtTokenGateway.generateAccessToken(member)
            val jwtRefreshToken = jwtTokenGateway.generateRefreshToken(member)

            if(member.role != input.role)
                throw MemberRoleMismatchException(member.role.toString())

            Output(
                isRegistered = true,
                accessToken = jwtAccessToken,
                refreshToken = jwtRefreshToken,
                memberId = member.id,
                name = member.name,
                oauthEmail = member.oauthEmail,
                role = member.role,
                provider = ProviderType.GOOGLE,
                contactEmail = member.contactEmail
            )
        } else {
            // 등록된 멤버가 없으면 isRegistered = false, memberId = null
            Output(
                isRegistered = false,
                accessToken = null,
                refreshToken = null,
                memberId = null,
                name = null,
                oauthEmail = email,
                role = null,
                provider = ProviderType.GOOGLE,
                contactEmail = null
            )
        }
    }
}
