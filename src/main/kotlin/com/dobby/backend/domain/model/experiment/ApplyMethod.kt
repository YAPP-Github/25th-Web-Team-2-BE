package com.dobby.backend.domain.model.experiment

data class ApplyMethod(
    val id: Long,
    var phoneNum: String?,
    var formUrl: String?,
    var content: String
) {
    fun update(phoneNum: String?, formUrl: String?, content: String){
        this.content = content
        this.formUrl = formUrl
        this.phoneNum = phoneNum
    }
    companion object {
        fun newApplyMethod(
            phoneNum: String?,
            formUrl: String?,
            content: String
        ) = ApplyMethod(
            id = 0L,
            phoneNum = phoneNum,
            formUrl = formUrl,
            content = content
        )
    }

}
