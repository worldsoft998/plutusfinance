package org.financeapps.plutusfinance.features.storage.di

import com.commonsware.cwac.saferoom.SafeHelperFactory
import org.financeapps.plutusfinance.features.auth.di.USER_PIN
import org.financeapps.plutusfinance.features.storage.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Mark Chen on 12-5-19.
 */

val storageModule = module {
    single { AppDatabase.getInstance(androidContext(), get()) }
    // TODO: Assisted injection.
    single { SafeHelperFactory(USER_PIN) }
}