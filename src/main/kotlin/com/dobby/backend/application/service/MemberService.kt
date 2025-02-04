package com.dobby.backend.application.service

import com.dobby.backend.application.usecase.member.*
import com.dobby.backend.domain.exception.SignupOauthEmailDuplicateException
import com.dobby.backend.domain.gateway.member.MemberGateway
import com.dobby.backend.infrastructure.database.entity.enums.MemberStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberGateway: MemberGateway,
    private val createParticipantUseCase: CreateParticipantUseCase,
    private val createResearcherUseCase: CreateResearcherUseCase,
    private val verifyResearcherEmailUseCase: VerifyResearcherEmailUseCase,
    private val getResearcherInfoUseCase: GetResearcherInfoUseCase,
    private val getParticipantInfoUseCase: GetParticipantInfoUseCase,
    private val updateResearcherInfoUseCase: UpdateResearcherInfoUseCase,
    private val updateParticipantInfoUseCase: UpdateParticipantInfoUseCase
) {
    @Transactional
    fun participantSignup(input: CreateParticipantUseCase.Input): CreateParticipantUseCase.Output {
           return createParticipantUseCase.execute(input)
    }

    @Transactional
    fun researcherSignup(input: CreateResearcherUseCase.Input) : CreateResearcherUseCase.Output{
        val existingMember = memberGateway.findByOauthEmailAndStatus(input.oauthEmail, MemberStatus.ACTIVE)
        if (existingMember!= null) throw SignupOauthEmailDuplicateException

        verifyResearcherEmailUseCase.execute(input.univEmail)
        return createResearcherUseCase.execute(input)
    }

    @Transactional
    fun getResearcherInfo(input: GetResearcherInfoUseCase.Input): GetResearcherInfoUseCase.Output {
        return getResearcherInfoUseCase.execute(input)
    }

    @Transactional
    fun getParticipantInfo(input: GetParticipantInfoUseCase.Input): GetParticipantInfoUseCase.Output {
        return getParticipantInfoUseCase.execute(input)
    }

    @Transactional
    fun updateResearcherInfo(input: UpdateResearcherInfoUseCase.Input): UpdateResearcherInfoUseCase.Output {
        return updateResearcherInfoUseCase.execute(input)
    }

    @Transactional
    fun updateParticipantInfo(input: UpdateParticipantInfoUseCase.Input): UpdateParticipantInfoUseCase.Output {
        return updateParticipantInfoUseCase.execute(input)
    }
}
