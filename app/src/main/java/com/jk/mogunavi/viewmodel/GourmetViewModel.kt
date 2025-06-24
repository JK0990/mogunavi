package com.jk.mogunavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.mogunavi.data.remote.model.Shop
import com.jk.mogunavi.data.repository.GourmetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GourmetViewModel : ViewModel() {
    private val repository = GourmetRepository()

    private val _shops = MutableStateFlow<List<Shop>>(emptyList())
    val shops: StateFlow<List<Shop>> = _shops

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

                    // 만약 100개 미만이면 다음 start는 생략 (끝까지 도달한 것)
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
}
