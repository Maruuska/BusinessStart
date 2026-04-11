package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.CodeCountry
import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Form
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AnketaState
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.StatementState
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetCodeCountryUseCase
import com.example.ppmob.domain.usecase.GetCountriesUseCase
import com.example.ppmob.domain.usecase.GetFormsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnketaViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getActivitysUseCase: GetActivitysUseCase,
) : ViewModel() {

    private val _fields = MutableStateFlow(AnketaState())
    val fields: StateFlow<AnketaState> = _fields.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка стран
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    //хранение списка видов деятельности компании
    private val _activitys = MutableLiveData<List<Activity>>()
    val activitys: LiveData<List<Activity>> get() = _activitys

    fun updateState(newState: AnketaState) {
        _fields.value = newState
    }

    init {
        loadingCountries()
        loadingActivitys()
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
                    _appState.value =
                        AppState.Error(result.exception.message ?: "ошибка получения стран")
                }
            }

        }
    }

    fun loadingActivitys() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getActivitysUseCase()) {
                is Rezult.Success -> {
                    _activitys.value = result.data
                    _appState.value = AppState.Success
                }

                is Rezult.Failure -> {
                    _appState.value = AppState.Error(
                        result.exception.message ?: "ошибка получения видов деятельности"
                    )
                }
            }

        }
    }

    fun save() {
        viewModelScope.launch {
            if (verificationFields()) {
                _appStateSave.value = AppState.Success
            } else {
                _appStateSave.value = AppState.Error("ошибка при заполнении анкеты")
            }
        }
    }

    fun verificationFields(): Boolean {
        var isValid = true

        // Сбросить ошибки
        _fields.value = _fields.value.copy(
            errorName = false,
            countryError = false,
            activityError = false,
            fioError = false,
            sourceError = false,
            incomeError = false
        )

        // Проверка наименования на русском
        val requiredText = "Общество с ограниченной ответственностью"

        if (_fields.value.name.isBlank()) {
            _fields.value = _fields.value.copy(errorName = true)
            _appStateSave.value = AppState.Error("поле имя пустое")
            isValid = false
        } else if (!_fields.value.name.contains(requiredText, ignoreCase = true)) {
            _fields.value = _fields.value.copy(errorName = true)
            _appStateSave.value = AppState.Error("поле имя не содержит ооо")
            isValid = false
        } else {
            _fields.value = _fields.value.copy(errorName = false)
        }

        // Проверка фио
        if (_fields.value.fio.isBlank()) {
            _fields.value.fioError = true
            _appStateSave.value = AppState.Error("поле фио пустое")
            isValid = false
        } else {
            _fields.value.fioError = false
        }

        // Проверка источника средств
        if (_fields.value.source.isBlank()) {
            _fields.value.sourceError = true
            _appStateSave.value = AppState.Error("поле источник пустое")
            isValid = false
        } else {
            _fields.value.sourceError = false
        }

        // Проверка дохода
        if (_fields.value.income == 0) {
            _fields.value.incomeError = true
            _appStateSave.value = AppState.Error("поле доход пустое")
            isValid = false
        } else {
            _fields.value.incomeError = false
        }

        // Проверка кода страны
        if (_fields.value.countryId == -1) {
            _fields.value.countryError = true
            _appStateSave.value = AppState.Error("поле страна пустое")
            isValid = false
        } else {
            _fields.value.countryError = false
        }

        // Проверка формы
        if (_fields.value.activityId == -1) {
            _fields.value.activityError = true
            _appStateSave.value = AppState.Error("поле форма пустое")
            isValid = false
        } else {
            _fields.value.activityError = false
        }

        return isValid
    }
}