package com.example.ppmob.data.dto

import com.google.gson.annotations.SerializedName

data class QuestionDto(
    val id: Int,
    @SerializedName("test_id")
    val testId: Int,
    val text: String
)