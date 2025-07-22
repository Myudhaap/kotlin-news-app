package dev.mayutama.project.testnewsapp.presentation.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.mayutama.project.testnewsapp.databinding.ItemCategoryBinding
import dev.mayutama.project.testnewsapp.domain.model.Category
import dev.mayutama.project.testnewsapp.presentation.ui.source.SourceActivity

class CategoryAdapter:
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK)
{
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.tvTitle.text = item.name

            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, SourceActivity::class.java).apply {
                    putExtra(SourceActivity.EXTRA_CATEGORY, item.code)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Category>(){
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}