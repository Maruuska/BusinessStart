package com.example.ppmob.domain.state

data class RegulationState(
    val ystavType: Boolean = true,  // true - типовой, false - индивидуальный
    val exitOption: Int = 0,        // 0 - свободный, 1 - с согласия, 2 - запрет
)