package org.financeapps.plutusfinance.features.users.repository

import org.financeapps.plutusfinance.features.users.vo.User

/**
 * Created by Mark Chen on 4-5-19.
 */

/**
 * Repository that works with local data source.
 * It can be mocked or faked in tests or debug builds.
 */
interface LocalUsersRepository {
    suspend fun getLoggedInUser(): User?
    suspend fun createUser(user: User)
    suspend fun login(user: User)
    suspend fun deleteUserSettings()
}

