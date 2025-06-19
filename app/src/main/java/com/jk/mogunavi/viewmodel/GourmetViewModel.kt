package com.jk.mogunavi.viewmodel

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

    fun fetchShops(apiKey: String, lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                val response = repository.searchShops(apiKey, lat, lng)
                _shops.value = response.results.shop
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
