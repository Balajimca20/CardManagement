package com.example.animation.domain.network.apiutils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class CustomErrorResponse(
    @SerializedName("status_code")
    val statusCode : Int,
    @SerializedName("status")
    val status : Boolean,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : String,
)
