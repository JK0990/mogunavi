package com.jk.mogunavi.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jk.mogunavi.data.remote.api.GourmetApi

object RetrofitInstance {
    val api: GourmetApi = Retrofit.Builder()
        .baseUrl("https://webservice.recruit.co.jp/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GourmetApi::class.java)
}