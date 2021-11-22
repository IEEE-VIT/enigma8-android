package com.ieeevit.enigma8.api_service

import com.ieeevit.enigma8.model.leaderboard.LeaderboardResponse
import com.ieeevit.enigma8.model.*
import com.ieeevit.enigma8.model.auth.AccessTokenResponse
import com.ieeevit.enigma8.model.auth.AuthRequest
import com.ieeevit.enigma8.model.countdown.StatusResponse
import com.ieeevit.enigma8.model.fcm.FcmRequest
import com.ieeevit.enigma8.model.fcm.FcmResponse
import com.ieeevit.enigma8.model.feedback.Feedback
import com.ieeevit.enigma8.model.feedback.FeedbackRequest
import com.ieeevit.enigma8.model.feedback.FeedbackStatus
import com.ieeevit.enigma8.model.notification.NotificationResponse
import com.ieeevit.enigma8.model.powerup.PowerUpResponse
import com.ieeevit.enigma8.model.powerup.PowerupRequest
import com.ieeevit.enigma8.model.powerup.SendPowerupResponse
import com.ieeevit.enigma8.model.profile.User
import com.ieeevit.enigma8.model.profile.UserRequest
import com.ieeevit.enigma8.model.question.*
import com.ieeevit.enigma8.model.room.CheckResponse
import com.ieeevit.enigma8.model.room.RoomResponse
import com.ieeevit.enigma8.model.story.CurrentStory
import com.ieeevit.enigma8.model.story.ProfileResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.logging.*

val  logging : HttpLoggingInterceptor =  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


val clientSetup = OkHttpClient.Builder().addInterceptor(logging) // read timeout
        .build()




val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(clientSetup)
        .build()

interface ApiClient {


    @POST(Constants.LOGIN_URL)

    fun getAccessToken(@Body authRequest: AuthRequest): Call<AccessTokenResponse>

    //    @POST("api/v1/users/logout/")
//    fun logOut(@Header("Authorization") authToken: String): Call<LogoutResponse>
    @GET(Constants.TIMER_URL)
    fun getEnigmaStatus(@Header("Authorization") authToken: String): Call<StatusResponse>
    @GET(Constants.Feedback_Filled_URL)
    fun getFeedbackStatus(@Header("Authorization") authToken: String): Call<FeedbackStatus>

    @POST(Constants.USER_URL)
    fun sendUserDetails(@Header("Authorization") authToken: String,@Body userRequest: UserRequest) : Call<User>

    @POST(Constants.FCM_URL)
    fun sendFCMToken(@Header("Authorization") authToken: String,@Body fcmRequest: FcmRequest) : Call<FcmResponse>

    @GET(Constants.ROOMS_URL)
    fun getRoomDetails(@Header("Authorization") authToken: String): Call<RoomResponse>

    @GET(Constants.PowerUp_URL)
    fun getPowerUpDeatils(@Header("Authorization") authToken: String): Call<PowerUpResponse>

    @POST(Constants.SendPowerUp_URL)
    fun sendPowerupDetails(@Header("Authorization") authToken: String,@Body powerupRequest: PowerupRequest) : Call<SendPowerupResponse>

    @GET(Constants.CurrentStory_URL)
    fun getCurrentStoryDeatils(@Query("roomId") roomId: String?, @Header("Authorization") authToken: String): Call<CurrentStory>

    @POST(Constants.Feedback_URL)
    fun sendFeedbackDetails(@Header("Authorization") authToken: String,@Body feedbackRequest: FeedbackRequest) : Call<Feedback>

    @GET(Constants.Question_URL)
    fun getQuestionDeatils(@Query("roomId") roomId: String?, @Header("Authorization") authToken: String): Call<QuestionResponse>

    @POST(Constants.Submit_URL)
    fun sendQuestionDetails(@Header("Authorization") authToken: String,@Body submitRequest: SubmitRequest) : Call<SubmitResponse>

    @GET(Constants.CheckRoom_URL)
    fun getRoomUnlockDeatils(@Query("roomId") roomId: String?, @Header("Authorization") authToken: String): Call<CheckResponse>

    @GET(Constants.Hint_URL)
    fun getHintDeatils(@Query("roomId") roomId: String?, @Header("Authorization") authToken: String): Call<HintResponse>
    @GET(Constants.Notification_URl)
    fun getNotificationDetails(@Header("Authorization") authToken: String): Call<NotificationResponse>
    @GET(Constants.UsePowerup_URL)
    fun getUsePowerupDetails(@Query("roomId") roomId: String?, @Header("Authorization") authToken: String): Call<UsePowerupResponse>

    @GET(Constants.FullStory_URL)
    fun getFullStoryDetails(@Query("roomId") roomId: String?, @Header("Authorization") authToken: String): Call<FullStory>

    @GET(Constants.Leaderboard_URL)
    fun getLeaderboardDetails(@Query("page") page: Int?,@Query("perPage")perpage: Int?,@Header("Authorization") authToken: String,@Query("query")quer:String?): Call<LeaderboardResponse>

    @GET(Constants.Profile_URL)
    fun getProfileDetails(@Header("Authorization") authToken: String): Call<ProfileResponse>




}

object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}