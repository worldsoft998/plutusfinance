package org.financeapps.plutusfinance.features.backend.di

import org.financeapps.plutusfinance.features.backend.BackendApi
import org.financeapps.plutusfinance.features.backend.internal.FakeBackendImpl
import org.koin.dsl.module

/**
 * Created by Mark Chen on 12-5-19.
 */

val backendModule = module {
    single { FakeBackendImpl() as BackendApi }
}