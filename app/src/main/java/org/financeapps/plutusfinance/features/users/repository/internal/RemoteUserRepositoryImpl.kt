package org.financeapps.plutusfinance.features.users.repository.internal

import org.financeapps.plutusfinance.features.users.dto.toModel
import org.financeapps.plutusfinance.features.auth.AuthService
import org.financeapps.plutusfinance.features.auth.vo.Token
import org.financeapps.plutusfinance.features.backend.BackendApi
import org.financeapps.plutusfinance.features.users.repository.RemoteUserRepository
import org.financeapps.plutusfinance.features.users.vo.User

/**
 * All calls to our (fake) backend are done here. The [backend] can be a Retrofit instance.
 */
class RemoteUserRepositoryImpl(private val backend: BackendApi) : RemoteUserRepository,
    AuthService {
    override suspend fun getUserToken(appToken: String): Token = backend.fetchUserToken(appToken)
    override suspend fun getPerson(token: String): User = backend.fetchPerson(token).toModel()
}