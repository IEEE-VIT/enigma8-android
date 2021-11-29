package com.ieeevit.enigma8.view.rooms

import android.content.Context
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
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
import android.widget.RelativeLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.instruction.RoomInstruction
import com.ieeevit.enigma8.view.leaderboard.LeaderboardFragment
import com.ieeevit.enigma8.view.notification.NotificationActivity
import com.ieeevit.enigma8.view.powerup.PowerupActivity
import com.ieeevit.enigma8.view.profile.Profdashboard
import com.ieeevit.enigma8.view.question.QuestionActivity
import com.ieeevit.enigma8.view.story.FullStoryFragment
import com.ieeevit.enigma8.viewModel.FullStoryViewModel
import com.ieeevit.enigma8.viewModel.RoomViewModel


class RoomsActvity:AppCompatActivity() {
    private lateinit var notification: ImageView
    private lateinit var instruction: ImageView
    private lateinit var viewModel:RoomViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            val view = View.inflate(this, R.layout.connection_error, null)
            val builder = android.app.AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.1f
            dialog.window!!.attributes = lp
            dialog.show()
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

//            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)

            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
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
            val intent = Intent(this, RoomInstruction::class.java)
            startActivity(intent)
            finish()
        }

        val roomfrag = RoomFragment()
        val leadboardfrag = LeaderboardFragment()
        val dashboardfrag = Profdashboard()
        val storymain = FullStoryFragment()
        val storylay = findViewById<RelativeLayout>(R.id.story)
        val profilelay = findViewById<RelativeLayout>(R.id.profile)
        val leaderlay = findViewById<RelativeLayout>(R.id.leaderboard)
        val homelay = findViewById<RelativeLayout>(R.id.home)
//        val tabhead = findViewById<TextView>(R.id.tabHeading)

        homelay.setBackgroundColor(resources.getColor(R.color.bottom_selected))
        profilelay.setBackgroundColor(resources.getColor(R.color.background))
        storylay.setBackgroundColor(resources.getColor(R.color.background))
        leaderlay.setBackgroundColor(resources.getColor(R.color.background))
//
//        val shader1 : Shader = LinearGradient(0f, 0f,0f,tabhead.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
//            Shader.TileMode.REPEAT)
//        tabhead.paint.shader = shader1

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFLcontainer, roomfrag)
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


}