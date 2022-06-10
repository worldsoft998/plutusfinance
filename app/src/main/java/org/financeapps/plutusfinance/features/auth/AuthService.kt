package org.financeapps.plutusfinance.features.auth

import org.financeapps.plutusfinance.features.auth.vo.Token

/**
 * Service that knows how to get us a token.
 * Will not over-complicate things with refresh tokens. We'll use the same function for both
 * fetching a new one and also refreshing.
 */
interface AuthService {
    suspend fun getUserToken(appToken: String): Token
}