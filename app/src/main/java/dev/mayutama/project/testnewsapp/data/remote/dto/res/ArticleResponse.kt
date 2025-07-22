package dev.mayutama.project.testnewsapp.data.remote.dto.res

import com.google.gson.annotations.SerializedName

data class ArticleResponse (
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("totalResults")
    val totalResults: Int,
    @field:SerializedName("articles")
    val articles: List<ItemArticle>
)

data class ItemArticle(
    @field:SerializedName("source")
    val source: ArticleSource,
    @field:SerializedName("author")
    val author: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("urlToImage")
    val urlToImage: String,
    @field:SerializedName("publishedAt")
    val publishedAt: String,
    @field:SerializedName("content")
    val content: String
)

data class ArticleSource (
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String
)
