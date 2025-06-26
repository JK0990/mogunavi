package com.jk.mogunavi.viewmodel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.mogunavi.data.remote.model.Shop
import com.jk.mogunavi.data.repository.GourmetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class GourmetViewModel : ViewModel() {
    private val repository = GourmetRepository()

    private val _shops = MutableStateFlow<List<Shop>>(emptyList())
    val shops: StateFlow<List<Shop>> = _shops

    private val _currentAddress = MutableStateFlow("위치 정보를 불러오는 중...")
    val currentAddress: StateFlow<String> = _currentAddress

    fun fetchShops(
        apiKey: String,
        lat: Double,
        lng: Double,
        range: Int = 3,
        keyword: String = ""
    ) {
        viewModelScope.launch {
            try {
                val allShops = mutableListOf<Shop>()

                val startValues = listOf(1, 101, 201)
                for (start in startValues) {
                    val response = repository.searchShops(
                        apiKey = apiKey,
                        lat = lat,
                        lng = lng,
                        range = range,
                        keyword = keyword,
                        start = start
                    )
                    val fetched = response.results.shop
                    Log.d("GourmetViewModel", "start=$start, fetched=${fetched.size}")
                    allShops.addAll(fetched)

                    if (fetched.size < 100) break
                }

                Log.d("GourmetViewModel", "총 받아온 shop 수: ${allShops.size}")
                _shops.value = allShops
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("GourmetViewModel", "API 호출 중 오류 발생: ${e.message}")
            }
        }
    }

    fun fetchCurrentAddressFromLatLng(context: Context, lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                val geocoder = Geocoder(context, Locale.JAPAN)
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                val address = addresses?.firstOrNull()

                // 주소 구성 요소 조합 (예: 大阪府 大阪市北区 梅田)
                val name = listOfNotNull(
                    address?.adminArea,     // 都道府県
                    address?.locality,      // 市
                    address?.subLocality,   // 区
                    address?.thoroughfare   // 丁目や通り
                ).joinToString(" ")

                Log.d("GourmetViewModel", "현재 주소: $name")
                _currentAddress.value = name.ifBlank { "위치 정보를 불러올 수 없음" }
            } catch (e: Exception) {
                Log.e("GourmetViewModel", "주소 변환 실패: ${e.message}")
                _currentAddress.value = "주소 정보를 불러올 수 없음"
            }
        }
    }
}
