// AI-generated: Hilt module to provide Room database and DAO
package com.shankarkakumani.data.di

import android.content.Context
import androidx.room.Room
import com.shankarkakumani.data.client.room.RoomConfig
import com.shankarkakumani.data.client.room.dao.ExpenseDao
import com.shankarkakumani.data.client.room.database.SmartExpenseDatabase
import com.shankarkakumani.data.datasource.ExpenseLocalDataSource
import com.shankarkakumani.data.datasource.RoomExpenseLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindExpenseLocalDataSource(
        impl: RoomExpenseLocalDataSource
    ): ExpenseLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): SmartExpenseDatabase =
            Room.databaseBuilder(
                context,
                SmartExpenseDatabase::class.java,
                RoomConfig.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        @Provides
        @Singleton
        fun provideExpenseDao(db: SmartExpenseDatabase): ExpenseDao = db.expenseDao()
    }
}


