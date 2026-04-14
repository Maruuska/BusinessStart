package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.SignInState
import com.example.ppmob.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _fieldsSignIn = MutableStateFlow(SignInState())  //поля экрана signIn
    val fieldsSignIn: StateFlow<SignInState> = _fieldsSignIn.asStateFlow()

    private val _appState =
        MutableStateFlow<AppState>(AppState.Initializing) // состояние приложения
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    fun updateState(newState: SignInState) {
        _fieldsSignIn.value = newState
    }

    // Публичная функция для авторизации
    fun signIn() {
        viewModelScope.launch {
            if (validateFields()) {  // проверка корректности полей
                _appState.value = AppState.Loading
                when (val result =
                    signInUseCase(_fieldsSignIn.value.email, _fieldsSignIn.value.password)) {
                    is Rezult.Success -> {
                        _appState.value = AppState.Success
                    }

                    is Rezult.Failure -> {
                        _appState.value =
                            AppState.Error(result.exception.message ?: "Authorization error")
                    }
                }
            }
        }
    }

    // приватная функция для проверки корректности введенных данных
    private fun validateFields(): Boolean {
        var isValid = true

        _fieldsSignIn.value = _fieldsSignIn.value.copy(
            errorEmail = false,
            errorPassword = false,
        )

        //оба поля пустые
        if (_fieldsSignIn.value.email.isBlank() && _fieldsSignIn.value.password.isBlank()) {
            _fieldsSignIn.value.errorEmail = true
            _fieldsSignIn.value.errorPassword = true
            _appState.value = AppState.Error("")
            return false
        } else {
            _fieldsSignIn.value.errorEmail = false
            _fieldsSignIn.value.errorPassword = false
        }

        //email не соответствует шаблону или пустой
        if (_fieldsSignIn.value.email.isBlank() || android.util.Patterns.EMAIL_ADDRESS.matcher(
                _fieldsSignIn.value.email
            ).matches() == false
        ) {
            _fieldsSignIn.value.errorEmail = true
            isValid = false
            _appState.value = AppState.Error("")
        } else {
            _fieldsSignIn.value.errorEmail = false
        }

        //пароль пустой
        if (_fieldsSignIn.value.password.isBlank()) {
            _fieldsSignIn.value.errorPassword = true
            isValid = false
            _appState.value = AppState.Error("")
        } else {
            _fieldsSignIn.value.errorPassword = false
        }

        //пароль меньше 6 символов
        if (_fieldsSignIn.value.password.length < 6) {
            _fieldsSignIn.value.errorPassword = true
            isValid = false
            _appState.value = AppState.Error("")
        } else {
            _fieldsSignIn.value.errorPassword = false
        }

        return isValid
    }

}