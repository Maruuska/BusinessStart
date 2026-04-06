package com.example.ppmob.presentation.viewmodel

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.model.Rights
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.usecase.CreateRightUseCase
import com.example.ppmob.domain.usecase.GetRightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RightsViewModel @Inject constructor(
    private val getRightsUseCase: GetRightsUseCase,
    private val createRightUseCase: CreateRightUseCase,
) : ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка прав
    private val _rights = MutableLiveData<List<Rights>>()
    val rights: LiveData<List<Rights>> get() = _rights

    init {
        loadingRights()
    }

    private fun loadingRights() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getRightsUseCase()) {
                is Rezult.Success -> {
                    _rights.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения прав")
                }
            }

        }
    }

    fun addRight(name: String){
        if(name.isNotEmpty()){
            _appStateSave.value = AppState.Loading
            viewModelScope.launch {
                when (val result = createRightUseCase(name)) {
                    is Rezult.Success -> {
                        loadingRights()
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