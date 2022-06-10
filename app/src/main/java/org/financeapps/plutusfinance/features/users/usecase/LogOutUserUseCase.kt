package org.financeapps.plutusfinance.features.users.usecase

import org.financeapps.plutusfinance.features.common.usecase.UseCase
import org.financeapps.plutusfinance.features.users.repository.LocalUsersRepository

/**
 * Use case that knows what to do when user has to be logged out.
 * Currently unused, because there is no real implementation.
 * We just exit the app instead. Its just for demo purposes.
 */
class LogOutUserUseCase(private val localUsersRepository: LocalUsersRepository) :
    UseCase<Unit, Result<Unit>> {

    override suspend fun invoke(param: Unit) =
        runCatching { localUsersRepository.deleteUserSettings() }
}