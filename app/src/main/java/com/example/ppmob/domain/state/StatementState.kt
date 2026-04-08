package com.example.ppmob.domain.state

data class StatementState (
    val name: String = "",
    var errorName:Boolean = false,
    val nameLat: String = "",
    var errorNameLat:Boolean = false,
    val shortName: String = "",
    val fio: String = "",
    var fioError: Boolean = false,
    var codeCountryId: Int = -1,
    var codeError: Boolean = false,
    var formId: Int = -1,
    var formError: Boolean = false,
)