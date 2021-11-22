package com.ieeevit.enigma8.model



object Constants {
    // Endpoints
    const val BASE_URL = "https://enigma8.herokuapp.com/"
    const val LOGIN_URL = "auth/app/google"
    const val TIMER_URL="static/timer"
    const val USER_URL="user/create"
    const val ROOMS_URL="room/allRooms"
    const val POSTS_URL = "posts"
    const val PowerUp_URL = "user/getPowerups"
    const val SendPowerUp_URL = "user/selectPowerup"
    const val CurrentStory_URL = "story/currentStory"
    const val Feedback_URL = "feedback/submitFeedback"
    const val Question_URL = "transact/getQuestion"
    const val Submit_URL = "transact/submitAnswer"
    const val CheckRoom_URL = "room/checkIfRoomUnlocked"
    const val Hint_URL = "transact/useHint"
    const val Notification_URl = "notifs/notifications"
    const val UsePowerup_URL = "transact/usePowerup"
    const val FCM_URL = "user/addFCM"
    const val Profile_URL = "user/getDetails"
    const val Leaderboard_URL = "game/leaderboards"
    const val FullStory_URL = "story/fullStory"
    const val Feedback_Filled_URL = "feedback/feedbackFilled"
}