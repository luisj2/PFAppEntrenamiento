package com.example.fithealth.Model.Retrofit

import android.util.Base64
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FatSecretInterceptor(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val scope: String = "basic"
) : Interceptor {

    private var accessToken: String? = null
    private var tokenExpirationTime: Long = 0L

    companion object {
        private const val TOKEN_URL = "https://oauth.fatsecret.com/connect/token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (accessToken == null || System.currentTimeMillis() >= tokenExpirationTime) accessToken =
            runBlocking { fetchAccessToken() }

        Log.i("access_token",accessToken!!)


        val requestWithToken =
            chain.request()
                .newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

        return chain.proceed(requestWithToken)
    }


    private suspend fun fetchAccessToken(): String {
        return withContext(Dispatchers.IO) {

            val credentials = "$consumerKey:$consumerSecret"

            val encodedCredentials = encriptMessage(credentials)


            val requestBody = createFormBodyWithMapParams(
                mapOf(
                    "grant_type" to "client_credentials",
                    "scope" to scope
                )
            )
            val request = createRequestWithAuthorization(TOKEN_URL, requestBody, encodedCredentials)

            val call = OkHttpClient().newCall(request)

            val responseBody = getResponseBodyWithCall(call)

            extractAccessTokenAndSetExpiration(responseBody)
        }
    }

    private suspend fun getResponseBodyWithCall(call: Call): String {
        return suspendCoroutine { continuation ->

            call.enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        if (!response.isSuccessful) continuation.resumeWithException(IOException("Error: ${response.code()}"))
                        else continuation.resume(getResponseBody(response))
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

            })
        }
    }

    private fun extractAccessTokenAndSetExpiration(responseBody: String): String {
        val json = JSONObject(responseBody)
        tokenExpirationTime = System.currentTimeMillis() + json.getLong("expires_in") * 1000
        return json.getString("access_token")
    }


    private fun getResponseBody(response: Response): String {
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body()?.use { it.string() } ?: throw IOException("Empty response body")
    }

    private fun createRequestWithAuthorization(
        url: String,
        requestBody: RequestBody,
        encodedCredentials: String
    ): Request {
        return Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Basic $encodedCredentials")
            .build()
    }

    private fun encriptMessage(message: String): String =
        Base64.encodeToString(message.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)

    private fun createFormBodyWithMapParams(params: Map<String, String>): RequestBody {
        val formBuilder = FormBody.Builder()
        params.forEach { (key, value) ->
            formBuilder.add(key, value)
        }
        return formBuilder.build()
    }


}

