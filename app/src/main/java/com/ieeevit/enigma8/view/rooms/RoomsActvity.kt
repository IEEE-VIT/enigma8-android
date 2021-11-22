package com.ieeevit.enigma8.view.rooms

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.leaderboard.LeaderboardFragment
import com.ieeevit.enigma8.view.notification.NotificationActivity
import com.ieeevit.enigma8.view.profile.Profdashboard
import com.ieeevit.enigma8.view.story.FullStoryFragment


@Suppress("DEPRECATION")
class RoomsActvity:AppCompatActivity() {
    private lateinit var notification: ImageView
    private lateinit var instruction: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            val view = View.inflate(this, R.layout.connection_error, null)
            val builder = android.app.AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.0f
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.window!!.attributes = lp
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            dialog.show()
            view.findViewById<Button>(R.id.try_again).setOnClickListener(View.OnClickListener {
                recreate()

            })
        }



        notification = findViewById(R.id.notification)
        instruction = findViewById(R.id.instruction)
        notification.setOnClickListener {
            val intent = Intent(this,NotificationActivity::class.java)
            startActivity(intent)
            finish()
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
            finish()
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



    override fun onBackPressed()
    {

        val currentFragment =this.supportFragmentManager.findFragmentById(R.id.fragmentFLcontainer)
        if(currentFragment is RoomFragment)
        {
            val view = View.inflate(this, R.layout.exit_dialog, null)
            val builder = android.app.AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.0f
            dialog.window!!.attributes = lp
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()
            view.findViewById<Button>(R.id.yes).setOnClickListener {
                finish()
            }
            view.findViewById<Button>(R.id.no).setOnClickListener{
                dialog.dismiss()
            }
        }
        else{
            super.onBackPressed()
        }


    }


}