package dev.mayutama.project.testnewsapp.data.remote.dto.res

import com.google.gson.annotations.SerializedName


data class SourceResponse(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("sources")
    val sources: List<ItemSourceResponse>
)

data class ItemSourceResponse(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("category")
    val category: String,
    @field:SerializedName("language")
    val language: String,
    @field:SerializedName("country")
    val country: String
)
