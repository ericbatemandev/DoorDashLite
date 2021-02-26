package com.ericbatemandev.doordashlite.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericbatemandev.doordashlite.model.RestaurantsResponse
import com.ericbatemandev.doordashlite.network.NetworkClient
import com.ericbatemandev.doordashlite.network.Result
import kotlinx.coroutines.launch

class RestaurantsViewModel : ViewModel() {
    val restaurantsLiveData: MutableLiveData<Result<RestaurantsResponse>> = MutableLiveData()

    fun fetchRestaurantData(context: Context) {
        viewModelScope.launch {
            val response = NetworkClient.getRestaurants(context)
            restaurantsLiveData.value = response
        }
    }
}