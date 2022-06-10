package org.financeapps.plutusfinance.features.auth.internal

import org.financeapps.plutusfinance.features.auth.AuthService
import org.financeapps.plutusfinance.features.auth.vo.Token
import org.financeapps.plutusfinance.features.users.repository.RemoteUserRepository

/**
 * A wrapper around a [RemoteUserRepository] that will call whenever we need to get a user token.
 * If requirements change, the rest of the app will still be using [AuthService], but the
 * mechanism of how we get the token can be completely different.
 */
class AuthServiceImpl(remoteUserRepository: RemoteUserRepository) :
    AuthService {

    /**
     * A great mechanism to get/fetch a token based on token's status.
     * Please class description for more information.
     */
    private val getOrFetchUserToken =
        GetOrFetchUserTokenUseCase(TokenCacheFacade(remoteUserRepository))

    override suspend fun getUserToken(appToken: String): Token {
        return getOrFetchUserToken(appToken)
    }
}