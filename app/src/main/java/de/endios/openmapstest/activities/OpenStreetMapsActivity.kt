package de.endios.openmapstest.activities

import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import de.endios.openmapstest.R
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polygon


class OpenStreetMapsActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        // This won't work unless you have imported this: org.osmdroid.config.Configuration.*
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, if you abuse osm's
        //tile servers will get you banned based on this string.

        //inflate and create the map
        setContentView(R.layout.activity_open_street_maps)

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.controller
        mapController.setZoom(14.0)
        val startPoint = GeoPoint(53.55, 10.0)
        mapController.setCenter(startPoint)

        map.addMarkers()
    }

    private fun MapView.addMarkers() {
        //your items
        val drawable =
            ContextCompat.getDrawable(this@OpenStreetMapsActivity, R.drawable.ic_baseline_place_24)?.apply {
                setTint(Color.CYAN)
            }
        val items = arrayListOf(
            OverlayItem("Title A", "Snippet", GeoPoint(53.55, 10.0))
                .apply { setMarker(drawable) },
            OverlayItem("Title B", "Snippet", GeoPoint(53.54, 10.01))
                .apply { setMarker(drawable) },
            OverlayItem("Title C", "Snippet", GeoPoint(53.56, 9.99))
                .apply { setMarker(drawable) },
        )
        val singleItem = arrayListOf(
            OverlayItem("Title A", "Snippet", GeoPoint(53.55, 10.0))
        )

        //the overlay
        val overlay = ItemizedOverlayWithFocus(
            singleItem,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
                    //do something
                    Log.d("MainActivity", "You clicked index: $index, item: $item")
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
                    return false
                }
            },
            context
        )
        overlay.setFocusItemsOnTap(true)

        val marker = Marker(map).apply {
            icon = drawable
            title = "Endios GmbH"
            snippet = "Steckelhörn 11, 24527 Hamburg"
            image =
                ContextCompat.getDrawable(this@OpenStreetMapsActivity,
                    R.drawable.ic_baseline_scuba_diving_24
                )
            position = GeoPoint(53.55, 10.0)
        }
        overlays.add(marker)

        val polygon = Polygon(map).apply {
            points = listOf(
                GeoPoint(53.54, 9.99),
                GeoPoint(53.54, 10.01),
                GeoPoint(53.56, 10.01),
                GeoPoint(53.56, 9.99)
            )
            fillPaint.color = ColorUtils.setAlphaComponent(Color.BLUE, 64)
        }
        overlays.add(polygon)
    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }
}