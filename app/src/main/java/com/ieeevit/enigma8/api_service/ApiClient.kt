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
    @GET(Constants.TIMER_URL)
    fun getEnigmaStatus(@Header("Authorization") authToken: String): Call<StatusResponse>

    @POST(Constants.USER_URL)
    fun sendUserDetails(@Header("Authorization") authToken: String,@Body userRequest: UserRequest) : Call<User>

    @GET(Constants.ROOMS_URL)
    fun getRoomDetails(@Header("Authorization") authToken: String): Call<RoomResponse>


}

object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}