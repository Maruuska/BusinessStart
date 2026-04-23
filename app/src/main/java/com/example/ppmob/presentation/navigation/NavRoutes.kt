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
    const val docWithParam = "doc/{needApostille}"
    // для создания маршрутов с параметрами
    fun docWithParam(needApostille: Boolean): String {
        return "doc/$needApostille"
    }
    const val step3 = "step3"
    const val step31 = "step31"
    const val regulation = "regulation"
    const val rights = "rights"
    const val duties = "duties"
    const val step4 = "step4"
    const val step41 = "step41"
    const val bank = "bank"
    const val packageRegistration = "packageS"
    const val endCompany = "endCompany"
    const val accounting = "accounting" //налоговый учет
    const val apostil = "apostil"
    const val statement = "statement"
    const val packageStatement = "packageStatement"
    const val endStatement = "endStatement"
    const val score = "score"  // открытие счета
    const val sberScreen = "sber_screen"
    const val tbankScreen = "tbank_screen"
    const val alfaScreen = "alfa_screen"
    const val vtbScreen = "vtb_screen"
    const val raiffeisenScreen = "raiffeisen_screen"
    const val otkritieScreen = "otkritie_screen"
    const val anketa = "anketa"
    const val endScore = "endScore"
    const val signin = "signin"
    const val signup = "signup"
    const val notification = "notification"
    const val votingScreen = "votingScreen"
    const val meeting = "meeting"
    const val notify = "notify"
    const val debit = "debit"
    const val balance = "balance"
    const val staff = "staff"
}