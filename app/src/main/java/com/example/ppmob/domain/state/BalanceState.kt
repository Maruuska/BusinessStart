package com.example.ppmob.domain.state

data class BalanceState(
    val rows: List<BalanceRow> = listOf(
        BalanceRow(type = "Активы", name = "Денежные средства на счетах", sum = "100 000", isExpanded = false),
        BalanceRow(type = "Активы", name = "Дебиторская задолженность", sum = "100 000", isExpanded = false),
        BalanceRow(type = "Пассивы", name = "Требования кредиторов", sum = "100 000", isExpanded = false)
    )
)

data class BalanceRow(
    val type: String, // "Активы" или "Пассивы"
    val name: String,
    val sum: String,
    val isExpanded: Boolean = false // для возможного расширения
)