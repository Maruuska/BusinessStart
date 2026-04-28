package com.example.ppmob.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Test
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.usecase.GetTestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestsViewModel @Inject constructor(
    private val getTestsUseCase: GetTestsUseCase
) : ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Loading)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    // хранение списка тестов
    private val _tests = mutableStateOf<List<Test>>(emptyList())
    val tests: State<List<Test>> = _tests

    init {
        loadTests()
    }

    private fun loadTests() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getTestsUseCase()) {
                is Rezult.Success -> {
                    _tests.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения тестов")
                }
            }

        }
    }

}