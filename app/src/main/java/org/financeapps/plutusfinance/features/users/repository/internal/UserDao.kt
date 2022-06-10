package org.financeapps.plutusfinance.features.users.repository.internal

import androidx.room.*
import org.financeapps.plutusfinance.features.users.entity.UserEntity

/**
 * Created by Mark Chen on 4-5-19.
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE id=:id")
    suspend fun deleteUser(id: Int)
}