package com.tiger.ar.museum

import com.tiger.ar.museum.common.BaseApplication
import com.tiger.ar.museum.common.extension.setApplication

class MuseumArApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }
}
