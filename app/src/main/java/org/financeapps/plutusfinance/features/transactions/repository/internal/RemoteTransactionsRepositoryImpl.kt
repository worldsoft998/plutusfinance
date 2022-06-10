package org.financeapps.plutusfinance.features.transactions.repository.internal

import org.financeapps.plutusfinance.features.auth.di.APP_TOKEN
import org.financeapps.plutusfinance.features.transactions.dto.toDTO
import org.financeapps.plutusfinance.features.transactions.dto.toDomainModel
import org.financeapps.plutusfinance.features.transactions.repository.RemoteTransactionsRepository
import org.financeapps.plutusfinance.features.transactions.vo.Transaction
import org.financeapps.plutusfinance.features.auth.AuthService
import org.financeapps.plutusfinance.features.backend.BackendApi

/**
 * Implementation of a repository that uses a remote data source to work with
 * account transactions.
 *
 * [backendApi] can be a Retrofit instance here.. but it isn't.
 * We faked it.. Though its just an interface.. only the dependency
 * injection framework knows the actual implementation
 *
 * Every call requires a user token, so we'll use the [authService] to get a valid token.
 * That could be cached (if valid) or will fetch a new one. Please see how that actually works.
 */
class RemoteTransactionsRepositoryImpl(
    private val backendApi: BackendApi,
    private val authService: AuthService
) :
    RemoteTransactionsRepository {

    override suspend fun getTransactions(accountId: Int): List<Transaction> =
        backendApi.fetchTransactions(authService.getUserToken(APP_TOKEN).accessToken, accountId)
            .map { it.toDomainModel() }


    override suspend fun addTransaction(transaction: Transaction) {
        backendApi.addTransaction(authService.getUserToken(APP_TOKEN).accessToken, transaction.toDTO())
    }
}