package dev.mayutama.project.testnewsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mayutama.project.testnewsapp.data.local.dao.CategoryDao
import dev.mayutama.project.testnewsapp.data.local.dao.SourceDao
import dev.mayutama.project.testnewsapp.data.local.database.AppDatabase
import dev.mayutama.project.testnewsapp.data.local.database.MIGRATION1_2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "db_news")
            .addMigrations(MIGRATION1_2)
//            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideSourceDao(db: AppDatabase): SourceDao = db.sourceDao()
}