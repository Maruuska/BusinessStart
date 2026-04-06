package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Duty
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Rights
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.usecase.CreateDutyUseCase
import com.example.ppmob.domain.usecase.GetDutiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DutiesViewModel @Inject constructor(
    private val getDutiesUseCase: GetDutiesUseCase,
    private val createDutyUseCase: CreateDutyUseCase,
) : ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка прав
    private val _duties = MutableLiveData<List<Duty>>()
    val duties: LiveData<List<Duty>> get() = _duties

    init {
        loadingDuties()
    }

    private fun loadingDuties() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getDutiesUseCase()) {
                is Rezult.Success -> {
                    _duties.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения обязанностей")
                }
            }

        }
    }

    fun addDuty(name: String){
        if(name.isNotEmpty()){
            _appStateSave.value = AppState.Loading
            viewModelScope.launch {
                when (val result = createDutyUseCase(name)) {
                    is Rezult.Success -> {
                        loadingDuties()
                        _appStateSave.value = AppState.Success
                    }
                    is Rezult.Failure -> {
                        _appStateSave.value = AppState.Error(result.exception.message ?: "ошибка создания права")
                    }
                }

            }
        }
    }
}