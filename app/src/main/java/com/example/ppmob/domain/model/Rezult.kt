package com.example.ppmob.domain.model

// Sealed class для типобезопасного представления результата операции
sealed class Rezult<out T> {
    // Успешный результат с данными
    data class Success<out T>(val data: T) : Rezult<T>() // Содержит данные произвольного типа

    // Неуспешный результат с исключением
    data class Failure(val exception: Exception) : Rezult<Nothing>() // Nothing - не содержит данных
}