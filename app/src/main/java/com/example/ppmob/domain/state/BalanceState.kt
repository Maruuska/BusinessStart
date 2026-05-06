package com.example.ppmob.domain.state

data class BalanceState(
    val rows: List<BalanceRow> = listOf(
        BalanceRow(type = "Активы", name = "Денежные средства на счетах", sum = "0"),
        BalanceRow(type = "Активы", name = "Дебиторская задолженность", sum = "0"),
        BalanceRow(type = "Пассивы", name = "Требования кредиторов", sum = "0")
    )
)

data class BalanceRow(
    val type: String, // "Активы" или "Пассивы"
    val name: String,
    val sum: String,
)