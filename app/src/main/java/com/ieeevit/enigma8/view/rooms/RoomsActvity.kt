package com.ieeevit.enigma8.view.rooms

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
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

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFLcontainer, roomfrag)
            addToBackStack(null)
            commit()
        }



        val dash = findViewById<ImageButton>(R.id.profileBtn)
        dash.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, dashboardfrag)
                addToBackStack(null)
                commit()
            }
        }
        val lead = findViewById<ImageButton>(R.id.leaderboardBtn)
        lead.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, leadboardfrag)
                addToBackStack(null)
                commit()
            }
        }
        val story = findViewById<ImageButton>(R.id.storyBtn)
        story.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentFLcontainer, storymain)
                addToBackStack(null)
                commit()
            }
        }

        val home = findViewById<ImageButton>(R.id.homeBtn)
        home.setOnClickListener{
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