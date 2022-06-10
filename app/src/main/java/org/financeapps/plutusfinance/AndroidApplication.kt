package org.financeapps.plutusfinance

import android.app.Application

/**
 * Created by Mark Chen on June-4-2022.
 */

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DependencyInjection.with(this)
    }
}

