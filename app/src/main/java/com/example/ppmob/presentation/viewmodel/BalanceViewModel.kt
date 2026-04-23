package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ppmob.domain.state.BalanceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor() : ViewModel() {

    private val _fieldsBalance = MutableStateFlow(BalanceState())
    val fieldsBalance: StateFlow<BalanceState> = _fieldsBalance.asStateFlow()

    fun updateState(newState: BalanceState) {
        _fieldsBalance.value = newState
    }

    fun updateRowSum(index: Int, newSum: String) {
        val currentState = _fieldsBalance.value
        val updatedRows = currentState.rows.toMutableList()
        updatedRows[index] = updatedRows[index].copy(sum = newSum)
        _fieldsBalance.value = currentState.copy(rows = updatedRows)
    }

    fun updateRowType(index: Int, newType: String) {
        val currentState = _fieldsBalance.value
        val updatedRows = currentState.rows.toMutableList()
        updatedRows[index] = updatedRows[index].copy(type = newType)
        _fieldsBalance.value = currentState.copy(rows = updatedRows)
    }
}