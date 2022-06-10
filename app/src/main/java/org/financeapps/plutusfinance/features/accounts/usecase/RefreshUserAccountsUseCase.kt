package org.financeapps.plutusfinance.features.accounts.usecase

import org.financeapps.plutusfinance.features.accounts.repository.LocalAccountsRepository
import org.financeapps.plutusfinance.features.accounts.repository.RemoteAccountsRepository
import org.financeapps.plutusfinance.features.common.usecase.UseCase
import org.financeapps.plutusfinance.features.common.vo.Result
import org.financeapps.plutusfinance.features.common.vo.catchResult
import org.financeapps.plutusfinance.features.common.vo.onFailure
import org.financeapps.plutusfinance.features.users.repository.LocalUsersRepository

/**
 * Use case that will update the local repository with data from the remote.
 * If the use case does not error, then the operation has been successful.
 */
class RefreshUserAccountsUseCase(
    private val localUsersRepository: LocalUsersRepository,
    private val localAccountsRepository: LocalAccountsRepository,
    private val remoteAccountsRepository: RemoteAccountsRepository
) : UseCase<Unit, Result<Unit>> {
    override suspend fun invoke(param: Unit): Result<Unit> =
        catchResult {
            val user = localUsersRepository.getLoggedInUser()!!
            // Fetch from network
            val accounts = remoteAccountsRepository.fetchAccounts(user)
            if (accounts.isNotEmpty()) {
                // Store to disk
                // TODO: We should make a diff and remove the accounts that have been deleted on the server
                // This of course won't happen in our example since everything is deterministic.
                localAccountsRepository.storeAccounts(user, accounts)
            } else {
                localAccountsRepository.deleteAccounts(user)
            }
        }.onFailure {
            // Log
            it.printStackTrace()
        }
}