package org.financeapps.plutusfinance

import android.content.Context
import org.financeapps.plutusfinance.features.accounts.di.accountsModule
import org.financeapps.plutusfinance.features.auth.di.authModule
import org.financeapps.plutusfinance.features.backend.di.backendModule
import org.financeapps.plutusfinance.features.login.di.loginModule
import org.financeapps.plutusfinance.features.storage.di.storageModule
import org.financeapps.plutusfinance.features.transactions.di.transactionsModule
import org.financeapps.plutusfinance.features.users.di.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

// TODO: Split each feature in a separate gradle module

object DependencyInjection {
    fun with(applicationContext: Context) {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                accountsModule,
                transactionsModule,
                usersModule,
                storageModule,
                authModule,
                backendModule,
                loginModule
            )
        }
    }
}