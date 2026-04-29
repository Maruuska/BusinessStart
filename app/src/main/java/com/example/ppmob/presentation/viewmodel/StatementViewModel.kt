package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.CodeCountry
import com.example.ppmob.domain.model.Form
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.StatementState
import com.example.ppmob.domain.usecase.GetCodeCountryUseCase
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
        validateFieldImmediately(newState)
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

    // Динамическая валидация отдельного поля при изменении
    private fun validateFieldImmediately(state: StatementState) {
        val currentState = _fields.value
        val nameError = state.name.isBlank()
        val nameLatError = state.nameLat.isBlank() || !isValidLatinText(state.nameLat)
        val fioError = state.fio.isBlank()
        val codeError = state.codeCountryId == -1
        val formError = state.formId == -1

        if (currentState.errorName != nameError ||
            currentState.errorNameLat != nameLatError ||
            currentState.fioError != fioError ||
            currentState.codeError != codeError ||
            currentState.formError != formError) {

            _fields.value = currentState.copy(
                errorName = nameError,
                errorNameLat = nameLatError,
                fioError = fioError,
                codeError = codeError,
                formError = formError
            )
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

        val isValidNameLat = currentState.nameLat.isNotBlank() && isValidLatinText(currentState.nameLat)
        if (!isValidNameLat) {
            _fields.value = _fields.value.copy(errorNameLat = true)
            isValid = false
        }

        val isValidFio = currentState.fio.isNotBlank()
        if (!isValidFio) {
            _fields.value = _fields.value.copy(fioError = true)
            isValid = false
        }

        val isValidCode = currentState.codeCountryId != -1
        if (!isValidCode) {
            _fields.value = _fields.value.copy(codeError = true)
            isValid = false
        }

        val isValidForm = currentState.formId != -1
        if (!isValidForm) {
            _fields.value = _fields.value.copy(formError = true)
            isValid = false
        }

        if (isValid) {
            _fields.value = _fields.value.copy(
                errorName = false,
                errorNameLat = false,
                fioError = false,
                codeError = false,
                formError = false
            )
        }

        return isValid
    }

    private fun isValidLatinText(text: String): Boolean {
        val latinRegex = Regex("^[A-Za-z\\s\\-]+$")
        return text.matches(latinRegex)
    }
}