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
//    @FormUrlEncoded
    fun getAccessToken(@Body authRequest:AuthRequest): Call<AccessTokenResponse>

//    @POST("api/v1/users/logout/")
//    fun logOut(@Header("Authorization") authToken: String): Call<LogoutResponse>
@GET("api/v1/game/status/")
fun getEnigmaStatus(@Header("Authorization") authToken: String): Call<StatusResponse>

}

object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}