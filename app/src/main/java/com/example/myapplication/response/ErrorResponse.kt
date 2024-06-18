package com.example.myapplication.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: ErrorDetail
)

data class ErrorDetail(
    @SerializedName("message")
    val message: String,

    @SerializedName("code")
    val code: Int
)
