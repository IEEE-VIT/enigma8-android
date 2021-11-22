package com.ieeevit.enigma8.view.rooms

import android.content.Intent

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import android.widget.RelativeLayout


import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.leaderboard.LeaderboardFragment
import com.ieeevit.enigma8.view.notification.NotificationActivity
import com.ieeevit.enigma8.view.profile.Profdashboard
import com.ieeevit.enigma8.view.story.FullStoryFragment


class RoomsActvity:AppCompatActivity() {
    private lateinit var notification: ImageView
    private lateinit var instruction: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        notification = findViewById(R.id.notification)
        instruction = findViewById(R.id.instruction)
        notification.setOnClickListener {
            val intent = Intent(this,NotificationActivity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }

        val roomfrag = RoomFragment()
        val leadboardfrag = LeaderboardFragment()
        val dashboardfrag = Profdashboard()
        val storymain = FullStoryFragment()
        val storylay = findViewById<RelativeLayout>(R.id.story)
        val profilelay = findViewById<RelativeLayout>(R.id.profile)
        val leaderlay = findViewById<RelativeLayout>(R.id.leaderboard)
        val homelay = findViewById<RelativeLayout>(R.id.home)
        val tabhead = findViewById<TextView>(R.id.tabHeading)

        homelay.setBackgroundColor(resources.getColor(R.color.bottom_selected))
        profilelay.setBackgroundColor(resources.getColor(R.color.background))
        storylay.setBackgroundColor(resources.getColor(R.color.background))
        leaderlay.setBackgroundColor(resources.getColor(R.color.background))

        val shader1 : Shader = LinearGradient(0f, 0f,0f,tabhead.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        tabhead.paint.shader = shader1

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFLcontainer, roomfrag)
            addToBackStack(null)
            commit()
        }



        val dash = findViewById<ImageButton>(R.id.profileBtn)
        dash.setOnClickListener{
            profilelay.setBackgroundColor(resources.getColor(R.color.bottom_selected))
            storylay.setBackgroundColor(resources.getColor(R.color.background))
            leaderlay.setBackgroundColor(resources.getColor(R.color.background))
            homelay.setBackgroundColor(resources.getColor(R.color.background))
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, dashboardfrag)
                addToBackStack(null)
                commit()
            }
        }
        val lead = findViewById<ImageButton>(R.id.leaderboardBtn)
        lead.setOnClickListener{
            profilelay.setBackgroundColor(resources.getColor(R.color.background))
            storylay.setBackgroundColor(resources.getColor(R.color.background))
            leaderlay.setBackgroundColor(resources.getColor(R.color.bottom_selected))
            homelay.setBackgroundColor(resources.getColor(R.color.background))
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, leadboardfrag)
                addToBackStack(null)
                commit()
            }
        }
        val story = findViewById<ImageButton>(R.id.storyBtn)
        story.setOnClickListener{
            profilelay.setBackgroundColor(resources.getColor(R.color.black))
            storylay.setBackgroundColor(resources.getColor(R.color.bottom_selected))
            leaderlay.setBackgroundColor(resources.getColor(R.color.black))
            homelay.setBackgroundColor(resources.getColor(R.color.black))
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, storymain)
                addToBackStack(null)
                commit()
            }
        }

        val home = findViewById<ImageButton>(R.id.homeBtn)
        home.setOnClickListener{
            profilelay.setBackgroundColor(resources.getColor(R.color.background))
            storylay.setBackgroundColor(resources.getColor(R.color.background))
            leaderlay.setBackgroundColor(resources.getColor(R.color.background))
            homelay.setBackgroundColor(resources.getColor(R.color.bottom_selected))
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, roomfrag)
                addToBackStack(null)
                commit()
            }
        }
//
//        val btnPrev = findViewById<Button>(R.id.btnstory_previous)
//        btnPrev.setOnClickListener{
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.fragmentFLcontainer, roomfrag)
//                addToBackStack(null)
//                commit()
//            }
//        }

    }
}