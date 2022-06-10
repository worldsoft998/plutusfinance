package org.financeapps.plutusfinance.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.financeapps.plutusfinance.features.common.vo.Result
import org.financeapps.plutusfinance.features.users.usecase.LogInUserUseCase
import org.financeapps.plutusfinance.features.users.vo.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

class LoginViewModel(
    private val logInUserUseCase: LogInUserUseCase
) : ViewModel() {

    /**
     * Executes a login use case in a coroutine, scoped to the view model,
     * but the result will be awaited in a different scope (lifecycle scope of a fragment).
     * This way we can immediately return a result, instead of creating a
     * separate live data stream just to return the result.
     */
    suspend fun login(pinCode: CharArray): Result<User> {
        val deferredResult = CompletableDeferred<Result<User>>()

        viewModelScope.launch {
            deferredResult.complete(logInUserUseCase(pinCode))
        }

        return deferredResult.await()
    }
}