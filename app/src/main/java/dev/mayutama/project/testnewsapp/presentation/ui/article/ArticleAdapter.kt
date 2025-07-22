package dev.mayutama.project.testnewsapp.presentation.ui.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mayutama.project.testnewsapp.databinding.ItemArticleBinding
import dev.mayutama.project.testnewsapp.domain.model.Article
import dev.mayutama.project.testnewsapp.presentation.ui.article.detailArticle.DetailArticleActivity

class ArticleAdapter:
    PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK)
{
    inner class ArticleViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.tvTitle.text = item.title
            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .into(binding.imgArticle)

            binding.root.setOnClickListener {
                println("url: ${item.url}")
                val intent = Intent(binding.root.context, DetailArticleActivity::class.java).apply {
                    putExtra(DetailArticleActivity.EXTRA_URL, item.url)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ArticleViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}