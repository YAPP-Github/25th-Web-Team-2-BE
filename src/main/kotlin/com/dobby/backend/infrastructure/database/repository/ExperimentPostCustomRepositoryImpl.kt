package com.dobby.backend.infrastructure.database.repository

import com.dobby.backend.domain.model.experiment.*
import com.dobby.backend.infrastructure.database.entity.enums.GenderType
import com.dobby.backend.infrastructure.database.entity.enums.MatchType
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area.Companion.isAll
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Region
import com.dobby.backend.infrastructure.database.entity.enums.experiment.RecruitStatus
import com.dobby.backend.infrastructure.database.entity.experiment.*
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
class ExperimentPostCustomRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : ExperimentPostCustomRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findExperimentPostsByCustomFilter(
        customFilter: CustomFilter,
        pagination: Pagination
    ): List<ExperimentPostEntity>? {
        val post = QExperimentPostEntity.experimentPostEntity
        val recruitStatusCondition = when (customFilter.recruitStatus) {
            RecruitStatus.ALL -> null
            RecruitStatus.OPEN -> post.recruitStatus.eq(true)
        }

        return jpaQueryFactory.selectFrom(post)
            .join(post.targetGroup).fetchJoin()
            .join(post.applyMethod).fetchJoin()
            .where(
                matchTypeEq(post, customFilter.matchType),
                genderEq(post, customFilter.studyTarget?.gender),
                ageBetween(post, customFilter.studyTarget?.age),
                regionEq(post, customFilter.locationTarget?.region),
                areasIn(post, customFilter.locationTarget?.areas),
                recruitStatusCondition
            )
            .offset((pagination.page - 1L) * pagination.count)
            .limit(pagination.count.toLong())
            .orderBy(post.createdAt.desc())
            .fetch()
    }

    override fun findExperimentPostsByMemberIdWithPagination(
        memberId: Long,
        pagination: Pagination,
        order: String
    ): List<ExperimentPostEntity>? {
        val post = QExperimentPostEntity.experimentPostEntity

        return jpaQueryFactory.selectFrom(post)
            .join(post.member).fetchJoin()
            .where(post.member.id.eq(memberId))
            .offset((pagination.page - 1L) * pagination.count)
            .limit(pagination.count.toLong())
            .orderBy(getOrderClause(order))
            .fetch()
    }

    override fun countExperimentPostsByCustomFilter(customFilter: CustomFilter): Int {
        val post = QExperimentPostEntity.experimentPostEntity
        val recruitStatusCondition = when (customFilter.recruitStatus) {
            RecruitStatus.ALL -> null
            RecruitStatus.OPEN -> post.recruitStatus.eq(true)
        }

        return jpaQueryFactory.select(post.count())
            .from(post)
            .where(
                matchTypeEq(post, customFilter.matchType),
                genderEq(post, customFilter.studyTarget?.gender),
                ageBetween(post, customFilter.studyTarget?.age),
                regionEq(post, customFilter.locationTarget?.region),
                areasIn(post, customFilter.locationTarget?.areas),
                recruitStatusCondition
            )
            .fetchOne()?.toInt() ?: 0
    }

    private fun matchTypeEq(post: QExperimentPostEntity, matchType: MatchType?): BooleanExpression? {
        return matchType?.let {
            if(it == MatchType.ALL) null else post.matchType.eq(it)
        }
    }

    private fun genderEq(post: QExperimentPostEntity, gender: GenderType?): BooleanExpression? {
        return gender?.let { post.targetGroup.genderType.eq(it) }
    }

    private fun ageBetween(post: QExperimentPostEntity, age: Int?): BooleanExpression? {
        return age?.let {
            post.targetGroup.startAge.loe(it).and(post.targetGroup.endAge.goe(it))
        }
    }

    private fun regionEq(post: QExperimentPostEntity, region: Region?): BooleanExpression? {
        return region?.let { post.region.eq(it) }
    }

    private fun areasIn(post: QExperimentPostEntity, areas: List<Area>?): BooleanExpression? {
        return areas?.takeIf { it.isNotEmpty() && !it.any { area -> area.isAll() } }?.let {
            post.area.`in`(it)
        }
    }

    @Override
    override fun updateExperimentPostStatus(currentDate: LocalDate): Long {
        val experimentPost = QExperimentPostEntity.experimentPostEntity

        return jpaQueryFactory.update(experimentPost)
            .set(experimentPost.recruitStatus, true)
            .where(
                experimentPost.endDate.lt(currentDate)
                    .and(experimentPost.recruitStatus.eq(false))
            )
            .execute()
    }

    private fun getOrderClause(order: String): OrderSpecifier<*> {
        val post = QExperimentPostEntity.experimentPostEntity
        return if (order == "ASC") {
            post.createdAt.asc()
        } else {
            post.createdAt.desc()
        }
    }

    override fun updateExperimentPost(experimentPost: ExperimentPost): ExperimentPost {
        val qExperimentPost = QExperimentPostEntity.experimentPostEntity
        val qApplyMethod = QApplyMethodEntity.applyMethodEntity
        val qTargetGroup = QTargetGroupEntity.targetGroupEntity

       jpaQueryFactory.update(qExperimentPost)
            .set(qExperimentPost.title, experimentPost.title)
            .set(qExperimentPost.reward, experimentPost.reward)
            .set(qExperimentPost.startDate, experimentPost.startDate)
            .set(qExperimentPost.endDate, experimentPost.endDate)
            .set(qExperimentPost.content, experimentPost.content)
            .set(qExperimentPost.count, experimentPost.count)
            .set(qExperimentPost.leadResearcher, experimentPost.leadResearcher)
            .set(qExperimentPost.detailedAddress, experimentPost.detailedAddress)
            .set(qExperimentPost.matchType, experimentPost.matchType)
            .set(qExperimentPost.univName, experimentPost.univName)
            .set(qExperimentPost.region, experimentPost.region)
            .set(qExperimentPost.area, experimentPost.area)
            .set(qExperimentPost.updatedAt, LocalDateTime.now())
            .where(qExperimentPost.id.eq(experimentPost.id))
            .execute()

        jpaQueryFactory.update(qApplyMethod)
            .set(qApplyMethod.content, experimentPost.applyMethod.content)
            .set(qApplyMethod.formUrl, experimentPost.applyMethod.formUrl)
            .set(qApplyMethod.phoneNum, experimentPost.applyMethod.phoneNum)
            .where(qApplyMethod.id.eq(experimentPost.applyMethod.id))
            .execute()

        jpaQueryFactory.update(qTargetGroup)
            .set(qTargetGroup.startAge, experimentPost.targetGroup.startAge)
            .set(qTargetGroup.endAge, experimentPost.targetGroup.endAge)
            .set(qTargetGroup.genderType, experimentPost.targetGroup.genderType)
            .set(qTargetGroup.otherCondition, experimentPost.targetGroup.otherCondition)
            .where(qTargetGroup.id.eq(experimentPost.targetGroup.id))
            .execute()

        updateImages(experimentPostId = experimentPost.id, experimentPost.images)

        val updatedEntity = jpaQueryFactory.selectFrom(qExperimentPost)
            .where(qExperimentPost.id.eq(experimentPost.id))
            .fetchOne() ?: throw IllegalStateException("ExperimentPost not found after update")

        return updatedEntity.toDomain()
    }

    private fun updateImages(experimentPostId: Long, images: List<ExperimentImage>) {
        val experimentPost = entityManager.find(ExperimentPostEntity::class.java, experimentPostId)
            ?: throw IllegalStateException("ExperimentPost not found")

        jpaQueryFactory.delete(QExperimentImageEntity.experimentImageEntity)
            .where(QExperimentImageEntity.experimentImageEntity.experimentPost.id.eq(experimentPostId))
            .execute()

        images.forEach { image ->
            val imageEntity = ExperimentImageEntity(
                id = 0L,
                imageUrl = image.imageUrl,
                experimentPost = experimentPost
            )
            experimentPost.addImage(imageEntity)
            entityManager.persist(imageEntity)
        }
        entityManager.merge(experimentPost)
    }
}
