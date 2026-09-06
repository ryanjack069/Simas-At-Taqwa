package com.example.network

import com.example.model.Jadwal
import com.example.model.UpdateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface JadwalApi {
    @GET
    suspend fun getJadwal(@Url url: String): List<Jadwal>

    @POST
    suspend fun updateJadwal(
        @Url url: String,
        @Body request: Map<String, String>
    ): UpdateResponse
}
