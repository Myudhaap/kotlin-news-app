package dev.mayutama.project.testnewsapp.presentation.ui.source

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mayutama.project.testnewsapp.databinding.ItemSourceBinding
import dev.mayutama.project.testnewsapp.domain.model.Source
import dev.mayutama.project.testnewsapp.presentation.ui.article.ArticleActivity

class SourceAdapter: PagingDataAdapter<Source, SourceAdapter.SourceViewHolder>(DIFF_CALLBACK) {
    inner class SourceViewHolder(private val binding: ItemSourceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Source) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, ArticleActivity::class.java).apply {
                    putExtra(ArticleActivity.EXTRA_SOURCE, item.id)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        Log.d("SourceAdapter", "Binding item at position: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = ItemSourceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SourceViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Source>() {
            override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
                return  oldItem == newItem
            }
        }
    }
}