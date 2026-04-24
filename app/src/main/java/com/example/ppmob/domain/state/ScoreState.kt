package com.example.ppmob.domain.state

data class ScoreState(
    val selectedBankIds: Set<Int> = emptySet()
)