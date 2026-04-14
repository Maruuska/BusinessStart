package com.example.ppmob.data.dto

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.UUID

data class CompanyDto (
    //val id:Int,
    val name: String,
    @SerializedName("short_name")
    val shortName: String,
    val address: Int,
    @SerializedName("type_of_activity")
    val typeActivity: Int,
    @SerializedName("one_founder")
    val oneFounder: Boolean,
    @SerializedName("user_id")
    val userId: UUID,
    val date: Timestamp
)