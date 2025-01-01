package com.dobby.backend.domain.gateway

import com.dobby.backend.domain.model.Member

interface TokenGateway {
    fun generateAccessToken(member: Member): String
    fun generateRefreshToken(member: Member): String
    fun generateAccessTokenForTestMember(memberId: Long): String
    fun generateRefreshTokenForTestMember(memberId: Long): String
    fun extractMemberIdFromRefreshToken(token: String): String
}
