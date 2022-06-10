package org.financeapps.plutusfinance.features.users.di

import org.financeapps.plutusfinance.features.storage.database.AppDatabase
import org.financeapps.plutusfinance.features.users.repository.LocalUsersRepository
import org.financeapps.plutusfinance.features.users.repository.RemoteUserRepository
import org.financeapps.plutusfinance.features.users.repository.internal.LocalUsersRepositoryImpl
import org.financeapps.plutusfinance.features.users.repository.internal.RemoteUserRepositoryImpl
import org.koin.dsl.module

/**
 * Created by Mark Chen on 12-5-19.
 */

val usersModule = module {
    single { get<AppDatabase>().userDao() }
    single { RemoteUserRepositoryImpl(get()) as RemoteUserRepository }
    single { LocalUsersRepositoryImpl(get()) as LocalUsersRepository }
}