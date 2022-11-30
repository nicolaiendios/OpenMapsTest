package de.endios.openmapstest.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import de.endios.openmapstest.R
import io.openmobilemaps.mapscore.map.loader.DataLoader
import io.openmobilemaps.mapscore.map.view.MapView
import io.openmobilemaps.mapscore.shared.map.MapConfig
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemFactory
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers
import io.openmobilemaps.mapscore.shared.map.coordinates.MapCoordinateSystem
import io.openmobilemaps.mapscore.shared.map.coordinates.RectCoord
import io.openmobilemaps.mapscore.shared.map.layers.tiled.Tiled2dMapLayerConfig
import io.openmobilemaps.mapscore.shared.map.layers.tiled.Tiled2dMapZoomInfo
import io.openmobilemaps.mapscore.shared.map.layers.tiled.Tiled2dMapZoomLevelInfo
import io.openmobilemaps.mapscore.shared.map.layers.tiled.raster.Tiled2dMapRasterLayerInterface

// https://github.com/openmobilemaps/maps-core/tree/develop/android
class OpenMobileMapsActivity : AppCompatActivity(R.layout.activity_open_mobile_maps) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.setupMap(MapConfig(CoordinateSystemFactory.getEpsg3857System()))
        mapView.registerLifecycle(lifecycle)
        val layerConfig = object : Tiled2dMapLayerConfig() {
            // Defines the bounds of the layer and implicitly the coordinate system used by the layer as well
            val epsg3857Bounds: RectCoord = RectCoord(
                Coord(CoordinateSystemIdentifiers.EPSG3857(), -20037508.34, 20037508.34, 0.0),
                Coord(CoordinateSystemIdentifiers.EPSG3857(), 20037508.34, -20037508.34, 0.0)
            )

            // Defines to map coordinate system of the layer
            override fun getCoordinateSystemIdentifier(): String =
                CoordinateSystemIdentifiers.EPSG3857()

            // Name of the layer
            override fun getLayerName(): String = "OSMLayer"

            // Defines the url-pattern to load tiles. Enter a valid OSM tile server here
            override fun getTileUrl(x: Int, y: Int, zoom: Int): String =
                "https://add-osm-web-server-address-here/$zoom/$x/$y.png"

            // Defines both an additional scale factor for the tiles, as well as how many
            // layers above the ideal one should be loaded an displayed as well.
            override fun getZoomInfo(): Tiled2dMapZoomInfo = Tiled2dMapZoomInfo(
                zoomLevelScaleFactor = 0.6f,
                numDrawPreviousLayers = 2,
                adaptScaleToScreen = true
            )

            // List of valid zoom-levels and their target zoom-value, the tile size in
            // the layers coordinate system, the number of tiles on that level and the
            // zoom identifier used for the tile-url (see getTileUrl above)
            override fun getZoomLevelInfos(): ArrayList<Tiled2dMapZoomLevelInfo> = ArrayList(
                listOf(
                    Tiled2dMapZoomLevelInfo(559082264.029, 40075016f, 1, 1, 1, epsg3857Bounds),
                    Tiled2dMapZoomLevelInfo(279541132.015, 20037508f, 2, 2, 1, epsg3857Bounds),
                    Tiled2dMapZoomLevelInfo(139770566.007, 10018754f, 4, 4, 1, epsg3857Bounds),
                    Tiled2dMapZoomLevelInfo(69885283.0036, 5009377.1f, 8, 8, 1, epsg3857Bounds),
                    Tiled2dMapZoomLevelInfo(
                        34942641.5018,
                        2504688.5f,
                        16,
                        16,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        17471320.7509,
                        1252344.3f,
                        32,
                        32,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(8735660.37545, 626172.1f, 64, 64, 1, epsg3857Bounds),
                    Tiled2dMapZoomLevelInfo(
                        4367830.18773,
                        313086.1f,
                        128,
                        128,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(2183915.09386, 156543f, 256, 256, 1,  epsg3857Bounds),
                    Tiled2dMapZoomLevelInfo(
                        1091957.54693,
                        78271.5f,
                        512,
                        512,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        545978.773466,
                        39135.8f,
                        1024,
                        1024,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        272989.386733,
                        19567.9f,
                        2048,
                        2048,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        136494.693366,
                        9783.94f,
                        4096,
                        4096,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        68247.3466832,
                        4891.97f,
                        8192,
                        8192,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        34123.6733416,
                        2445.98f,
                        16384,
                        16384,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        17061.8366708,
                        1222.99f,
                        32768,
                        32768,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        8530.91833540,
                        611.496f,
                        65536,
                        65536,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        4265.45916770,
                        305.748f,
                        131072,
                        131072,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        2132.72958385,
                        152.874f,
                        262144,
                        262144,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        1066.36479193,
                        76.437f,
                        524288,
                        524288,
                        1,
                        epsg3857Bounds
                    ),
                    Tiled2dMapZoomLevelInfo(
                        533.18239597,
                        38.2185f,
                        1_048_576,
                        1_048_576,
                        1,
                        epsg3857Bounds
                    )
                )
            )
        }
        val textureLoader = DataLoader(this, cacheDir, 50L * 1024L * 1024L,"self")
        val tiledLayer = Tiled2dMapRasterLayerInterface.create(layerConfig, textureLoader)
        mapView.addLayer(tiledLayer.asLayerInterface())
    }
}