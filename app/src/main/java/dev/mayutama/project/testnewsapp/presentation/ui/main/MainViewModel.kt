package dev.mayutama.project.testnewsapp.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mayutama.project.testnewsapp.domain.model.Category
import dev.mayutama.project.testnewsapp.domain.useCase.category.GetCategoriesUseCase
import dev.mayutama.project.testnewsapp.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {
    private val _category = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val category: StateFlow<UiState<List<Category>>> get() = _category

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart { _category.value = UiState.Loading }
                .collect{ result ->
                    result.onSuccess {
                        _category.value = UiState.Success(it)
                    }
                    result.onFailure {
                        _category.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
                    }
                }
        }
    }
}