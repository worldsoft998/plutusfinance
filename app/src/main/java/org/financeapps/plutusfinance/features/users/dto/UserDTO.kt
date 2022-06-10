package org.financeapps.plutusfinance.features.users.dto

import org.financeapps.plutusfinance.features.users.vo.User
import java.util.*

/**
 * Created by Mark Chen on 11-5-19.
 */

/**
 * Data-Access-Object that represents the object we'll have to use when
 * working with our (fake) backend.
 */
data class UserDTO(val personId: Int = 0, val firstName: String, val lastName: String, val registrationDate: Date)

/**
 * Mapper for the domain layer of the app
 */
fun UserDTO.toModel() = User(
    id = personId,
    firstName = firstName,
    lastName = lastName
)