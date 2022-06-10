package org.financeapps.plutusfinance.features.accounts.di

import org.financeapps.plutusfinance.features.accounts.repository.LocalAccountsRepository
import org.financeapps.plutusfinance.features.accounts.repository.RemoteAccountsRepository
import org.financeapps.plutusfinance.features.accounts.repository.internal.LocalAccountsRepositoryImpl
import org.financeapps.plutusfinance.features.accounts.repository.internal.RemoteAccountsRepositoryImpl
import org.financeapps.plutusfinance.features.accounts.ui.AccountListViewModel
import org.financeapps.plutusfinance.features.accounts.usecase.FetchUserAccountsUseCase
import org.financeapps.plutusfinance.features.accounts.usecase.RefreshUserAccountsUseCase
import org.financeapps.plutusfinance.features.storage.database.AppDatabase
import org.financeapps.plutusfinance.features.transactions.usecase.GetAccountBalanceUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Mark Chen on 12-5-19.
 */

val accountsModule = module {
    viewModel { AccountListViewModel(get(), get()) }
    single { RefreshUserAccountsUseCase(get(), get(), get()) }
    single { FetchUserAccountsUseCase(get(), get(), get()) }
    single { RemoteAccountsRepositoryImpl(get(), get()) as RemoteAccountsRepository }
    single { LocalAccountsRepositoryImpl(get()) as LocalAccountsRepository }
    single { get<AppDatabase>().accountDao() }
    single { GetAccountBalanceUseCase(get(), get()) }
}