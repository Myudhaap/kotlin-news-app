package dev.mayutama.project.testnewsapp.presentation.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mayutama.project.testnewsapp.domain.model.Article
import dev.mayutama.project.testnewsapp.domain.useCase.article.ArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleUseCase: ArticleUseCase
): ViewModel() {
    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> get() = _searchQuery

    fun getArticle(source: String): StateFlow<PagingData<Article>> = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            articleUseCase.getAllArticleUseCase(source, query)
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun search(query: String) {
        _searchQuery.value = query
    }
}