package com.example.ppmob.domain.state

data class OfficeState (
    val name: String = "",
    var errorName:Boolean = false,
    val shortName: String = "",
    var addressId: Int = -1,
    var errorAddress:Boolean = false,
    var activityId: Int = -1,
    var errorActivity: Boolean = false,
    val oneFounder: Boolean = true  // один учредитель?
)