package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.domain.state.AppState
import com.example.ppmob.domain.state.BankState
import com.example.ppmob.domain.state.OfficeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor() : ViewModel() {

    private val _fieldsBank = MutableStateFlow(BankState())
    val fieldsBank: StateFlow<BankState> = _fieldsBank.asStateFlow()

    fun updateState(newState: BankState) {
        _fieldsBank.value = newState
    }
}