package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.SignUpState
import com.example.ppmob.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _fieldsSignUp = MutableStateFlow(SignUpState())
    val fieldsSignUp: StateFlow<SignUpState> = _fieldsSignUp.asStateFlow()

    private val _appState = MutableStateFlow<AppState>(AppState.Initializing)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    fun updateState(newState: SignUpState) {
        _fieldsSignUp.value = newState
    }

    fun signUp() {
        viewModelScope.launch {
            if (validateFields()
            ) {
                _appState.value =
                    AppState.Loading
                when (val result = signUpUseCase(
                    _fieldsSignUp.value.email,
                    _fieldsSignUp.value.password
                )) {
                    is Rezult.Success -> {
                        _appState.value = AppState.Success
                    }

                    is Rezult.Failure -> {
                        _appState.value =
                            AppState.Error(result.exception.message ?: "Registration error")
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        // Сбросить ошибки
        _fieldsSignUp.value = _fieldsSignUp.value.copy(
            errorEmail = false,
            errorPassword = false
        )

        // Проверка email
        if (_fieldsSignUp.value.email.isBlank()) {
            _fieldsSignUp.value.errorEmail = true
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_fieldsSignUp.value.email).matches()) {
            _fieldsSignUp.value.errorEmail = true
            isValid = false
        } else {
            _fieldsSignUp.value.errorEmail = false
        }

        // Проверка на пустой пароль
        if (_fieldsSignUp.value.password.isBlank()) {
            _fieldsSignUp.value.errorPassword = true
            isValid = false
        }
        // Проверка длины пароля
        else if (_fieldsSignUp.value.password.length < 6) {
            _fieldsSignUp.value.errorPassword = true
            isValid = false
            _appState.value = AppState.Error("Пароль должен содержать не менее 6 символов")
        }
        // Проверка совпадения паролей
        else if (_fieldsSignUp.value.password != _fieldsSignUp.value.confirmPassword) {
            _fieldsSignUp.value.errorPassword = true
            isValid = false
            _appState.value = AppState.Error("Пароли не совпадают")
        }
        // Проверка на подтверждение пароля
        else if (_fieldsSignUp.value.confirmPassword.isBlank()) {
            _fieldsSignUp.value.errorPassword = true
            isValid = false
            _appState.value = AppState.Error("Подтвердите пароль")
        }
        else {
            _fieldsSignUp.value.errorPassword = false
        }


        if (!isValid && (_appState.value as? AppState.Error)?.message.isNullOrEmpty()) {
            _appState.value = AppState.Error("Заполните все поля корректно")
        }

        return isValid
    }
}