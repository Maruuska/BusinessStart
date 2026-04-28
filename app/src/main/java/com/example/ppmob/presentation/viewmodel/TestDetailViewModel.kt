package com.example.ppmob.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestDetailViewModel @Inject constructor(
    //private val getTestsUseCase: GetTestsUseCase,  // получение теста со всеми вопросами и ответами к ним
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle["id"])  // id теста



}