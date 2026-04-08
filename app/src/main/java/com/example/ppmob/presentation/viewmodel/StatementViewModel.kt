package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.CodeCountry
import com.example.ppmob.domain.model.Country
import com.example.ppmob.domain.model.Form
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.CountryState
import com.example.ppmob.domain.state.StatementState
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
class StatementViewModel @Inject constructor(
    private val getCodeCountryUseCase: GetCodeCountryUseCase,
    private val getFormsUseCase: GetFormsUseCase,
) : ViewModel() {
    private val _fields = MutableStateFlow(StatementState())
    val fields: StateFlow<StatementState> = _fields.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    //хранение списка кодов стран
    private val _codeCountries = MutableLiveData<List<CodeCountry>>()
    val codeCountries: LiveData<List<CodeCountry>> get() = _codeCountries

    //хранение списка форм
    private val _forms = MutableLiveData<List<Form>>()
    val forms: LiveData<List<Form>> get() = _forms

    fun updateState(newState: StatementState) {
        _fields.value = newState
    }

    init {
        loadingCodeCountries()
        loadingForms()
    }

    private fun loadingCodeCountries() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getCodeCountryUseCase()) {
                is Rezult.Success -> {
                    _codeCountries.value = result.data
                    _appState.value = AppState.Success
                }

                is Rezult.Failure -> {
                    _appState.value =
                        AppState.Error(result.exception.message ?: "ошибка получения кодов стран")
                }
            }

        }
    }

    private fun loadingForms() {
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getFormsUseCase()) {
                is Rezult.Success -> {
                    _forms.value = result.data
                    _appState.value = AppState.Success
                }

                is Rezult.Failure -> {
                    _appState.value =
                        AppState.Error(result.exception.message ?: "ошибка получения кодов стран")
                }
            }

        }
    }

    fun save() {
        viewModelScope.launch {
            if (verificationFields()) {

                _appStateSave.value = AppState.Success

            } else {
                _appStateSave.value = AppState.Error("ошибка при создании заявления")
            }
        }
    }


    fun verificationFields(): Boolean {
        var isValid = true

        // Сбросить ошибки
        _fields.value = _fields.value.copy(
            errorName = false,
            errorNameLat = false,
            codeError = false,
            fioError = false,
            formError = false
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


        // Проверка наименования на латинице (только английские буквы)
        if (_fields.value.nameLat.isBlank()) {
            _fields.value = _fields.value.copy(errorNameLat = true)
            _appStateSave.value = AppState.Error("поле имя лат пустое")
            isValid = false
        } else if (!isValidLatinText(_fields.value.nameLat)) {
            _fields.value = _fields.value.copy(errorNameLat = true)
            _appStateSave.value = AppState.Error("ошибка в латинском названии")
            isValid = false
        } else {
            _fields.value = _fields.value.copy(errorNameLat = false)
        }

        // Проверка фио
        if (_fields.value.fio.isBlank()) {
            _fields.value.fioError = true
            _appStateSave.value = AppState.Error("поле фио пустое")
            isValid = false
        } else {
            _fields.value.fioError = false
        }

        // Проверка кода страны
        if (_fields.value.codeCountryId == -1) {
            _fields.value.codeError = true
            _appStateSave.value = AppState.Error("поле код страны пустое")
            isValid = false
        } else {
            _fields.value.codeError = false
        }

        // Проверка формы
        if (_fields.value.formId == -1) {
            _fields.value.formError = true
            _appStateSave.value = AppState.Error("поле форма пустое")
            isValid = false
        } else {
            _fields.value.formError = false
        }

        return isValid
    }

    // проверка строки на содержание только латинских букв
    private fun isValidLatinText(text: String): Boolean {
        val latinRegex = Regex("^[A-Za-z\\s\\-]+$")
        return text.matches(latinRegex)
    }
}