package com.jk.mogunavi.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jk.mogunavi.util.rememberMapViewWithLifecycle
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapPermissionWrapper() {
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    if (locationPermission.status.isGranted) {
        MapScreen()
    } else {
        Text("位置情報の権限が必要です。")
    }
}

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(factory = { mapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = true
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("MapScreen", "位置情報の権限が許可されました。現在地を取得中です…")

                try {
                    googleMap.isMyLocationEnabled = true
                } catch (e: SecurityException) {
                    Log.e("MapScreen", "isMyLocationEnabled で SecurityException が発生", e)
                }

                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

                val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
                    priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 10000
                }

                val locationCallback = object : com.google.android.gms.location.LocationCallback() {
                    override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                        val location = result.lastLocation
                        if (location != null) {
                            val current = LatLng(location.latitude, location.longitude)
                            Log.d("MapScreen", "現在地の座標: ${current.latitude}, ${current.longitude}")
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15f))
                            googleMap.addMarker(MarkerOptions().position(current).title("現在地"))
                            fusedLocationClient.removeLocationUpdates(this)
                        } else {
                            Log.w("MapScreen", "requestLocationUpdates の location が null です")
                        }
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )

            } else {
                Log.w("MapScreen", "위치 권한이 없어 위치를 가져올 수 없음")
            }
        }
    }
}
