package com.dobby.backend.infrastructure.database.repository

import com.dobby.backend.domain.model.experiment.CustomFilter
import com.dobby.backend.domain.model.experiment.Pagination
import com.dobby.backend.infrastructure.database.entity.enums.GenderType
import com.dobby.backend.infrastructure.database.entity.enums.MatchType
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Area.Companion.isAll
import com.dobby.backend.infrastructure.database.entity.enums.areaInfo.Region
import com.dobby.backend.infrastructure.database.entity.experiment.ExperimentPostEntity
import com.dobby.backend.infrastructure.database.entity.experiment.QExperimentPostEntity
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ExperimentPostCustomRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory
) : ExperimentPostCustomRepository {
    override fun findExperimentPostsByCustomFilter(
        customFilter: CustomFilter,
        pagination: Pagination
    ): List<ExperimentPostEntity>? {
        val post = QExperimentPostEntity.experimentPostEntity

        return jpaQueryFactory.selectFrom(post)
            .join(post.targetGroup).fetchJoin()
            .join(post.applyMethod).fetchJoin()
            .where(
                matchTypeEq(post, customFilter.matchType),
                genderEq(post, customFilter.studyTarget?.gender),
                ageBetween(post, customFilter.studyTarget?.age),
                regionEq(post, customFilter.locationTarget?.region),
                areasIn(post, customFilter.locationTarget?.areas),
                recruitStatusEq(post, customFilter.recruitStatus)
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

    private fun recruitStatusEq(post: QExperimentPostEntity, recruitStatus: Boolean?): BooleanExpression? {
        return recruitStatus?.let { post.recruitStatus.eq(it) }
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
}
