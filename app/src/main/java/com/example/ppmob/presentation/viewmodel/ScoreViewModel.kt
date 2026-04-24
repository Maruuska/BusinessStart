package com.example.ppmob.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ppmob.domain.model.Bank
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.RegulationState
import com.example.ppmob.domain.state.ScoreState
import com.example.ppmob.domain.usecase.GetBanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val getBanksUseCase: GetBanksUseCase,
) : ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка банков
    private val _banks = mutableStateOf<List<Bank>>(emptyList())
    val banks: State<List<Bank>> = _banks

    private val _scoreState = MutableStateFlow(ScoreState())
    val scoreState: StateFlow<ScoreState> = _scoreState.asStateFlow()

    init {
        loadingBanks()
    }

    private fun loadingBanks() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getBanksUseCase()) {
                is Rezult.Success -> {
                    _banks.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения банков")
                }
            }
        }
    }

    fun updateState(newState: ScoreState) {
        _scoreState.value = newState
    }

}