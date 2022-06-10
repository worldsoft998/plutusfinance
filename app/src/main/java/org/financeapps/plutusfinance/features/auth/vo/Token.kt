package org.financeapps.plutusfinance.features.auth.vo

/**
 * Created by Mark Chen on 12-5-19.
 */

/**
 * Token, that is a DTO and a VO at the same time.
 *
 * Note: We didn't make a refresh token, just to keep things simpler.
 */
data class Token(val accessToken: String,
                 val expireDate: Long)