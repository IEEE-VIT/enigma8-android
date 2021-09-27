package com.ieeevit.enigma8.api_service
import com.ieeevit.enigma8.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface ApiClient {


    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun getAccessToken(@Field("id_token") code:String): Call<AccessToken>

//    @POST("api/v1/users/logout/")
//    fun logOut(@Header("Authorization") authToken: String): Call<LogoutResponse>

}

object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}