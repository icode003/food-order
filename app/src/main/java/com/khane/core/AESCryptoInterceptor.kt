package com.khane.core

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.Buffer
import java.io.IOException
import javax.inject.Inject

/**
 * Created by hlink21 on 29/11/17.
 */

class AESCryptoInterceptor @Inject
constructor(private val aes: com.khane.core.AES) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val plainQueryParameters = request.url.queryParameterNames
        var httpUrl = request.url
        // Check Query Parameters and encrypt
        if (plainQueryParameters.isNotEmpty()) {
            val httpUrlBuilder = httpUrl.newBuilder()
            for (i in plainQueryParameters.indices) {
                val name = httpUrl.queryParameterName(i)
                val value = httpUrl.queryParameterValue(i)
                httpUrlBuilder.setQueryParameter(name, aes.encrypt(value))
            }
            httpUrl = httpUrlBuilder.build()
        }

        // Get Header for encryption
        val apiKey = request.headers[Session.API_KEY]
        val token = request.headers[Session.TOKEN]
        val language = request.headers[Session.ACCEPT_LANGUAGE]
        val newRequest: Request
        val requestBuilder = request.newBuilder()

        // Check if any body and encrypt
        request.body?.let { requestBody ->
            val isMultipart =
                !requestBody.contentType()?.type.equals("multipart", ignoreCase = true)
            val bodyPlainText =
                if (isMultipart) transformInputStream(bodyToString(requestBody)) else bodyToString(
                    requestBody
                )
            bodyPlainText?.let { plainText ->
                if (isMultipart) {
                    requestBuilder
                        .post(RequestBody.create("text/plain".toMediaTypeOrNull(), plainText))
                } else {
                    requestBuilder
                        .post(RequestBody.create(requestBody.contentType(), plainText))
                }

            }
        }
        // Build the final request
        /*newRequest = requestBuilder.url(httpUrl)
                .header(Session.API_KEY, aes.encrypt(apiKey)!!)
                .header(Session.USER_SESSION, aes.encrypt(token)!!)
                .build()*/
        newRequest = if (token.isNullOrEmpty()) {
            requestBuilder.url(httpUrl)
                .header(Session.API_KEY, aes.encrypt(apiKey)!!)
                .addHeader(Session.ACCEPT_LANGUAGE, language!!)
                .build()
        } else {
            requestBuilder.url(httpUrl)
                .header(Session.API_KEY, aes.encrypt(apiKey)!!)
                .header(Session.TOKEN, aes.encrypt(token)!!)
                .addHeader(Session.ACCEPT_LANGUAGE, language!!)
                .build()

        }


        // execute the request
        val proceed = chain.proceed(newRequest)
        // get the response body and decrypt it.
        val cipherBody = proceed.body!!.string()
        val plainBody = aes.decrypt(cipherBody)

        // create new Response with plaint text body for further process
        return proceed.newBuilder()
            .body(
                ResponseBody.create(
                    "text/json".toMediaTypeOrNull(),
                    plainBody!!.trim { it <= ' ' }
                )
            ).build()

    }

    private fun bodyToString(request: RequestBody?): ByteArray? {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return null
            return buffer.readByteArray()
        } catch (e: IOException) {
            return null
        }

    }

    private fun transformInputStream(inputStream: ByteArray?): ByteArray? {
        return aes.encrypt(inputStream!!)
    }
}
