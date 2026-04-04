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

    private val _appState =
        MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    //хранение списка видов деятельности компании
    private val _activitys = MutableLiveData<List<Activity>>()
    val activitys: LiveData<List<Activity>> get() = _activitys

    //хранение списка юр.адресов
    private val _addresses = MutableLiveData<List<Address>>()
    val addresses: LiveData<List<Address>> get() = _addresses

    fun updateState(newState: OfficeState) {
        _fieldsOffice.value = newState
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
            _appState.value = AppState.Loading
            when (val result =
                createCompanyUseCase(_fieldsOffice.value.name, _fieldsOffice.value.shortName, _fieldsOffice.value.addressId, _fieldsOffice.value.activityId,_fieldsOffice.value.oneFounder)) {
                is Rezult.Success -> {
                    _appState.value = AppState.Success
                }
                is Rezult.Failure -> {
                    _appState.value =
                        AppState.Error(result.exception.message ?: "ошибка создания ооо")
                }
            }
        }
    }

}