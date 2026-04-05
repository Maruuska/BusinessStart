package com.example.artguess.presentation.navigation

// класс содержащий маршруты для навигации по приложению
object NavRoutes {
    const val splash = "splash"
    const val menu = "menu"
    const val step1 = "step1"
    const val step11 = "step11"
    const val office = "office"
    const val oneFounder = "oneFounder"
    const val severalFounder = "severalFounder"
    const val step2 = "step2"
    const val step21 = "step21"
    const val palata = "palata"
    const val doc = "doc"
    const val docWithParam = "doc/{needApostille}"

    // Функции для создания маршрутов с параметрами
    fun docWithParam(needApostille: Boolean): String {
        return "doc/$needApostille"
    }

    const val signin = "signin"
    const val signup = "signup"
}