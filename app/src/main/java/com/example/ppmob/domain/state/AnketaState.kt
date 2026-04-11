package com.example.ppmob.domain.state

data class AnketaState (
    var name: String = "",
    var errorName:Boolean = false,
    var countryId: Int = -1,
    var countryError: Boolean = false,
    var activityId: Int = -1,
    var activityError: Boolean = false,
    var income: Int = 500000,
    var incomeError: Boolean = false,
    var source: String = "",
    var sourceError: Boolean = false,
    var fio: String = "",
    var fioError: Boolean = false
)