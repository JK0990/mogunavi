package com.jk.mogunavi.data.repository

import com.jk.mogunavi.data.remote.api.RetrofitInstance.api
import com.jk.mogunavi.data.remote.model.GourmetResponse

class GourmetRepository {
    suspend fun searchShops(
        apiKey: String,
        lat: Double,
        lng: Double,
        range: Int,
        keyword: String,
        start: Int = 1
    ): GourmetResponse {
        return api.searchShops(
            key = apiKey,
            lat = lat,
            lng = lng,
            range = range,
            keyword = keyword,
            start = start
        )
    }
}
