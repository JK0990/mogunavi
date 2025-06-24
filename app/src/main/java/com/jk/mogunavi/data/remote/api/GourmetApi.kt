package com.jk.mogunavi.data.remote.api

import com.jk.mogunavi.data.remote.model.GourmetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GourmetApi {
    @GET("hotpepper/gourmet/v1/")
    suspend fun searchShops(
        @Query("key") key: String,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("range") range: Int = 3,
        @Query("keyword") keyword: String = "",
        @Query("format") format: String = "json",
        @Query("count") count: Int = 100,
        @Query("start") start: Int = 1
    ): GourmetResponse
}