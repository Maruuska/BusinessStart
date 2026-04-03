package com.example.ppmob.domain.state

sealed class AppState{
    data object Loading: AppState()
    data object Initializing: AppState()
    data object Success: AppState()
    data class Error(val message: String): AppState()
}