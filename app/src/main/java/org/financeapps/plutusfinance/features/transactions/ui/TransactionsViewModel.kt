package org.financeapps.plutusfinance.features.transactions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.PagedList
import org.financeapps.plutusfinance.features.accounts.vo.Account
import org.financeapps.plutusfinance.features.transactions.usecase.GetAccountBalanceUseCase
import org.financeapps.plutusfinance.features.transactions.usecase.RefreshAccountTransactionsUseCase
import org.financeapps.plutusfinance.features.transactions.vo.Transaction

/**
 * Created by Mark Chen on 11-5-19.
 */

class TransactionsViewModel(
    private val refreshAccountTransactionsUseCase: RefreshAccountTransactionsUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase,
    private val account: Account
) : ViewModel() {
    // Create a coroutine live data (https://developer.android.com/topic/libraries/architecture/coroutines)
    // Query the business logic to get user bank accounts
    // And then map them to something that the UI can render
    val transactionListStream: LiveData<PagedList<Transaction>> = liveData {
        emitSource(refreshAccountTransactionsUseCase(account))
    }

    val accountBalanceStream = liveData<String> {
        emitSource(getAccountBalanceUseCase(account.id).map { it.toPlainString() })
    }

    override fun onCleared() {
        super.onCleared()
        // Since we are using an internal coroutine scope in this use case,
        // we have to make sure we cancel the pending jobs (if any)
        refreshAccountTransactionsUseCase.cancel()
    }
}

