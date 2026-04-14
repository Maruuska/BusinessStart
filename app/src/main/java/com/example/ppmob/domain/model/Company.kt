package com.example.ppmob.domain.model

import java.util.UUID

data class Company (
    val name: String,
    val shortName: String,
    val address: Int,
    val typeActivity: Int,
    val oneFounder: Boolean,
    val userId: UUID
)