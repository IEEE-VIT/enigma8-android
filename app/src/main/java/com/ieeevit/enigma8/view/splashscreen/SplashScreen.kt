package com.ieeevit.enigma8.view.splashscreen

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.onboarding.OnboardingActivity
import com.ieeevit.enigma8.view.profile.ProfileActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.view.timer.CountdownActivity

class SplashScreen:AppCompatActivity() {
    private lateinit var view:VideoView
    private lateinit var sharedPreferences: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        sharedPreferences = PrefManager(this)
        var indicator = sharedPreferences.getIndicator()

        view = findViewById(R.id.videoView)
        val vidoPath = "android.resource://$packageName/raw/enigma"
        view.setVideoPath(vidoPath)
        view.setOnCompletionListener {
            val r  = object : Runnable {
                override fun run() {
                    Log.e("indicator","$indicator")
                    Log.e("getCount","${sharedPreferences.getCount()}")
                    Log.e("HuntStarted","${sharedPreferences.isHuntStarted()}")
//                    val intent = Intent(this@SplashScreen,MainActivity::class.java)
//                            startActivity(intent)
//                            finish()

                    if(indicator==0) {
                        val intent = Intent(this@SplashScreen,OnboardingActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(indicator == 1) {
                        val intent = Intent(this@SplashScreen,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
//                    else if()
////                    else if(indicator==1) {
//                            val intent = Intent(this@SplashScreen,MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                    }
//                    else if(indicator == 2) {
//                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    else if (sharedPreferences.getCount()==6) {
//                        val intent = Intent(this@SplashScreen,RoomsActvity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    else if(sharedPreferences.getCount() == 9) {
//                        val intent = Intent(this@SplashScreen,CountdownActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    else if(isNew == 0 && sharedPreferences.getError() == false) {
//                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    else if(sharedPreferences.isHuntStarted() == true && isNew == 2 && sharedPreferences.getCount() == 2) {
//                        val intent = Intent(this@SplashScreen,RoomsActvity::class.java)
//                        startActivity(intent)
//                        finish()
//
//                    }
//
////                    else if(sharedPreferences.isHuntStarted()==false && sharedPreferences.getCount()==1) {
////                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
////                        startActivity(intent)
////                        finish()
////                    }
//                    else if(sharedPreferences.isHuntStarted()==false && sharedPreferences.getCount()==2 && isNew ==2) {
//                        val intent = Intent(this@SplashScreen,CountdownActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
////                    else if(sharedPreferences.isHuntStarted()==true && sharedPreferences.getCount()==1) {
////                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
////                        startActivity(intent)
////                        finish()
////                    }
////                    else if(sharedPreferences.isHuntStarted()==true && sharedPreferences.getCount()==2) {
////                        val intent = Intent(this@SplashScreen,RoomsActvity::class.java)
////                        startActivity(intent)
////                        finish()
////                    }
//                    else {
//                        val intent = Intent(this@SplashScreen,RoomsActvity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }

                }
            
            }
            Handler().postDelayed(r,500)
        }
        view.start()


    }


}
