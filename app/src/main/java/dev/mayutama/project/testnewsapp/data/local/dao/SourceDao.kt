package dev.mayutama.project.testnewsapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mayutama.project.testnewsapp.data.local.entity.SourceEntity

@Dao
interface SourceDao {
    @Query("SELECT * FROM mst_source WHERE name like '%' || :query || '%' OR description like '%' || :query || '%' ORDER BY name ASC")
    fun getAll(query: String): PagingSource<Int, SourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<SourceEntity>)

    @Query("DELETE FROM mst_source")
    suspend fun clearAll()
}