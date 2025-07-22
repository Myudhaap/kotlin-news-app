package dev.mayutama.project.testnewsapp.presentation.ui.article

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.mayutama.project.testnewsapp.databinding.ActivityArticleBinding
import dev.mayutama.project.testnewsapp.util.Util
import dev.mayutama.project.testnewsapp.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityArticleBinding::inflate)
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var source: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        source = intent.getStringExtra(EXTRA_SOURCE) ?: ""
        articleAdapter = ArticleAdapter()
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvArticle.let {
            it.layoutManager = layoutManager
            it.adapter = articleAdapter
        }
        observeGetProducts()

        binding.edtSearch.addTextChangedListener { text ->
            val query = text.toString()
            articleViewModel.search(query)
        }
    }

    private fun observeGetProducts() {
        lifecycleScope.launch {
            // dummy
//            articleAdapter.submitData(PagingData.from(listOf(
//                Article(
//                    ArticleSource("test", "test"),
//                    "Test",
//                    "Test",
//                    "Test",
//                    "https://abcnews.go.com/Health/us-measles-cases-hit-highest-number-33-years/story?id=123564379",
//                    "https://i.abcnewsfe.com/a/96cbf0af-721a-49d6-8ca6-c3054c6b7bfb/measles-sign-ap-jt-250311_1741732269895_hpMain_16x9.jpg?w=1600",
//                    "test",
//                    "test"
//                )
//            )))
            articleViewModel.getArticle(source).collectLatest { pagingData ->
                articleAdapter.submitData(pagingData)
            }
        }

        articleAdapter.addLoadStateListener { loadState ->
            val isLoading = loadState.source.refresh is LoadState.Loading
            val isSuccess = loadState.source.refresh is LoadState.NotLoading
            val isError = loadState.source.refresh is LoadState.Error
            val isEmpty = loadState.refresh is LoadState.NotLoading && articleAdapter.itemCount == 0

            if (isLoading) {
                showLoading()
            }

            if (isSuccess) {
                hideLoading()
            }

            if (isError) {
                val error = (loadState.source.refresh as LoadState.Error).error
                Util.showToast(this, error.message ?: "Unknown Error")
                hideLoading()
            }

            binding.tvNotFound.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.rvArticle.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
    }

    private fun showLoading(){
        binding.loadingLayout.root.visibility = View.VISIBLE
        Util.disableScreenAction(window)
    }

    private fun hideLoading(){
        binding.loadingLayout.root.visibility = View.GONE
        Util.enableScreenAction(window)
    }

    companion object {
        val EXTRA_SOURCE = "extra_source"
    }
}