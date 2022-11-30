package de.endios.openmapstest

import android.app.Application
import io.openmobilemaps.mapscore.MapsCore

class MapsTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MapsCore.initialize()
    }
}