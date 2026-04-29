package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AnketaState
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetCountriesUseCase
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
        validateFieldImmediately(newState)
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

    // Динамическая валидация отдельного поля при изменении
    private fun validateFieldImmediately(state: AnketaState) {
        val currentState = _fields.value
        val nameError = state.name.isBlank()
        val fioError = state.fio.isBlank()
        val sourceError = state.source.isBlank()
        val incomeError = state.income == 0
        val countryError = state.countryId == -1
        val activityError = state.activityId == -1

        if (currentState.errorName != nameError ||
            currentState.fioError != fioError ||
            currentState.sourceError != sourceError ||
            currentState.incomeError != incomeError ||
            currentState.countryError != countryError ||
            currentState.activityError != activityError) {

            _fields.value = currentState.copy(
                errorName = nameError,
                fioError = fioError,
                sourceError = sourceError,
                incomeError = incomeError,
                countryError = countryError,
                activityError = activityError
            )
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
        val currentState = _fields.value

        val isValidName = currentState.name.isNotBlank()
        if (!isValidName) {
            _fields.value = currentState.copy(errorName = true)
            isValid = false
        }

        val isValidFio = currentState.fio.isNotBlank()
        if (!isValidFio) {
            _fields.value = _fields.value.copy(fioError = true)
            isValid = false
        }

        val isValidSource = currentState.source.isNotBlank()
        if (!isValidSource) {
            _fields.value = _fields.value.copy(sourceError = true)
            isValid = false
        }

        val isValidIncome = currentState.income != 0
        if (!isValidIncome) {
            _fields.value = _fields.value.copy(incomeError = true)
            isValid = false
        }

        val isValidCountry = currentState.countryId != -1
        if (!isValidCountry) {
            _fields.value = _fields.value.copy(countryError = true)
            isValid = false
        }

        val isValidActivity = currentState.activityId != -1
        if (!isValidActivity) {
            _fields.value = _fields.value.copy(activityError = true)
            isValid = false
        }

        if (isValid) {
            _fields.value = _fields.value.copy(
                errorName = false,
                fioError = false,
                sourceError = false,
                incomeError = false,
                countryError = false,
                activityError = false
            )
        }

        return isValid
    }
}