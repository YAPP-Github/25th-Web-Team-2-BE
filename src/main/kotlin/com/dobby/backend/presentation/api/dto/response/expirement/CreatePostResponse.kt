package com.dobby.backend.presentation.api.dto.response.expirement

import java.time.LocalDate

data class CreatePostResponse(
    val postInfo: PostInfo
)
data class PostInfo(
    val postId: Long,
    val title: String,
    val views: Int,
    val school: String,
    val reward: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
