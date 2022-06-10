package org.financeapps.plutusfinance.features.backend

import org.financeapps.plutusfinance.features.accounts.dto.AccountDTO
import org.financeapps.plutusfinance.features.accounts.vo.Account
import org.financeapps.plutusfinance.features.auth.vo.Token
import org.financeapps.plutusfinance.features.transactions.dto.TransactionDTO
import org.financeapps.plutusfinance.features.users.dto.UserDTO
import org.financeapps.plutusfinance.features.users.vo.User
import java.math.BigDecimal

/**
 * Since we are not going to communicate with an actual backend, we'll pretend as if there is one.
 * That is the public api /endpoints/ of our fake backend.
 * Notice that we expose DTOs here like [UserDTO].
 */
interface BackendApi {
    suspend fun fetchUserToken(username: String): Token
    suspend fun fetchPerson(token: String): UserDTO
    suspend fun fetchTransactions(userAccessToken: String, accountId: Int): List<TransactionDTO>
    suspend fun addTransaction(userAccessToken: String, transaction: TransactionDTO)
    suspend fun fetchAccounts(userAccessToken: String, user: User): List<AccountDTO>
    suspend fun fetchAccountBalance(userAccessToken: String, accountId: Int): BigDecimal
    suspend fun updateAccountBalance(userAccessToken: String, accountId: Int, newBalance: BigDecimal)
}