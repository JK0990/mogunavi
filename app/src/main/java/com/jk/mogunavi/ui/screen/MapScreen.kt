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

// 권한 요청 Composable 추가
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapPermissionWrapper() {
    Log.d("MapScreen", "MapPermissionWrapper 실행됨")
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    if (locationPermission.status.isGranted) {
        MapScreen()
    } else {
        Text("위치 권한이 필요합니다.")
    }
}

@Composable
fun MapScreen() {
    Log.d("MapScreen", "MapScreen() 호출됨")

    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(factory = { mapView }) { mapView ->
        Log.d("MapScreen", "AndroidView factory 호출됨")
        mapView.getMapAsync { googleMap ->
            Log.d("MapScreen", "getMapAsync 콜백 호출됨")
            googleMap.uiSettings.isZoomControlsEnabled = true
            Log.d("MapScreen", "지도 준비 완료됨")

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("MapScreen", "위치 권한 허용됨, 현재 위치 요청 중...")

                try {
                    googleMap.isMyLocationEnabled = true
                } catch (e: SecurityException) {
                    Log.e("MapScreen", "isMyLocationEnabled에서 SecurityException 발생", e)
                }

                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

                val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
                    priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 10000  // 10초
                }

                val locationCallback = object : com.google.android.gms.location.LocationCallback() {
                    override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                        val location = result.lastLocation
                        if (location != null) {
                            val current = LatLng(location.latitude, location.longitude)
                            Log.d("MapScreen", "현재 위치 좌표: ${current.latitude}, ${current.longitude}")
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15f))
                            googleMap.addMarker(MarkerOptions().position(current).title("현재 위치"))

                            // 한 번만 받아오고 콜백 제거
                            fusedLocationClient.removeLocationUpdates(this)
                        } else {
                            Log.w("MapScreen", "requestLocationUpdates의 location이 null")
                        }
                    }
                }

                // 위치 요청 시작
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
