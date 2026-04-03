package com.example.ppmob.domain.model

data class Company (
    val name: String,
    val shortName: String,
    val address: String,
    val typeActivity: Int,
    val oneFounder: Boolean,
)