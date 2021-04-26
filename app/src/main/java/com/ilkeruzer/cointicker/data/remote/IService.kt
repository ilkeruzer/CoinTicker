package com.ilkeruzer.cointicker.data.remote

import com.ilkeruzer.cointicker.data.remote.model.CoinDetailResponse
import com.ilkeruzer.cointicker.data.remote.model.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IService {

    @GET("coins/list")
    suspend fun coinList(): List<CoinResponse>

    @GET("coins/{id}")
    suspend fun getCoinById(@Path("id") id: String): CoinDetailResponse
}