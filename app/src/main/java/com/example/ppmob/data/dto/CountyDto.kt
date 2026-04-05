package com.example.ppmob.data.dto

import com.google.gson.annotations.SerializedName

data class CountyDto (
    val id: Int,
    val name: String,
    val legal: String,
    @SerializedName("tax_code")
    val taxCode: Boolean
)