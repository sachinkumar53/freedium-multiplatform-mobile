package com.sachin.freedium

import android.app.Application
import multiplatform.network.cmptoast.AppContext

class Freedium : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.set(applicationContext)
    }
}