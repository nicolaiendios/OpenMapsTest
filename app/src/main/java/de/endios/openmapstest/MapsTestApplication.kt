package de.endios.openmapstest

import android.app.Application
import io.openmobilemaps.mapscore.MapsCore

class MapsTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MapsCore.initialize()
    }
}

/**
 * Creates an Log Tag String which is often useful when logging
 */
val Any.TAG: String get() = javaClass.simpleName
