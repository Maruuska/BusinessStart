package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.QuestionDto
import com.example.ppmob.domain.model.Question

object QuestionMapper {
    // Преобразование одного QuestionDto в доменную модель Question
    fun toDomain(questionDto: QuestionDto): Question {
        return Question(
            id = questionDto.id,
            testId = questionDto.testId,
            text = questionDto.text
        )
    }

    // Преобразование списка DTO в список доменных моделей Question
    fun toDomainList(questions: List<QuestionDto>): List<Question>   {
        return questions.map { item ->
            toDomain(
                item
            )
        }
    }
}