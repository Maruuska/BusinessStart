package com.example.ppmob.data.dto

import com.google.gson.annotations.SerializedName

data class AnswerDto (
    val id: Int,
    @SerializedName("question_id")
    val questionId: Int,
    val text: String,
    @SerializedName("is_correct")
    val isCorrect: Boolean
)