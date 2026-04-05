package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.artguess.presentation.navigation.NavRoutes
import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.OfficeState
import com.example.ppmob.domain.state.PalataState
import com.example.ppmob.domain.usecase.CreateCompanyUseCase
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

    private val _fieldsPalata = MutableStateFlow(PalataState())
    val fieldsPalata: StateFlow<PalataState> = _fieldsPalata.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка стран
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    fun updateState(newState: PalataState) {
        _fieldsPalata.value = newState
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

    fun transition(navController: NavHostController? = null){
        if(_fieldsPalata.value.countryId!=-1){
            // Получаем выбранную страну
            val selectedCountry = _countries.value?.find { it.id == _fieldsPalata.value.countryId }

            // Определяем, нужен ли апостиль (legal == "Апостиль")
            val needApostille = selectedCountry?.legal == "Апостиль"

            _appStateSave.value = AppState.Success

            // Передаем параметр на экран DocumentScreen
            navController?.navigate(NavRoutes.docWithParam(needApostille))
        }
        else{
            _fieldsPalata.value.errorCountry=true
            _appStateSave.value = AppState.Error("страна не выбрана")
        }
    }
}