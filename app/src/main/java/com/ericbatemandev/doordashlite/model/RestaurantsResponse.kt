package com.ericbatemandev.doordashlite.model

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
        @SerializedName("stores") val restaurantList: List<Restaurant>
)

data class Restaurant(
        val id: Long,
        val name: String,
        val description: String,
        @SerializedName("cover_img_url") val imgUrl: String
)