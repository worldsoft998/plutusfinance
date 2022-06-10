package org.financeapps.plutusfinance.features.accounts.repository

import androidx.lifecycle.LiveData
import org.financeapps.plutusfinance.features.accounts.vo.Account
import org.financeapps.plutusfinance.features.users.vo.User
import java.math.BigDecimal

/**
 * Created by Mark Chen on 4-5-19.
 */

/**
 * Repository that works with local data source.
 * It can be mocked or faked in tests or debug builds.
 */
interface LocalAccountsRepository {
    suspend fun getAccounts(user: User): List<Account>
    suspend fun getAccountsRefreshing(user: User): LiveData<List<Account>>
    suspend fun getAccountBalanceRefreshing(accountId: Int): LiveData<BigDecimal>
    suspend fun storeAccounts(user: User, accounts: List<Account>)
    suspend fun updateAccountBalance(accountId: Int, newBalance: BigDecimal)
    suspend fun deleteAccounts(user: User)
}