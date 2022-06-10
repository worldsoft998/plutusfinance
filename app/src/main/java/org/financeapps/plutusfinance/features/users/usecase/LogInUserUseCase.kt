package org.financeapps.plutusfinance.features.users.usecase

import org.financeapps.plutusfinance.features.common.usecase.UseCase
import org.financeapps.plutusfinance.features.common.vo.catchResult
import org.financeapps.plutusfinance.features.common.vo.Result
import org.financeapps.plutusfinance.features.common.vo.onFailure
import org.financeapps.plutusfinance.features.auth.AuthService
import org.financeapps.plutusfinance.features.auth.di.APP_TOKEN
import org.financeapps.plutusfinance.features.auth.di.USER_PIN
import org.financeapps.plutusfinance.features.users.repository.LocalUsersRepository
import org.financeapps.plutusfinance.features.users.repository.RemoteUserRepository
import org.financeapps.plutusfinance.features.users.vo.User

typealias PinCode = CharArray

/**
 * Use case that attempts to log user in
 */
class LogInUserUseCase(
    private val localUsersRepository: LocalUsersRepository,
    private val remoteUserRepository: RemoteUserRepository,
    private val authService: AuthService
) :
    UseCase<CharArray, Result<User>> {

    override suspend fun invoke(param: PinCode) =
        catchResult {
            if (!tryOpenEncryptedDatabaseWith(param)) {
                throw WrongPasswordException()
            }
            val token = authService.getUserToken(APP_TOKEN)
            val user = remoteUserRepository.getPerson(token.accessToken)
            localUsersRepository.createUser(user)
            localUsersRepository.login(user)
            user
        }.onFailure {
            it.printStackTrace()
        }

    private fun tryOpenEncryptedDatabaseWith(param: PinCode): Boolean {
        // TODO: Instead of pin comparison, we should try opening the database with the pin
        // and not having the pin hardcoded in the app :)
        return param.contentEquals(USER_PIN)
    }
}

class WrongPasswordException : RuntimeException()