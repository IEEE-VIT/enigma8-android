package com.ieeevit.enigma8.view.profile

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.question.QustionStatus
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.privacy.PrivacyActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.viewModel.ProfileViewModel

class Profdashboard : Fragment() {
    private lateinit var sharedPreferences: PrefManager
    private lateinit var usertxt:TextView
    private lateinit var emailtxt:TextView
    private lateinit var scoretxt:TextView
    private lateinit var starstxt:TextView
    private lateinit var ranktxt:TextView
    private lateinit var policy:ImageView
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var progress: ProgressBar
    private lateinit var blackScreen:ImageView


    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this,ProfileViewModel.Factory())
            .get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(),viewModel.gso)

        val root = inflater.inflate(R.layout.fragment_profile_dashboard, container, false)
        sharedPreferences = PrefManager(requireContext())
        Log.e("journeylist","${sharedPreferences.getJourneyList()}")
        val authToken = sharedPreferences.getAuthCode()
        val journeydataList : MutableList<QustionStatus> = sharedPreferences.getJourneyList()
        Log.e("Token","Bearer $authToken")
        blackScreen  = root.findViewById(R.id.overlay)
        progress = root.findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)
        val privacy_policy = root.findViewById<ImageView>(R.id.privacy_policy)


        val room1 = root.findViewById<TextView>(R.id.room_1)
        val room2 = root.findViewById<TextView>(R.id.room_2)
        val room3 = root.findViewById<TextView>(R.id.room_3)
        val room4 = root.findViewById<TextView>(R.id.room_4)
        val room5 = root.findViewById<TextView>(R.id.room_6)
        val room6 = root.findViewById<TextView>(R.id.room_5)
        val room7 = root.findViewById<TextView>(R.id.room_7)
        val room8 = root.findViewById<TextView>(R.id.room_8)
        val room9 = root.findViewById<ImageView>(R.id.room_9)

        val room1q1 = root.findViewById<ImageView>(R.id.roomone_q1key)
        val room1q2 = root.findViewById<ImageView>(R.id.roomone_q2key)
        val room1q3 = root.findViewById<ImageView>(R.id.roomone_q3key)
        val room2q1 = root.findViewById<ImageView>(R.id.roomtwo_q1key)
        val room2q2 = root.findViewById<ImageView>(R.id.roomtwo_q2key)
        val room2q3 = root.findViewById<ImageView>(R.id.roomtwo_q3key)
        val room3q1 = root.findViewById<ImageView>(R.id.roomthree_q1key)
        val room3q2 = root.findViewById<ImageView>(R.id.roomthree_q2key)
        val room3q3 = root.findViewById<ImageView>(R.id.roomthree_q3key)
        val room4q1 = root.findViewById<ImageView>(R.id.roomfour_q1key)
        val room4q2 = root.findViewById<ImageView>(R.id.roomfour_q2key)
        val room4q3 = root.findViewById<ImageView>(R.id.roomfour_q3key)
        val room5q1 = root.findViewById<ImageView>(R.id.roomfive_q1key)
        val room5q2 = root.findViewById<ImageView>(R.id.roomfive_q2key)
        val room5q3 = root.findViewById<ImageView>(R.id.roomfive_q3key)
        val room6q1 = root.findViewById<ImageView>(R.id.roomsix_q1key)
        val room6q2 = root.findViewById<ImageView>(R.id.roomsix_q2key)
        val room6q3 = root.findViewById<ImageView>(R.id.roomsix_q3key)
        val room7q1 = root.findViewById<ImageView>(R.id.roomseven_q1key)
        val room7q2 = root.findViewById<ImageView>(R.id.roomseven_q2key)
        val room7q3 = root.findViewById<ImageView>(R.id.roomseven_q3key)
        val room8q1 = root.findViewById<ImageView>(R.id.roomeight_q1key)
        val room8q2 = root.findViewById<ImageView>(R.id.roomeight_q2key)
        val room8q3 = root.findViewById<ImageView>(R.id.roomeight_q3key)
        val line1 = root.findViewById<ImageView>(R.id.line_room1)
        val line2 = root.findViewById<ImageView>(R.id.line_room2)
        val line3 = root.findViewById<ImageView>(R.id.line_room3)
        val line4 = root.findViewById<ImageView>(R.id.line_room4)
        val line5 = root.findViewById<ImageView>(R.id.line_room5)
        val line6 = root.findViewById<ImageView>(R.id.line_room6)
        val line7 = root.findViewById<ImageView>(R.id.line_room7)
        val line8 = root.findViewById<ImageView>(R.id.line_final)

        room9.visibility = View.INVISIBLE
        line8.visibility = View.INVISIBLE
        privacy_policy.setOnClickListener {
            val intent = Intent(requireContext(),PrivacyActivity::class.java)
            startActivity(intent)
        }

         usertxt = root.findViewById<TextView>(R.id.usernamevar)
         emailtxt = root.findViewById<TextView>(R.id.emailvar)
         starstxt = root.findViewById<TextView>(R.id.starsvar)
         scoretxt = root.findViewById<TextView>(R.id.scorevar)
         ranktxt = root.findViewById<TextView>(R.id.rankvar)

        val headtext = root.findViewById<TextView>(R.id.textView2)
        val painthead = headtext.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,headtext.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        painthead.shader = shader1

        val logout_btn = root.findViewById<Button>(R.id.logoutbtn)
        logout_btn.setOnClickListener() {
            mGoogleSignInClient.signOut().addOnSuccessListener {
                Log.e("sign out","sign out")
            }
            val intent = Intent(requireContext() , MainActivity::class.java)
            startActivity(intent)
            (activity as RoomsActvity).finish()
        }



        Log.e("list 2 ","$journeydataList")

            if(journeydataList[0].list[0] == "solved" || (journeydataList[0].list[0]=="solved" && journeydataList[0].list[1] == "solved" ) ) {
                room1.setBackgroundResource(R.drawable.bg_progressbar_blue)
            }
         if(journeydataList[1].list[0] == "solved" || (journeydataList[1].list[0]=="solved" && journeydataList[1].list[1] == "solved" ) ) {
            room2.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line1.setImageResource(R.drawable.greendot)
        }
        if(journeydataList[2].list[0] == "solved" || (journeydataList[2].list[0]=="solved" && journeydataList[2].list[1] == "solved" ) ) {
            room3.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line2.setImageResource(R.drawable.greendot)
        }
        if(journeydataList[3].list[0] == "solved" || (journeydataList[3].list[0]=="solved" && journeydataList[3].list[1] == "solved" ) ) {
            room4.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line3.setImageResource(R.drawable.greendot)
        }
        if(journeydataList[4].list[0] == "solved" || (journeydataList[4].list[0]=="solved" && journeydataList[4].list[1] == "solved" ) ) {
            room5.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line4.setImageResource(R.drawable.greendot_rotated)
        }
        if(journeydataList[5].list[0] == "solved" || (journeydataList[5].list[0]=="solved" && journeydataList[5].list[1] == "solved" ) ) {
            room6.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line5.setImageResource(R.drawable.greendot_mirror)
        }
             if(journeydataList[6].list[0] == "solved" || (journeydataList[6].list[0]=="solved" && journeydataList[6].list[1] == "solved" ) ) {
                room7.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line6.setImageResource(R.drawable.greendot_mirror)
            }
             if(journeydataList[7].list[0] == "solved" || (journeydataList[7].list[0]=="solved" && journeydataList[7].list[1] == "solved" ) ) {
                room8.setBackgroundResource(R.drawable.bg_progressbar_blue)
                line7.setImageResource(R.drawable.greendot_mirror)
            }



                if(journeydataList[0].list[0] == "solved") room1q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[0].list[1] == "solved") room1q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[0].list[2] == "solved") room1q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[1].list[0] == "solved") room2q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[1].list[1] == "solved") room2q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[1].list[2] == "solved") room2q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[2].list[0] == "solved") room3q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[2].list[1] == "solved") room3q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[2].list[2] == "solved") room3q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[3].list[0] == "solved") room4q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[3].list[1] == "solved") room4q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[3].list[2] == "solved") room4q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[4].list[0] == "solved") room5q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[4].list[1] == "solved") room5q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[4].list[2] == "solved") room5q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[5].list[0] == "solved") room6q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[5].list[1] == "solved") room6q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[5].list[2] == "solved") room6q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[6].list[0] == "solved") room7q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[6].list[1] == "solved") room7q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[6].list[2] == "solved") room7q3.setImageResource(R.drawable.ques_done)

                 if (journeydataList[7].list[0] == "solved") room8q1.setImageResource(R.drawable.ques_done)
                 if (journeydataList[7].list[1] == "solved") room8q2.setImageResource(R.drawable.ques_done)
                 if (journeydataList[7].list[2] == "solved") room8q3.setImageResource(R.drawable.ques_done)
//

        if (journeydataList[0].list[0] == "solved" && journeydataList[0].list[1] == "solved" && journeydataList[0].list[2] == "solved") {
            room1.setBackgroundResource(R.drawable.bg_progressbar_green)
        }
          if (journeydataList[1].list[0] == "solved" && journeydataList[1].list[1] == "solved" && journeydataList[1].list[2] == "solved") {
            room2.setBackgroundResource(R.drawable.bg_progressbar_green)
            line1.setImageResource(R.drawable.greenful)
        }
          if (journeydataList[2].list[0] == "solved" && journeydataList[2].list[1] == "solved" && journeydataList[2].list[2] == "solved"){
            room3.setBackgroundResource(R.drawable.bg_progressbar_green)
            line2.setImageResource(R.drawable.greenful)
        }
          if (journeydataList[3].list[0] == "solved" && journeydataList[3].list[1] == "solved" && journeydataList[3].list[2] == "solved") {
            room4.setBackgroundResource(R.drawable.bg_progressbar_green)
            line3.setImageResource(R.drawable.greenful)
        }
          if (journeydataList[4].list[0] == "solved" && journeydataList[4].list[1] == "solved" && journeydataList[4].list[2] == "solved") {
            room5.setBackgroundResource(R.drawable.bg_progressbar_green)
            line4.setImageResource(R.drawable.greenful_rotated)
        }
          if (journeydataList[5].list[0] == "solved" && journeydataList[5].list[1] == "solved" && journeydataList[5].list[2] == "solved") {
            room6.setBackgroundResource(R.drawable.bg_progressbar_green)
            line5.setImageResource(R.drawable.greenful_mirror)
        }
          if (journeydataList[6].list[0] == "solved" && journeydataList[6].list[1] == "solved" && journeydataList[6].list[2] == "solved") {
            room7.setBackgroundResource(R.drawable.bg_progressbar_green)
            line6.setImageResource(R.drawable.greenful_mirror)
        }
          if (journeydataList[7].list[0] == "solved" && journeydataList[7].list[1] == "solved" && journeydataList[7].list[2] == "solved") {
            room8.setBackgroundResource(R.drawable.bg_progressbar_green)
            line7.setImageResource(R.drawable.greenful_mirror)
            line8.visibility = View.VISIBLE
            room9.visibility = View.VISIBLE
        }



        viewModel.getProfileDetails("Bearer $authToken")

        viewModel.profileResponse.observe(viewLifecycleOwner, {
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE

         if(it != null){
             sharedPreferences.setStars(it.data.starts)

             usertxt?.text = it.data.username
             emailtxt?.text = it.data.email
             starstxt?.text = it.data.starts.toString()
             scoretxt?.text = it.data.score.toString()
             ranktxt?.text = it.data.rank.toString()
             Log.e("ya", "$usertxt")
            }
            Log.e("Response","$it")
        })

        return root

    }

}
