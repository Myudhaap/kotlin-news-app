package dev.mayutama.project.testnewsapp.presentation.ui.source

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.mayutama.project.testnewsapp.util.viewBinding
import dev.mayutama.project.testnewsapp.databinding.ActivitySourcesBinding
import dev.mayutama.project.testnewsapp.presentation.state.UiState
import dev.mayutama.project.testnewsapp.util.Util
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourceActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivitySourcesBinding::inflate)
    private val sourceViewModel: SourceViewModel by viewModels()
    private lateinit var sourceAdapter: SourceAdapter

    private lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        category = intent.getStringExtra(EXTRA_CATEGORY) ?: ""
        sourceAdapter = SourceAdapter()
        val layoutManager = LinearLayoutManager(this@SourceActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvSource.let {
            it.adapter = sourceAdapter
            it.layoutManager = layoutManager
        }

        observeRefresh()
        observeGetSources()
        sourceViewModel.refresh(category)

        binding.edtSearch.addTextChangedListener { text ->
            val query = text.toString()
            sourceViewModel.search(query)
        }
    }

    private fun observeRefresh() {
        lifecycleScope.launch {
            sourceViewModel.refreshState.collect{state ->
                when(state) {
                    is UiState.Loading -> showLoading()
                    is UiState.Success -> {
                        hideLoading()
                    };
                    is UiState.Error -> {
                        hideLoading()
                        println(state.message)
                    }
                }
            }
        }
    }

    private fun observeGetSources() {
        lifecycleScope.launch {
            sourceViewModel.sourceFlow.collectLatest { pagingData ->
                sourceAdapter.submitData(pagingData)
            }
        }

        sourceAdapter.addLoadStateListener { loadState ->
            Log.d("SourceActivity", "LoadState: ${loadState.source.refresh}")
            val isLoading = loadState.source.refresh is LoadState.Loading
            val isSuccess = loadState.source.refresh is LoadState.NotLoading
            val isError = loadState.source.refresh is LoadState.Error
            val isEmpty = loadState.refresh is LoadState.NotLoading && sourceAdapter.itemCount == 0

            if (isLoading) {
                showLoading()
            }

            if (isSuccess) {
                println("Loaddddddddddd")
                hideLoading()
            }

            if (isError) {
                val error = (loadState.source.refresh as LoadState.Error).error
                Util.showToast(this, error.message ?: "Unknown Error")
                hideLoading()
            }

            binding.tvNotFound.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.rvSource.visibility = if (isEmpty) View.GONE else View.VISIBLE
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
        val EXTRA_CATEGORY: String = "extra_category";
    }
}