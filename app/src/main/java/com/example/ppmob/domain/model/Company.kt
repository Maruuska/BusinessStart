package com.example.ppmob.domain.model

import java.sql.Timestamp
import java.util.UUID

data class Company (
    val name: String,
    val shortName: String,
    val address: Int,
    val typeActivity: Int,
    val oneFounder: Boolean,
    val userId: UUID,
    val date: Timestamp
)