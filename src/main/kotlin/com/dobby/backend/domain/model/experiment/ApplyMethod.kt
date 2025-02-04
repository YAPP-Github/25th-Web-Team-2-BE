package com.dobby.backend.domain.model.experiment

data class ApplyMethod(
    val id: String,
    var phoneNum: String?,
    var formUrl: String?,
    var content: String
) {
    fun update(
        phoneNum: String?, formUrl: String?, content: String?
    ): ApplyMethod {
        return this.copy(
            phoneNum = phoneNum ?: this.phoneNum,
            formUrl = formUrl ?: this.formUrl,
            content = content ?: this.content
        )
    }

    companion object {
        fun newApplyMethod(
            id: String,
            phoneNum: String?,
            formUrl: String?,
            content: String
        ) = ApplyMethod(
            id = id,
            phoneNum = phoneNum,
            formUrl = formUrl,
            content = content
        )
    }

}
