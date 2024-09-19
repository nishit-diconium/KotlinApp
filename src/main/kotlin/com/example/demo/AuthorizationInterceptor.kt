package com.example.demo

import org.koin.core.component.KoinComponent
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class AuthorizationInterceptor : Interceptor, KoinComponent {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

//        requestBuilder.addHeader(
//        )

        val response = chain.proceed(requestBuilder.build())

        if (response.isSuccessful) {
            return response
        } else {
            errorHandlerNavigator(response)
        }
        return response
    }

    private fun errorHandlerNavigator(response: Response) {
        when (response.code) {
            400,404,500 -> {
                try {
//                    Log.e("ENetworkError", e.toString())

                }catch (e: Exception){
                    e.printStackTrace()
//                    Log.e("ENetworkError", e.toString())
                }
            }
            401 -> {
            }

            else -> {}
        }
    }

}
