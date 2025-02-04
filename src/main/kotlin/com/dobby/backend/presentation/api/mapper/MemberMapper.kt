package com.dobby.backend.presentation.api.mapper

import com.dobby.backend.application.usecase.member.*
import com.dobby.backend.domain.model.member.Participant
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Region
import com.dobby.backend.presentation.api.dto.request.member.ParticipantSignupRequest
import com.dobby.backend.presentation.api.dto.request.member.ResearcherSignupRequest
import com.dobby.backend.presentation.api.dto.request.member.UpdateParticipantInfoRequest
import com.dobby.backend.presentation.api.dto.request.member.UpdateResearcherInfoRequest
import com.dobby.backend.presentation.api.dto.response.member.*
import com.dobby.backend.util.getCurrentMemberId

object MemberMapper {
    fun toCreateResearcherInput(req: ResearcherSignupRequest) : CreateResearcherUseCase.Input{
        return CreateResearcherUseCase.Input(
            oauthEmail = req.oauthEmail,
            provider = req.provider,
            contactEmail = req.contactEmail,
            univEmail = req.univEmail,
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
            additionalAddressInfo = CreateParticipantUseCase.AddressInfo(
                region = req.additionalAddressInfo?.region ?: Region.NONE,
                area = req.additionalAddressInfo?.area ?: Area.NONE
            ),
            matchType = req.matchType
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
            memberInfo = MemberResponse.fromDomain(response.member),
            univEmail = response.univEmail,
            univName = response.univName,
            major = response.major,
            labInfo = response.labInfo,
        )
    }

    fun toGetParticipantInfoUseCaseInput(): GetParticipantInfoUseCase.Input {
        return GetParticipantInfoUseCase.Input(
            memberId = getCurrentMemberId()
        )
    }

    fun toParticipantInfoResponse(output: GetParticipantInfoUseCase.Output): ParticipantInfoResponse {
        return ParticipantInfoResponse(
            memberInfo = MemberResponse.fromDomain(output.member),
            gender = output.gender,
            birthDate = output.birthDate,
            basicAddressInfo = AddressInfoResponse.fromDomain(output.basicAddressInfo),
            additionalAddressInfo = output.additionalAddressInfo?.let { AddressInfoResponse.fromDomain(it) },
            matchType = output.matchType
        )
    }

    fun toUpdateResearcherInfoUseCaseInput(request: UpdateResearcherInfoRequest): UpdateResearcherInfoUseCase.Input {
        return UpdateResearcherInfoUseCase.Input(
            memberId = getCurrentMemberId(),
            contactEmail = request.contactEmail,
            name = request.name,
            univName = request.univName,
            major = request.major,
            labInfo = request.labInfo
        )
    }

    fun toResearcherInfoResponse(response: UpdateResearcherInfoUseCase.Output): ResearcherInfoResponse {
        return ResearcherInfoResponse(
            memberInfo = MemberResponse.fromDomain(response.member),
            univEmail = response.univEmail,
            univName = response.univName,
            major = response.major,
            labInfo = response.labInfo,
        )
    }

    fun toUpdateParticipantInfoUseCaseInput(request: UpdateParticipantInfoRequest): UpdateParticipantInfoUseCase.Input {
        return UpdateParticipantInfoUseCase.Input(
            memberId = getCurrentMemberId(),
            contactEmail = request.contactEmail,
            name = request.name,
            basicAddressInfo = Participant.AddressInfo(
                region = request.basicAddressInfo.region,
                area = request.basicAddressInfo.area
            ),
            additionalAddressInfo = Participant.AddressInfo(
                region = request.additionalAddressInfo?.region ?: Region.NONE,
                area = request.additionalAddressInfo?.area ?: Area.NONE
            ),
            matchType = request.matchType
        )
    }

    fun toParticipantInfoResponse(output: UpdateParticipantInfoUseCase.Output): ParticipantInfoResponse {
        return ParticipantInfoResponse(
            memberInfo = MemberResponse.fromDomain(output.member),
            gender = output.gender,
            birthDate = output.birthDate,
            basicAddressInfo = AddressInfoResponse.fromDomain(output.basicAddressInfo),
            additionalAddressInfo = output.additionalAddressInfo?.let { AddressInfoResponse.fromDomain(it) },
            matchType = output.matchType
        )
    }
}
