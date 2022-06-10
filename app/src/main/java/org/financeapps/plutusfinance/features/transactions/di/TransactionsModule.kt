package org.financeapps.plutusfinance.features.transactions.di

import org.financeapps.plutusfinance.R
import org.financeapps.plutusfinance.features.accounts.vo.Account
import org.financeapps.plutusfinance.features.storage.database.AppDatabase
import org.financeapps.plutusfinance.features.transactions.repository.LocalTransactionsRepository
import org.financeapps.plutusfinance.features.transactions.repository.RemoteTransactionsRepository
import org.financeapps.plutusfinance.features.transactions.repository.internal.LocalTransactionsRepositoryImpl
import org.financeapps.plutusfinance.features.transactions.repository.internal.RemoteTransactionsRepositoryImpl
import org.financeapps.plutusfinance.features.transactions.ui.AddTransactionUseCase
import org.financeapps.plutusfinance.features.transactions.ui.AddTransactionViewModel
import org.financeapps.plutusfinance.features.transactions.ui.GetAvailableAccountsForTransactionUseCase
import org.financeapps.plutusfinance.features.transactions.ui.TransactionsViewModel
import org.financeapps.plutusfinance.features.transactions.usecase.RefreshAccountTransactionsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Mark Chen on 12-5-19.
 */

val transactionsModule = module {
    viewModel { (account: Account) -> TransactionsViewModel(get(), get(), account) } // Assisted injection
    viewModel { (account: Account) -> AddTransactionViewModel(account, get(), get()) } // Assisted injection
    single { LocalTransactionsRepositoryImpl(get()) as LocalTransactionsRepository }
    single { RemoteTransactionsRepositoryImpl(get(), get()) as RemoteTransactionsRepository }
    single { get<AppDatabase>().transactionDao() }
    single {
        AddTransactionUseCase(
            androidContext().getString(R.string.deposit),
            androidContext().getString(R.string.withdraw),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory { RefreshAccountTransactionsUseCase(get(), get()) }
    factory { GetAvailableAccountsForTransactionUseCase(get(), get()) }
}