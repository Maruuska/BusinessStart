package com.example.ppmob.domain.model

data class Answer(
    val id: Int,
    val questionId: Int,
    val text: String,
    val isCorrect: Boolean
)