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
                val response = repository.searchShops(apiKey, lat, lng, range, keyword)

                // 🔍 로그 출력: range 값과 받아온 shop 수
                Log.d("GourmetViewModel", "range=$range, keyword=$keyword, shops=${response.results.shop.size}")

                _shops.value = response.results.shop
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("GourmetViewModel", "API 호출 중 오류 발생: ${e.message}")
            }
        }
    }
}
