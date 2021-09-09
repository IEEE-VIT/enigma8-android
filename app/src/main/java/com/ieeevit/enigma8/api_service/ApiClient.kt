package com.ieeevit.enigma8.api_service
import com.ieeevit.enigma8.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://enigma-api.ieeevit.org/")
    .build()

interface ApiClient {
//    @PATCH("api/v1/users/me/edit/") @Headers("Content-Type: application/json")
//    fun editUsername(@Header("Authorization") authToken: String, @Body username: EditUsernameRequest): Call<EditUsernameResponse>
//
//
//    @POST("api/v1/users/auth/google/")
//    @FormUrlEncoded
//    fun getAccessToken(@Field("code") code: String, @Field("callback_url") callBackUrl: String): Call<AccessToken>

}

object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}