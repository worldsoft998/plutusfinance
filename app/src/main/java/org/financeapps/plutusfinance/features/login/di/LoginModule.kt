package org.financeapps.plutusfinance.features.login.di

import org.financeapps.plutusfinance.features.login.ui.LoginViewModel
import org.financeapps.plutusfinance.features.users.usecase.LogInUserUseCase
import org.financeapps.plutusfinance.features.users.usecase.LogOutUserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Mark Chen on 12-5-19.
 */

val loginModule = module {
    viewModel { LoginViewModel(get()) }
    single { LogInUserUseCase(get(), get(), get()) }
    single { LogOutUserUseCase(get()) }
}