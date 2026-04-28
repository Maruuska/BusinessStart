package com.example.ppmob.data.repository

import com.example.ppmob.data.mapper.AnswerMapper
import com.example.ppmob.data.mapper.QuestionMapper
import com.example.ppmob.data.mapper.TestMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.domain.model.Answer
import com.example.ppmob.domain.model.Question
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Test
import com.example.ppmob.domain.repository.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : TestRepository {
    override suspend fun getTests(): Rezult<List<Test>> {
        return try {
            val testsDto = apiInterface.getTests()
            val tests = TestMapper.toDomainList(testsDto)
            Rezult.Success(tests)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun getQuestions(): Rezult<List<Question>> {
        return try {
            val questionsDto = apiInterface.getQuestions()
            val questions = QuestionMapper.toDomainList(questionsDto)
            Rezult.Success(questions)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }

    override suspend fun getAnswers(): Rezult<List<Answer>> {
        return try {
            val answersDto = apiInterface.getAnswers()
            val answers = AnswerMapper.toDomainList(answersDto)
            Rezult.Success(answers)
        } catch (e: Exception) {
            Rezult.Failure(e)
        }
    }
}