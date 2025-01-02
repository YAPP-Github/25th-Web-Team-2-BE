package com.dobby.backend.domain.usecase

import com.dobby.backend.domain.gateway.TokenGateway

class GenerateTestToken(
    private val tokenGateway: TokenGateway
) : UseCase<GenerateTestToken.Input, GenerateTestToken.Output> {
    data class Input(
        val memberId: Long
    )

    data class Output(
        val accessToken: String,
        val refreshToken: String,
    )

    override fun execute(input: Input): Output {
        val memberId = input.memberId
        return Output(
            accessToken = tokenGateway.generateAccessToken(memberId),
            refreshToken = tokenGateway.generateRefreshToken(memberId)
        )
    }
}
