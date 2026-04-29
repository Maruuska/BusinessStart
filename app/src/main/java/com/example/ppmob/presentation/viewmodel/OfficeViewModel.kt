package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Activity
import com.example.ppmob.domain.model.Address
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.OfficeState
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetAddressUseCase
import com.example.ppmob.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfficeViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val getActivitysUseCase: GetActivitysUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _fieldsOffice = MutableStateFlow(OfficeState())
    val fieldsOffice: StateFlow<OfficeState> = _fieldsOffice.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _appStateSave = MutableStateFlow<AppState>(AppState.Initializing)
    val appStateSave: StateFlow<AppState> = _appStateSave.asStateFlow()

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _addressError = MutableStateFlow<String?>(null)
    val addressError: StateFlow<String?> = _addressError.asStateFlow()

    private val _activityError = MutableStateFlow<String?>(null)
    val activityError: StateFlow<String?> = _activityError.asStateFlow()

    // предупреждение о лицензии
    private val _showLicenseWarning = MutableStateFlow(false)
    val showLicenseWarning: StateFlow<Boolean> = _showLicenseWarning.asStateFlow()

    private val _activitys = MutableLiveData<List<Activity>>()
    val activitys: LiveData<List<Activity>> get() = _activitys

    private val _addresses = MutableLiveData<List<Address>>()
    val addresses: LiveData<List<Address>> get() = _addresses

    fun updateState(newState: OfficeState) {
        _fieldsOffice.value = newState

        validateName(newState.name)
        validateAddress(newState.addressId)
        validateActivity(newState.activityId)

        // Проверка лицензии
        if (newState.activityId != -1) {
            checkActivityLicenseWarning()
        } else {
            _showLicenseWarning.value = false
        }
    }

    init {
        //GetCurentUser()
        loadingActivitys()
        loadingAddresses()
    }

    // Валидация наименования
    private fun validateName(name: String) {
        val requiredText = "Общество с ограниченной ответственностью"

        when {
            name.isBlank() -> {
                _nameError.value = "Поле обязательно для заполнения"
                _fieldsOffice.value = _fieldsOffice.value.copy(errorName = true)
            }
            !name.contains(requiredText, ignoreCase = true) -> {
                _nameError.value = "Наименование должно содержать \"$requiredText\""
                _fieldsOffice.value = _fieldsOffice.value.copy(errorName = true)
            }
            else -> {
                _nameError.value = null
                _fieldsOffice.value = _fieldsOffice.value.copy(errorName = false)
            }
        }
    }

    // Валидация адреса
    private fun validateAddress(addressId: Int) {
        when {
            addressId == -1 -> {
                _addressError.value = "Выберите юридический адрес"
                _fieldsOffice.value = _fieldsOffice.value.copy(errorAddress = true)
            }
            else -> {
                val selectedAddress = _addresses.value?.find { it.id == addressId }
                if (selectedAddress != null && !selectedAddress.name.contains("офис", ignoreCase = true)) {
                    _addressError.value = "Адрес не подходит для регистрации юрлица"
                    _fieldsOffice.value = _fieldsOffice.value.copy(errorAddress = true)
                } else {
                    _addressError.value = null
                    _fieldsOffice.value = _fieldsOffice.value.copy(errorAddress = false)
                }
            }
        }
    }

    // Валидация деятельности
    private fun validateActivity(activityId: Int) {
        when {
            activityId == -1 -> {
                _activityError.value = "Выберите вид деятельности"
                _fieldsOffice.value = _fieldsOffice.value.copy(errorActivity = true)
            }
            else -> {
                _activityError.value = null
                _fieldsOffice.value = _fieldsOffice.value.copy(errorActivity = false)
            }
        }
    }

    private fun checkActivityLicenseWarning() {
        val selectedActivity = _activitys.value?.find { it.id == _fieldsOffice.value.activityId }
        _showLicenseWarning.value = selectedActivity != null && selectedActivity.license
    }

    fun loadingAddresses (){
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getAddressUseCase()) {
                is Rezult.Success -> {
                    _addresses.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения адресов")
                }
            }
        }
    }

    fun loadingActivitys(){
        _appState.value = AppState.Loading
        viewModelScope.launch {
            when (val result = getActivitysUseCase()) {
                is Rezult.Success -> {
                    _activitys.value = result.data
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value = AppState.Error(result.exception.message ?: "ошибка получения видов деятельности")
                }
            }
        }
    }

    fun save(){
        viewModelScope.launch {
            _appStateSave.value = AppState.Loading
            val currentState = _fieldsOffice.value
            validateName(currentState.name)
            validateAddress(currentState.addressId)
            validateActivity(currentState.activityId)

            val hasErrors = _fieldsOffice.value.errorName ||
                    _fieldsOffice.value.errorAddress ||
                    _fieldsOffice.value.errorActivity

            if (!hasErrors) {
                _appStateSave.value = AppState.Success
            } else {
                _appStateSave.value = AppState.Error("Проверьте правильность заполнения полей")
            }
        }
    }
}