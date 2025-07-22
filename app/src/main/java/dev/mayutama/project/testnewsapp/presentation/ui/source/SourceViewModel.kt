package dev.mayutama.project.testnewsapp.presentation.ui.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mayutama.project.testnewsapp.domain.model.Source
import dev.mayutama.project.testnewsapp.domain.useCase.source.SourceUseCase
import dev.mayutama.project.testnewsapp.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val sourceUseCase: SourceUseCase
): ViewModel() {
    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> get() = _searchQuery

    private val _refreshState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val refreshState: StateFlow<UiState<Unit>> get() = _refreshState

    val sourceFlow: StateFlow<PagingData<Source>> = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            sourceUseCase.getAllSourceUseCase(query ?: "")
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun refresh(category: String) {
        viewModelScope.launch {
            sourceUseCase.refreshSourceUseCase(category)
                .onStart { _refreshState.value = UiState.Loading }
                .collect{result ->
                    result.onSuccess { _refreshState.value = UiState.Success(Unit) }
                    result.onFailure { _refreshState.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error") }
                }
        }
    }
}