package com.example.ppmob.domain.repository

import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.model.Question
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Test

interface TestRepository {

    // Приостанавливающая функция для получения списка всех тестов
    suspend fun getTests(): Rezult<List<Test>>

    // Приостанавливающая функция для получения списка всех вопросов
    suspend fun getQuestions(): Rezult<List<Question>>

    // Приостанавливающая функция для получения списка всех ответов
    suspend fun getAnswers(): Rezult<List<Answer>>
}