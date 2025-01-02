package com.dobby.backend.application.service

import com.dobby.backend.application.usecase.FetchGoogleUserInfoUseCase
import com.dobby.backend.presentation.api.dto.request.OauthLoginRequest
import com.dobby.backend.presentation.api.dto.response.OauthLoginResponse
import org.springframework.stereotype.Service

@Service
class OauthService(
    private val fetchGoogleUserInfoUseCase: FetchGoogleUserInfoUseCase
) {
    fun getGoogleUserInfo(oauthLoginRequest: OauthLoginRequest): OauthLoginResponse {
        return fetchGoogleUserInfoUseCase.execute(oauthLoginRequest)
    }

}