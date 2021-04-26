package com.ilkeruzer.cointicker.data.remote.model

import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("description") val description: DescriptionResponse,
        @SerializedName("hashing_algorithm") val hashingAlgorithm: String?,
        @SerializedName("image") val image: ImageResponse,
        @SerializedName("last_updated") val lastUpdated: String,
        @SerializedName("market_data") val marketData: MarketDataResponse,
)

data class DescriptionResponse(
        @SerializedName("en") val en: String,
)

data class ImageResponse(
        @SerializedName("large") val large: String,
        @SerializedName("small") val small: String,
        @SerializedName("thumb") val thumb: String
)

data class MarketDataResponse(
        @SerializedName("current_price") val currentPrice: CurrentPriceResponse,
        @SerializedName("high_24h") val highestPrice24h: High24hResponse,
        @SerializedName("low_24h") val lowestPrice24h: Low24hResponse,
)

data class CurrentPriceResponse(
        @SerializedName("try") val TRY: Double,
        @SerializedName("usd") val usd: Double,
)

data class High24hResponse(
        @SerializedName("usd") val usd: Double,
)

data class Low24hResponse(
        @SerializedName("usd") val usd: Double,
)