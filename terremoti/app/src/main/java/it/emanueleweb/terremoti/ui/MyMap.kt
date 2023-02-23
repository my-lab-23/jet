package it.emanueleweb.terremoti.ui

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getDrawable
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import it.emanueleweb.terremoti.MyApplication

@Composable
fun MyMap(lat: Double = 41.9027835, lng: Double = 12.4963655) {

    val styleUrl = "https://api.maptiler.com/maps/streets/style.json?key="
    MapComposable(styleUrl, lat, lng)
}

@Composable
fun MapComposable(styleUrl: String, lat: Double, lng: Double) {
    AndroidView(
        {
            val mapView = MapView(it)
            mapView.id = View.generateViewId()
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                map.setStyle(styleUrl) {
                    addSymbolAnnotation(mapView, map, it, lat, lng)
                    map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                    map.cameraPosition = CameraPosition.Builder()
                        .target(LatLng(lat, lng))
                        .zoom(8.0)
                        .build()
                }
            }
            mapView
        },
        Modifier.fillMaxSize()
    )
}

private fun addMarkerImageToStyle(style: Style) {

    val context = MyApplication.appContext

    style.addImage(
        "marker",
        BitmapUtils.getBitmapFromDrawable(getDrawable(
            context, com.mapbox.mapboxsdk.R.drawable.mapbox_marker_icon_default))!!,
        true
    )
}

private fun addSymbolAnnotation(
    mapView: MapView,
    mapboxMap: MapboxMap,
    style: Style,
    lat: Double,
    lng: Double
) {

    // Add icon to the style
    addMarkerImageToStyle(style)

    // Create a SymbolManager.
    val symbolManager = SymbolManager(mapView, mapboxMap, style)

    // Set non-data-driven properties.
    symbolManager.iconAllowOverlap = true
    symbolManager.iconIgnorePlacement = true

    // Create a symbol at the specified location.
    val symbolOptions = SymbolOptions()
        .withLatLng(LatLng(lat, lng))
        .withIconImage("marker")
        .withIconSize(1.3f)
        .withIconColor("#FF0000")

    // Use the manager to draw the annotations.
    symbolManager.create(symbolOptions)
}
