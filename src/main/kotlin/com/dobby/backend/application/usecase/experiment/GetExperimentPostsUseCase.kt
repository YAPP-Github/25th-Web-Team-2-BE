package com.dobby.backend.application.usecase.experiment

import com.dobby.backend.application.mapper.ExperimentMapper
import com.dobby.backend.application.usecase.UseCase
import com.dobby.backend.domain.model.experiment.CustomFilter
import com.dobby.backend.domain.gateway.experiment.ExperimentPostGateway
import com.dobby.backend.domain.model.experiment.LocationTarget
import com.dobby.backend.domain.model.experiment.Pagination
import com.dobby.backend.infrastructure.database.entity.enums.GenderType
import com.dobby.backend.infrastructure.database.entity.enums.MatchType
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Region
import java.time.LocalDate

class GetExperimentPostsUseCase (
    private val experimentPostGateway: ExperimentPostGateway
): UseCase<GetExperimentPostsUseCase.Input, List<GetExperimentPostsUseCase.Output>>{
    data class Input (
        val customFilter: CustomFilterInput,
        val pagination: PaginationInput
    )
    data class CustomFilterInput(
        val matchType : MatchType?,
        val studyTarget: StudyTargetInput?,
        val locationTarget: LocationTargetInput?,
        val recruitDone: Boolean?,
    )

    data class StudyTargetInput(
        val gender : GenderType?,
        val age: Int?
    )

    data class LocationTargetInput(
        val region: Region?,
        val areas: List<Area>?,
    )

    data class PaginationInput(
        val page: Int = 1,
        val count: Int = 6,
    )

    data class Output(
        val postInfo: PostInfoOutput
    )

    data class PostInfoOutput(
        val postId: Long,
        val title: String,
        val views: Int,
        val univName: String,
        val reward: String,
        val recruitDone: Boolean,
        val durationInfo: DurationInfoOutput
    )

    data class DurationInfoOutput(
        val startDate: LocalDate?,
        val endDate: LocalDate?
    )

    override fun execute(input: Input) : List<Output> {
        val posts = experimentPostGateway.findExperimentPostsByCustomFilter(
            ExperimentMapper.toDomainFilter(input.customFilter),
            ExperimentMapper.toDomainPagination(input.pagination)
        )

        return posts?.map { post ->
            Output(
                postInfo = PostInfoOutput(
                    postId = post.id,
                    title = post.title,
                    views = post.views,
                    univName = post.univName,
                    reward = post.reward,
                    recruitDone = post.recruitDone,
                    durationInfo = DurationInfoOutput(
                        startDate = post.startDate,
                        endDate = post.endDate
                    )
                )
            )
        } ?: emptyList()
    }

}
