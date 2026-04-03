package com.example.ppmob.data.dto

import com.google.gson.annotations.SerializedName

data class CompanyDto (
    //val id:Int,
    val name: String,
    @SerializedName("short_name")
    val shortName: String,
    val address: String,
    @SerializedName("type_of_activity")
    val typeActivity: Int,
    @SerializedName("one_founder")
    val oneFounder: Boolean,
)