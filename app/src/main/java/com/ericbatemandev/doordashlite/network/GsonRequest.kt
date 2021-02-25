package com.ericbatemandev.doordashlite.network

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Request class to obtain a parsed object from JSON.
 *
 * @param method            Request method
 * @param url               URL of the request to make
 * @param clazz             Relevant class object, for Gson's reflection
 * @param requestParams     Map of request params
 * @param listener          Reports network success response
 * @param errorListener     Reports network error response
 */
class GsonRequest<T>(
    method: Int,
    url: String,
    private val clazz: Class<T>,
    private val requestParams: Map<String, Any>?,
    listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : JsonRequest<T>(method, url, null, listener, errorListener) {
    private val gson = Gson()

    override fun getBody(): ByteArray? {
        return if (requestParams != null) {
            JSONObject(requestParams).toString().toByteArray()
        } else null
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}