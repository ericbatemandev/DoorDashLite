package com.ericbatemandev.doordashlite.network

import android.content.Context
import android.util.Log
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.VolleyError
import com.ericbatemandev.doordashlite.R
import com.ericbatemandev.doordashlite.model.RestaurantsResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object NetworkClient {
    private const val DOOR_DASH_API_URL = "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50"

    suspend fun getRestaurants(context: Context): Result<RestaurantsResponse> {
        val url = DOOR_DASH_API_URL
        return sendRequest(context, Request.Method.GET, url, RestaurantsResponse::class.java, null)
    }

    private suspend fun <T> sendRequest(
        context: Context,
        method: Int,
        url: String,
        clazz: Class<T>,
        params: Map<String, Any>?
    ) = suspendCoroutine<Result<T>> { continuation ->
        val gsonRequest = GsonRequest(method, url, clazz, params,
            { response ->
                continuation.resume(Result.Success(response))
            },
            { error ->
                val errorMessage = getErrorMessage(context, error)
                continuation.resume(Result.Failure(errorMessage))

                error.printStackTrace()
            })

        VolleySingleton.getInstance(context).addToRequestQueue(gsonRequest)
    }

    private fun getErrorMessage(context: Context, error: VolleyError): String {
        var errorMessage: String
        var statusCode = 0
        when {
            error is NoConnectionError -> {
                errorMessage = context.getString(R.string.network_unavailable)
            }
            error.networkResponse != null -> {
                statusCode = error.networkResponse.statusCode
                    try {
                        val jsonObject = JSONObject(
                            String(
                                error.networkResponse.data,
                                StandardCharsets.UTF_8
                            )
                        )
                        errorMessage = jsonObject.getString("message")
                    } catch (e: UnsupportedEncodingException) {
                        errorMessage = context.getString(R.string.generic_error)
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        errorMessage = context.getString(R.string.generic_error)
                        e.printStackTrace()
                    }
            }
            else -> {
                errorMessage = context.getString(R.string.generic_error)
            }
        }
        Log.d("EJB", "Response failed with status code $statusCode\nError message: $errorMessage")
        return errorMessage
    }

}