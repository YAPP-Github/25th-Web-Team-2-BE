package com.dobby.backend.domain.model.experiment

import com.dobby.backend.domain.model.member.Member
import com.dobby.backend.infrastructure.database.entity.enums.MatchType
import com.dobby.backend.infrastructure.database.entity.enums.TimeSlot
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Region
import java.time.LocalDate
import java.time.LocalDateTime

data class ExperimentPost(
    val id: Long,
    val member: Member,
    val targetGroup: TargetGroup,
    val applyMethod: ApplyMethod,
    var views: Int,
    var title: String,
    var content: String,
    var leadResearcher: String,
    var reward: String,
    var startDate: LocalDate?,
    var endDate: LocalDate?,
    var timeRequired: TimeSlot?,
    var count: Int,
    var matchType: MatchType,
    var univName: String,
    var region: Region,
    var area: Area,
    var detailedAddress: String?,
    val alarmAgree: Boolean,
    var recruitStatus: Boolean,
    var images: MutableList<ExperimentImage>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) {
    fun incrementViews() {
        this.views += 1
        this.updatedAt = LocalDateTime.now()
    }

    fun updateRecruitStatus(
        recruitStatus: Boolean = this.recruitStatus,
        updatedAt: LocalDateTime = this.updatedAt
    ): ExperimentPost {
        return this.copy(
            recruitStatus = recruitStatus,
            updatedAt = updatedAt
        )
    }

    fun update(
        title: String?,
        reward: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        content: String?,
        count: Int?,
        leadResearcher: String?,
        detailedAddress: String?,
        matchType: MatchType?,
        univName: String?,
        region: Region?,
        area: Area?,
        imageListInfo: List<String>?
    ) {
        title?.let { this.title = it }
        reward?.let { this.reward = it }
        startDate?.let { this.startDate = it }
        endDate?.let { this.endDate = it }
        content?.let { this.content = it }
        count?.let { this.count = it }
        leadResearcher?.let { this.leadResearcher = it }
        detailedAddress?.let { this.detailedAddress = it }
        matchType?.let { this.matchType = it }
        univName?.let { this.univName = it }
        region?.let { this.region = it }
        area?.let { this.area = it }

        imageListInfo?.let {
            val newImages = it.map { imageUrl ->
                val existingImage = this.images.find { existing -> existing.imageUrl == imageUrl }
                ExperimentImage(
                    id = existingImage?.id ?: 0L,
                    experimentPost = this,
                    imageUrl = imageUrl
                )
            }
            this.updateImages(newImages)
        }
    }

    fun updateImages(newImages: List<ExperimentImage>) {
        images.clear()
        images.addAll(newImages)
        updatedAt = LocalDateTime.now()
    }


    companion object {
        fun newExperimentPost(
            id: Long,
            member: Member,
            targetGroup: TargetGroup,
            applyMethod: ApplyMethod,
            views: Int,
            title: String,
            content: String,
            leadResearcher: String,
            reward: String,
            startDate: LocalDate?,
            endDate: LocalDate?,
            timeRequired: TimeSlot?,
            count: Int,
            matchType: MatchType,
            univName: String,
            region: Region,
            area: Area,
            detailedAddress: String,
            alarmAgree: Boolean,
            recruitStatus: Boolean,
            images: List<ExperimentImage> = listOf(),
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now()
        ) = ExperimentPost(
            id = id,
            member = member,
            targetGroup = targetGroup,
            applyMethod = applyMethod,
            views = views,
            title = title,
            content = content,
            leadResearcher = leadResearcher,
            reward = reward,
            startDate = startDate,
            endDate = endDate,
            timeRequired = timeRequired,
            count = count,
            matchType = matchType,
            univName = univName,
            region = region,
            area = area,
            detailedAddress = detailedAddress,
            alarmAgree = alarmAgree,
            recruitStatus = recruitStatus,
            images = images.toMutableList(),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
