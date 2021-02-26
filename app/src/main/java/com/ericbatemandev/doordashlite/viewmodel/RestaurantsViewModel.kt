package com.ericbatemandev.doordashlite.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericbatemandev.doordashlite.model.RestaurantsResponse
import com.ericbatemandev.doordashlite.network.Result
import com.ericbatemandev.doordashlite.repository.RestaurantsRepository
import kotlinx.coroutines.launch

class RestaurantsViewModel : ViewModel() {
    private val repository = RestaurantsRepository()
    val restaurantsLiveData: MutableLiveData<Result<RestaurantsResponse>> = MutableLiveData()

    fun fetchRestaurantData(context: Context) {
        viewModelScope.launch {
            val response = repository.fetchRestaurantData(context)
            restaurantsLiveData.postValue(response)
        }
    }
}