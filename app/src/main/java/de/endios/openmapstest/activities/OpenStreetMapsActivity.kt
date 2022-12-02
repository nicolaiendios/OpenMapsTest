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
import de.endios.openmapstest.TAG
import de.endios.openmapstest.databinding.ActivityOpenStreetMapsBinding
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

    private lateinit var binding: ActivityOpenStreetMapsBinding

    private val singleItem = arrayListOf(
        OverlayItem("Title A", "Snippet", GeoPoint(53.55, 10.0))
    )
    private val markerDrawable by lazy {
        ContextCompat.getDrawable(this@OpenStreetMapsActivity, R.drawable.ic_baseline_place_24)
            ?.apply {
                setTint(Color.CYAN)
            }
    }
    private val markerImage by lazy {
        ContextCompat.getDrawable(
            this@OpenStreetMapsActivity,
            R.drawable.ic_baseline_power_off_24
        )
    }
    private val startPoint = GeoPoint(53.55, 10.0)
    private val polygonPoints = listOf(
        GeoPoint(53.54, 9.99),
        GeoPoint(53.54, 10.01),
        GeoPoint(53.56, 10.01),
        GeoPoint(53.56, 9.99)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // handle permissions first, before map is created. not depicted here

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        // note, the load method also sets the HTTP User Agent to your application's package name,
        // if you abuse osm's tile servers will get you banned based on this string.


        binding = ActivityOpenStreetMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.map.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            controller.setZoom(14.0)
            controller.setCenter(startPoint)
            addMarkers()
        }
    }

    private fun MapView.addMarkers() {
        val overlay = ItemizedOverlayWithFocus(
            singleItem,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
                    //do something
                    Log.d(TAG, "You clicked index: $index, item: $item")
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
                    return false
                }
            },
            context
        )
        overlay.setFocusItemsOnTap(true)

        val marker = Marker(this).apply {
            icon = markerDrawable
            title = "Endios GmbH"
            snippet = "Steckelhörn 11, 24527 Hamburg"
            image = markerImage
            position = startPoint
        }
        overlays.add(marker)

        val polygon = Polygon(this).apply {
            points = polygonPoints
            fillPaint.color = ColorUtils.setAlphaComponent(Color.BLUE, 64)
        }
        overlays.add(polygon)
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
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