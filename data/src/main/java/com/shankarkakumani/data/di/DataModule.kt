// AI-generated: Hilt dependency injection module for the data layer
package com.shankarkakumani.data.di

import com.shankarkakumani.data.datasource.ExpenseInMemoryDataSource
import com.shankarkakumani.data.datasource.ExpenseLocalDataSource
import com.shankarkakumani.data.mapper.ExpenseMapper
import com.shankarkakumani.data.repository.ExpenseRepositoryImpl
import com.shankarkakumani.domain.repository.ExpenseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing data layer dependencies.
 * Configures repository implementations, data sources, and mappers.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    
    /**
     * Binds the repository implementation to the domain interface.
     * Uses abstract function for interface binding.
     */
    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository
    
    companion object {
        /**
         * Provides the expense mapper as a singleton.
         * Mapper is lightweight but singleton for consistency.
         */
        @Provides
        @Singleton
        fun provideExpenseMapper(): ExpenseMapper = ExpenseMapper()
    }
}
