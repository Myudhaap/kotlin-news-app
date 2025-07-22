package dev.mayutama.project.testnewsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import dev.mayutama.project.testnewsapp.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM mst_category")
    fun getAll(): Flow<List<CategoryEntity>>
}