package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
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
class PalataViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
) : ViewModel() {

    private val _fieldsPalata = MutableStateFlow(CountryState())
    val fieldsPalata: StateFlow<CountryState> = _fieldsPalata.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка стран
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    fun updateState(newState: CountryState) {
        _fieldsPalata.value = newState
        validateCountry(newState.countryId)
    }

    init {
        loadingCountries()
    }

    private fun validateCountry(countryId: Int) {
        if (countryId == -1) {
            _fieldsPalata.value = _fieldsPalata.value.copy(errorCountry = true)
        } else {
            _fieldsPalata.value = _fieldsPalata.value.copy(errorCountry = false)
        }
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

    fun transition(navController: NavHostController? = null){
        if(_fieldsPalata.value.countryId != -1){
            val selectedCountry = _countries.value?.find { it.id == _fieldsPalata.value.countryId }

            val needApostille = selectedCountry?.legal == "Апостиль"
            val isSimplified = selectedCountry?.legal == "Упрощённый порядок"  // для Беларуси

            _appStateSave.value = AppState.Success

            navController?.navigate(NavRoutes.docWithParam(needApostille, isSimplified))
        }
        else{
            _fieldsPalata.value = _fieldsPalata.value.copy(errorCountry = true)
            _appStateSave.value = AppState.Error("страна не выбрана")
        }
    }
}