package com.example.ppmob.domain.state

data class OfficeState (
    val name: String = "",
    var errorName:Boolean = false,
    val shortName: String = "",
    val address: String = "",
    var errorAddress:Boolean = false,
    var activityId: Int = -1,
    val oneFounder: Boolean=true  // один учредитель?
)