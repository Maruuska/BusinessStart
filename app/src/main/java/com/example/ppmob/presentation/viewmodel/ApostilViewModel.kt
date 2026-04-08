package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.CountryState
import com.example.ppmob.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApostilViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
) : ViewModel() {
    private val _fields = MutableStateFlow(CountryState())
    val fields: StateFlow<CountryState> = _fields.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка стран
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    fun updateState(newState: CountryState) {
        // сброс ошибки при изменении страны
        if (_fields.value.countryId != newState.countryId) {
            _fields.value = newState.copy(errorCountry = false)
        } else {
            _fields.value = newState
        }
    }

    init {
        loadingCountries()
    }

    private fun loadingCountries() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getCountriesUseCase()) {
                is Rezult.Success -> {
                    _countries.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения стран")
                }
            }

        }
    }

    fun transition(){
        if(_fields.value.countryId!=-1){
            _appStateSave.value = AppState.Success
        }
        else{
            _fields.value.errorCountry=true
            _appStateSave.value = AppState.Error("страна не выбрана")
        }
    }
}