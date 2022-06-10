package org.financeapps.plutusfinance.features.transactions.ui

import org.financeapps.plutusfinance.features.accounts.repository.LocalAccountsRepository
import org.financeapps.plutusfinance.features.accounts.vo.Account
import org.financeapps.plutusfinance.features.common.usecase.UseCase
import org.financeapps.plutusfinance.features.users.repository.LocalUsersRepository

/**
 * Returns bank accounts other than the currently selected one
 */
class GetAvailableAccountsForTransactionUseCase(
    private val usersRepository: LocalUsersRepository,
    private val localAccountsRepository: LocalAccountsRepository
) :
    UseCase<Account, List<Account>> {
    override suspend fun invoke(param: Account) =
        localAccountsRepository.getAccounts(usersRepository.getLoggedInUser()!!)
            .filter { it.id != param.id }
}