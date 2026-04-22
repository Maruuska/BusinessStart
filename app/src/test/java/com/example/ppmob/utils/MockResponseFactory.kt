package com.example.ppmob.utils

import com.example.ppmob.data.dto.BankDto
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse

object MockResponseFactory {
    private val gson = Gson()

    fun successResponse(banks: List<BankDto>): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .setBody(gson.toJson(banks))
            .addHeader("Content-Type", "application/json")
    }

    fun errorResponse(code: Int = 500, message: String = "Server Error"): MockResponse {
        return MockResponse()
            .setResponseCode(code)
            .setBody("""{"error": "$message"}""")
    }

    fun createBankDtoList(): List<BankDto> {
        return listOf(
            BankDto(
                id = 1,
                name = "Test Bank 1",
                specialization = "Универсальный",
                time = "4-5 дней",
                comment = "comment",
                image = ""
            ),
            BankDto(
                id = 2,
                name = "Test Bank 2",
                specialization = "ОАЭ",
                time = "4-5 дней",
                comment = "comment",
                image = ""
            )
        )
    }
}