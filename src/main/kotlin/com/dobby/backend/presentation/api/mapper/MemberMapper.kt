package com.dobby.backend.presentation.api.mapper

import com.dobby.backend.application.usecase.member.GetResearcherInfoUseCase
import com.dobby.backend.application.usecase.member.CreateResearcherUseCase
import com.dobby.backend.application.usecase.member.CreateParticipantUseCase
import com.dobby.backend.application.usecase.member.GetParticipantInfoUseCase
import com.dobby.backend.presentation.api.dto.request.signup.ParticipantSignupRequest
import com.dobby.backend.presentation.api.dto.request.signup.ResearcherSignupRequest
import com.dobby.backend.presentation.api.dto.response.member.*
import com.dobby.backend.util.getCurrentMemberId

object MemberMapper {
    fun toCreateResearcherInput(req: ResearcherSignupRequest) : CreateResearcherUseCase.Input{
        return CreateResearcherUseCase.Input(
            oauthEmail = req.oauthEmail,
            provider = req.provider,
            contactEmail = req.contactEmail,
            univEmail = req.univEmail,
            emailVerified = req.emailVerified,
            univName = req.univName,
            name = req.name,
            major = req.major,
            labInfo = req.labInfo
        )
    }

    fun toResearcherSignupResponse(output: CreateResearcherUseCase.Output): SignupResponse {
        return SignupResponse(
            accessToken = output.accessToken,
            refreshToken = output.refreshToken,
            memberInfo = toMemberResDto(output.memberInfo)
        )
    }

    fun toCreateParticipantInput(req: ParticipantSignupRequest): CreateParticipantUseCase.Input {
        return CreateParticipantUseCase.Input(
            oauthEmail = req.oauthEmail,
            provider = req.provider,
            contactEmail = req.contactEmail,
            name = req.name,
            gender = req.gender,
            birthDate = req.birthDate,
            basicAddressInfo = CreateParticipantUseCase.AddressInfo(
                region = req.basicAddressInfo.region,
                area = req.basicAddressInfo.area
            ),
            additionalAddressInfo = req.additionalAddressInfo?.let {
                CreateParticipantUseCase.AddressInfo(region = it.region, area = it.area)
            },
            preferType = req.preferType
        )
    }

    fun toParticipantSignupResponse(output: CreateParticipantUseCase.Output): SignupResponse {
        return SignupResponse(
            accessToken = output.accessToken,
            refreshToken = output.refreshToken,
            memberInfo = toMemberResDto(output.memberInfo)
        )
    }

    private fun toMemberResDto(input: Any): MemberResponse {
        return when (input) {
            is CreateResearcherUseCase.MemberResponse -> {
                MemberResponse(
                    memberId = input.memberId,
                    name = input.name,
                    provider = input.provider,
                    role = input.role,
                    oauthEmail = input.oauthEmail
                )
            }
            is CreateParticipantUseCase.MemberResponse -> {
                MemberResponse(
                    memberId = input.memberId,
                    name = input.name,
                    provider = input.provider,
                    role = input.role,
                    oauthEmail = input.oauthEmail
                )
            }
            else -> throw IllegalArgumentException("Unsupported MemberResponse type")
        }
    }

    fun toGetResearcherInfoUseCaseInput(): GetResearcherInfoUseCase.Input {
        return GetResearcherInfoUseCase.Input(
            memberId = getCurrentMemberId()
        )
    }

    fun toResearcherInfoResponse(response: GetResearcherInfoUseCase.Output): ResearcherInfoResponse {
        return ResearcherInfoResponse(
            leadResearcher = response.leadResearcher,
            univName = response.univName
        )
    }

    fun toGetParticipantInfoUseCaseInput(memberId: Long): GetParticipantInfoUseCase.Input {
        return GetParticipantInfoUseCase.Input(
            memberId = memberId,
            loginMemberId = getCurrentMemberId()
        )
    }

    fun toParticipantInfoResponse(output: GetParticipantInfoUseCase.Output): ParticipantInfoResponse {
        return ParticipantInfoResponse(
            memberInfo = MemberResponse.fromDomain(output.member),
            gender = output.gender,
            birthDate = output.birthDate,
            basicAddressInfo = AddressInfoResponse.fromDomain(output.basicAddressInfo),
            additionalAddressInfo = output.additionalAddressInfo?.let { AddressInfoResponse.fromDomain(it) },
            matchType = output.preferType
        )
    }
}
