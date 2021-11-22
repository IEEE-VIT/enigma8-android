package com.ieeevit.enigma8.view.splashscreen

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
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
import com.ieeevit.enigma8.view.story.CharacterActivity
import com.ieeevit.enigma8.view.timer.CountdownActivity

class SplashScreen:AppCompatActivity() {
    private lateinit var view:VideoView
    private lateinit var sharedPreferences: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        sharedPreferences = PrefManager(this)
        var indicator = sharedPreferences.getIndicator()
        var isNew = sharedPreferences.getisNew()
//        val text1 : TextView = findViewById(R.id.heading)
//        val text2 : TextView = findViewById(R.id.desc)
//
//        val paint1 = text1.paint
//        val paint2 = text2.paint
//        val shader1 : Shader = LinearGradient(0f, 0f,0f,text1.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),Shader.TileMode.REPEAT)
//        val shader2 : Shader= LinearGradient(0f, 0f,0f,text1.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_blue), this.getColor(R.color.dark_blue)), floatArrayOf(0.3f,0.7f),Shader.TileMode.REPEAT)
//        paint1.shader = shader1
//        paint2.shader = shader2
//        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top)
//        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom)
//        val new = AnimationUtils.loadAnimation(this,R.anim.newanim)
//        val logo=findViewById<TextView>(R.id.heading)
//        val logoImg=findViewById<ImageView>(R.id.feather)
//        logoImg.startAnimation(new)
//        logo.startAnimation(new)
//
//
//
//        val timeout = 4000
//        val homeIntent= Intent(this,MainActivity::class.java)
//
//         Handler().postDelayed({
////             if (user!=null){
////                 startActivity(Intent(this,GoogleDashboard::class.java))
////                 finish()
////             }
////             else{
//                 startActivity(homeIntent)
////             }
//        }, timeout.toLong())
        view = findViewById(R.id.videoView)
        val vidoPath = "android.resource://$packageName/raw/enigma"
        view.setVideoPath(vidoPath)
        view.setOnCompletionListener {
            val r  = object : Runnable {
                override fun run() {
//                    val intent = Intent(this@SplashScreen,MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
                    if(indicator==1 && isNew == 0) {
                            val intent = Intent(this@SplashScreen,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                    }
                    else if(isNew == 1) {
                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(indicator==0) {
                        val intent = Intent(this@SplashScreen,OnboardingActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(sharedPreferences.isHuntStarted()==false && sharedPreferences.getCount()==1) {
                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(sharedPreferences.isHuntStarted()==false && sharedPreferences.getCount()==2) {
                        val intent = Intent(this@SplashScreen,CountdownActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(sharedPreferences.isHuntStarted()==true && sharedPreferences.getCount()==1) {
                        val intent = Intent(this@SplashScreen,ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(sharedPreferences.isHuntStarted()==true && sharedPreferences.getCount()==2) {
                        val intent = Intent(this@SplashScreen,RoomsActvity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            
            }
            Handler().postDelayed(r,500)
        }
        view.start()


    }


}
