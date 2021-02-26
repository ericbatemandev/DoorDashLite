package com.ericbatemandev.doordashlite.repository

import android.content.Context
import com.ericbatemandev.doordashlite.model.RestaurantsResponse
import com.ericbatemandev.doordashlite.network.NetworkClient
import com.ericbatemandev.doordashlite.network.Result

class RestaurantsRepository {

    suspend fun fetchRestaurantData(context: Context): Result<RestaurantsResponse> {
        return  NetworkClient.getRestaurants(context)
    }
}