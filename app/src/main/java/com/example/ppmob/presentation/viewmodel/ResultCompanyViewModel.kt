package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.usecase.GetCurrentUserUseCase
import com.example.ppmob.domain.usecase.GetLastCompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ResultCompanyViewModel @Inject constructor(
    private val getLastCompanyUseCase: GetLastCompanyUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _companyName = MutableStateFlow("")
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    init {
        loadCompanyData()
    }

    private fun loadCompanyData() {
        viewModelScope.launch {
            _appState.value = AppState.Loading

            // 1. Сначала получаем текущего пользователя
            when (val userResult = getCurrentUserUseCase()) {
                is Rezult.Success -> {
                    val userId = userResult.data.id

                    // 2. Затем получаем компанию по userId
                    when (val companyResult = getLastCompanyUseCase("eq." + userId.toString())) {
                        is Rezult.Success -> {
                            _companyName.value = companyResult.data.shortName
                            _appState.value = AppState.Success
                        }
                        is Rezult.Failure -> {
                            _appState.value = AppState.Error(
                                companyResult.exception.message ?: "Ошибка получения названия компании"
                            )
                        }
                    }
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(
                        userResult.exception.message ?: "Ошибка получения текущего пользователя"
                    )
                }
            }
        }
    }

    // Функция для повторной загрузки (опционально)
    fun retryLoad() {
        loadCompanyData()
    }
}