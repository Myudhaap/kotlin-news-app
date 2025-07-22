package dev.mayutama.project.testnewsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.mayutama.project.testnewsapp.data.local.dao.CategoryDao
import dev.mayutama.project.testnewsapp.data.local.dao.SourceDao
import dev.mayutama.project.testnewsapp.data.local.entity.CategoryEntity
import dev.mayutama.project.testnewsapp.data.local.entity.SourceEntity

@Database(entities = [CategoryEntity::class, SourceEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun sourceDao(): SourceDao
}

val MIGRATION1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            INSERT INTO mst_category
            VALUES
            (1, 'business', 'Business'),
            (2, 'entertainment', 'Entertainment'),
            (3, 'general', 'General'),
            (4, 'health', 'Health'),
            (5, 'science', 'Science'),
            (6, 'sports', 'Sports'),
            (7, 'technology', 'Technology');
        """.trimIndent())
    }
}