package dev.mayutama.project.testnewsapp.presentation.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.mayutama.project.testnewsapp.util.viewBinding
import dev.mayutama.project.testnewsapp.R
import dev.mayutama.project.testnewsapp.databinding.ActivityMainBinding
import dev.mayutama.project.testnewsapp.presentation.state.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        init()
    }

    private fun init() {
        setRvCategory()
        observeCategory()
    }

    private fun setRvCategory(){
        categoryAdapter = CategoryAdapter()
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvCategory.let {
            it.layoutManager = layoutManager
            it.adapter = categoryAdapter
        }
    }

    private fun observeCategory() {
        lifecycleScope.launch {
            mainViewModel.category.collect{state ->
                when(state) {
                    is UiState.Loading -> println("Lagi loading")
                    is UiState.Success -> {
                        println(state.data)
                        categoryAdapter.submitList(state.data)
                    };
                    is UiState.Error -> println(state.message)
                }
            }
        }
    }
}