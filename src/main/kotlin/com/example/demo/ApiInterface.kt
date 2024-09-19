package com.example.demo


import com.example.demo.model.PodState
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import com.facebook.stetho.okhttp3.StethoInterceptor

interface ApiInterface {

    @GET(ApiConstants.API_URL_BY_POD_NAME)
    suspend fun getPodData(@Query(ApiConstants.BY_POD_NAME) cartId: String): PodState

    @DELETE(ApiConstants.API_URL_BY_POD_NAME)
    suspend fun removePodData(
        @Path(ApiConstants.BY_POD_NAME) cartId: String,
    ): PodState

    @PATCH(ApiConstants.API_URL_BY_POD_NAME)
    suspend fun updatePodData(
        @Path(ApiConstants.BY_POD_NAME) cartId: String,
        @Body pdstate: PodState
    ): PodState

    @POST(ApiConstants.API_URL_BY_POD_NAME)
    suspend fun getPodDataWithName(
        @Body podstate: PodState
    ): PodState

    companion object {

        private var BASE_URL = ApiConstants.BASE_URL_HOST
        fun create(): ApiInterface {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(AuthorizationInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .callTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(builder.build())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}