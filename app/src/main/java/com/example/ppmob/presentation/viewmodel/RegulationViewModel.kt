package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ppmob.domain.state.OfficeState
import com.example.ppmob.domain.state.RegulationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegulationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _fields = MutableStateFlow(RegulationState())
    val fields: StateFlow<RegulationState> = _fields.asStateFlow()

    fun updateState(newState: RegulationState) {
        _fields.value = newState
    }
}