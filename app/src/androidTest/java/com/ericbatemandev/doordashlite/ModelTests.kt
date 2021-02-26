package com.ericbatemandev.doordashlite

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ericbatemandev.doordashlite.model.Restaurant
import com.ericbatemandev.doordashlite.model.RestaurantsResponse
import com.ericbatemandev.doordashlite.model.Status
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Unit test for model class deserialization
 */
@RunWith(AndroidJUnit4::class)
class ModelTests {

    @Test
    fun testRestaurantsResponse() {
        val json = inputStreamToJsonString(com.ericbatemandev.doordashlite.test.R.raw.restaurants_response)
        val restaurantsResponse = Gson().fromJson(json, RestaurantsResponse::class.java)

        assertNotNull(restaurantsResponse.restaurantList)
        assertEquals(restaurantsResponse.restaurantList.size, 2)
    }

    @Test
    fun testRestaurant() {
        val json = inputStreamToJsonString(com.ericbatemandev.doordashlite.test.R.raw.restaurant)
        val restaurant = Gson().fromJson(json, Restaurant::class.java)

        assertNotNull(restaurant)
        assertEquals(restaurant.id, 62087)
        assertEquals(restaurant.name, "The Melt")
        assertEquals(restaurant.description, "Super melty stuff")
        assertEquals(restaurant.imgUrl, "https://cdn.doordash.com/media/restaurant/cover/The-Melt.png")
        assertNotNull(restaurant.status)
    }

    @Test
    fun testStatus() {
        val json = inputStreamToJsonString(com.ericbatemandev.doordashlite.test.R.raw.status)
        val status = Gson().fromJson(json, Status::class.java)

        assertNotNull(status)
        assertEquals(status.minutesRange.size, 2)
        assertEquals(status.minutesRange[0], 54)
    }

    private fun inputStreamToJsonString(resource: Int): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val inputStream = context.resources.openRawResource(resource)

            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            ""
        }
    }
}