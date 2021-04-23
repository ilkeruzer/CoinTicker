package com.ilkeruzer.cointicker.data.remote

import com.ilkeruzer.cointicker.data.remote.model.CoinResponse
import retrofit2.http.GET

interface IService {

    @GET("coins/list")
    suspend fun coinList(): List<CoinResponse>
}