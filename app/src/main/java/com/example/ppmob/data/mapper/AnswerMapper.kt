package com.example.ppmob.data.mapper

import com.example.ppmob.data.dto.AnswerDto
import com.example.ppmob.domain.model.Answer

object AnswerMapper {
    // Преобразование одного AnswerDto в доменную модель Answer
    fun toDomain(answerDto: AnswerDto): Answer {
        return Answer(
            id = answerDto.id,
            questionId = answerDto.questionId,
            text = answerDto.text,
            isCorrect = answerDto.isCorrect
        )
    }

    // Преобразование списка DTO в список доменных моделей Answer
    fun toDomainList(answers: List<AnswerDto>): List<Answer>   {
        return answers.map { item ->
            toDomain(
                item
            )
        }
    }
}