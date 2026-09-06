package com.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Jadwal(
    @Json(name = "NO") val no: Int,
    @Json(name = "TANGGAL") val tanggal: String,
    @Json(name = "INDEX") val index: Int,
    @Json(name = "JUMAT") val jumat: String,
    @Json(name = "MUADZIN") val muadzin: String,
    @Json(name = "PEMBAWA ACARA") val pembawaAcara: String,
    @Json(name = "BILAL") val bilal: String,
    @Json(name = "KHOTIB") val khotib: String,
    @Json(name = "IMAM") val imam: String
)

@JsonClass(generateAdapter = true)
data class UpdateResponse(
    @Json(name = "status") val status: String,
    @Json(name = "message") val message: String
)
