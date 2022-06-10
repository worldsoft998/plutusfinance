package org.financeapps.plutusfinance.features.users.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.financeapps.plutusfinance.features.users.vo.User

/**
 * Created by Mark Chen on 11-5-19.
 */

/**
 * Entity that represents a User in a Room database
 */
@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(@PrimaryKey val id: Int, val firstName: String, val lastName: String) {
    companion object {
        internal const val TABLE_NAME = "user"
    }
}

/**
 * Mappers for other layers of the app
 */
fun UserEntity.toModel() = User(
    id = id,
    firstName = firstName,
    lastName = lastName
)

fun User.toEntity() = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName
)