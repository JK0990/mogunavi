package com.jk.mogunavi.data.repository

import com.jk.mogunavi.data.remote.api.RetrofitInstance
import com.jk.mogunavi.data.remote.model.GourmetResponse

class GourmetRepository {
    suspend fun searchShops(
        key: String,
        lat: Double,
        lng: Double,
        range: Int = 3,
        keyword: String = "",
        format: String = "json"
    ): GourmetResponse {
        return RetrofitInstance.api.searchShops(key, lat, lng, range, keyword, format)
    }
}
