package com.jk.mogunavi.data.remote.model

data class GourmetResponse(
    val results: Results
)

data class Results(
    val shop: List<Shop>
)

data class Shop(
    val id: String,
    val name: String,
    val access: String?,
    val address: String,
    val lat: Double,
    val lng: Double,
    val photo: Photo,
    val open: String?
)

data class Photo(
    val mobile: Mobile
)

data class Mobile(
    val l: String // 큰 이미지 URL
)
