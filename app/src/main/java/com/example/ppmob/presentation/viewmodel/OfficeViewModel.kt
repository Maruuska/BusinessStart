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
import com.example.ppmob.domain.usecase.CreateCompanyUseCase
import com.example.ppmob.domain.usecase.GetActivitysUseCase
import com.example.ppmob.domain.usecase.GetAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfficeViewModel @Inject constructor(
    private val createCompanyUseCase: CreateCompanyUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val getActivitysUseCase: GetActivitysUseCase,
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

    //хранение списка видов деятельности компании
    private val _activitys = MutableLiveData<List<Activity>>()
    val activitys: LiveData<List<Activity>> get() = _activitys

    //хранение списка юр.адресов
    private val _addresses = MutableLiveData<List<Address>>()
    val addresses: LiveData<List<Address>> get() = _addresses

    fun updateState(newState: OfficeState) {
        _fieldsOffice.value = newState
        // При изменении activityId проверка лицензии
        if (newState.activityId != -1) {
            checkActivityLicense()
        }
    }

    // Функция для проверки лицензии у выбранного вида деятельности
    private fun checkActivityLicense() {
        val selectedActivity = _activitys.value?.find { it.id == _fieldsOffice.value.activityId }
        if (selectedActivity != null && selectedActivity.license) {
            _activityError.value = "Для данного вида деятельности потребуется лицензия"
            _fieldsOffice.value.errorActivity = true
        } else {
            _activityError.value = null
            _fieldsOffice.value.errorActivity = false
        }
    }

    init {
        loadingActivitys()
        loadingAddresses()
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
            if(verificationFields()) {
                _appStateSave.value = AppState.Loading
                when (val result =
                    createCompanyUseCase(
                        _fieldsOffice.value.name,
                        _fieldsOffice.value.shortName,
                        _fieldsOffice.value.addressId,
                        _fieldsOffice.value.activityId,
                        _fieldsOffice.value.oneFounder
                    )) {
                    is Rezult.Success -> {
                        _appStateSave.value = AppState.Success
                    }

                    is Rezult.Failure -> {
                        _appStateSave.value =
                            AppState.Error(result.exception.message ?: "ошибка создания ооо")
                    }
                }
            }
        }
    }

    fun verificationFields(): Boolean {
        var isValid = true

        // Сбросить ошибки
        _fieldsOffice.value = _fieldsOffice.value.copy(
            errorName = false,
            errorAddress = false,
            errorActivity = false
        )

        // Проверка наименования
        val requiredText = "Общество с ограниченной ответственностью"

        if (_fieldsOffice.value.name.isBlank()) {
            _nameError.value = "Поле обязательно для заполнения"
            _fieldsOffice.value.errorName = true
            _appStateSave.value = AppState.Error("поле имя пустое")
            isValid = false
        }
        else{
            _fieldsOffice.value.errorName = false
        }

        if (!_fieldsOffice.value.name.contains(requiredText, ignoreCase = true)) {
            _nameError.value = "Наименование должно содержать \"$requiredText\""
            _fieldsOffice.value.errorName = true
            _appStateSave.value = AppState.Error("поле имя не содержит ооо")
            isValid = false
        } else {
            _nameError.value = null
            _fieldsOffice.value.errorName = false
        }

        // Проверка адреса
        if (_fieldsOffice.value.addressId == -1) {
            _addressError.value = "Выберите юридический адрес"
            _fieldsOffice.value.errorAddress = true
            _appStateSave.value = AppState.Error("поле адрес пустое")
            isValid = false
        }
        else {
            val selectedAddress = _addresses.value?.find { it.id == _fieldsOffice.value.addressId }

            if (selectedAddress != null && !selectedAddress.name.contains("офис", ignoreCase = true)) {
                _addressError.value = "Адрес не подходит для регистрации юрлица"
                _fieldsOffice.value.errorAddress = true
                _appStateSave.value = AppState.Error("адрес не содержит офис")
                isValid = false
            } else {
                _addressError.value = null
                _fieldsOffice.value = _fieldsOffice.value.copy(errorAddress = false)
            }
        }

        // Проверка деятельности
        if (_fieldsOffice.value.activityId == -1) {
            _activityError.value = "Выберите вид деятельности"
            _fieldsOffice.value.errorActivity = true
            _appStateSave.value = AppState.Error("поле деятельность пустое")
            isValid = false
        }
        else{
            // Проверка на лицензию
            val selectedActivity = _activitys.value?.find { it.id == _fieldsOffice.value.activityId }
            if (selectedActivity != null && selectedActivity.license) {
                _activityError.value = "Для данного вида деятельности потребуется лицензия"
                _fieldsOffice.value = _fieldsOffice.value.copy(errorActivity = true)
            } else {
                _activityError.value = null
                _fieldsOffice.value = _fieldsOffice.value.copy(errorActivity = false)
            }
        }

        return isValid
    }
}