package com.ieeevit.enigma8.view.timer

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.demoQuestion.DemoQuestionActivity
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.viewModel.CountdownViewModel
import java.util.*


class   CountdownActivity : AppCompatActivity() {

    private lateinit var bt:Button
    private lateinit var demo: TextView
    private lateinit var sharedPreference: PrefManager
    private lateinit var currentCalendar: Calendar
    private lateinit var eventCalendar: Calendar
    private lateinit var days1: TextView
    private lateinit var days2: TextView
    private lateinit var hours1: TextView
    private lateinit var hours2: TextView
    private lateinit var minutes1: TextView
    private lateinit var play: TextView
    private lateinit var minutes2: TextView
    private  var timeLeft:Long = 0
    lateinit var viewModel:CountdownViewModel
    private var currentTime: Long = 0
    private var eventStartTime: Long = 0
    private lateinit var heading:TextView
    private lateinit var days:TextView
    private lateinit var hours:TextView
    private lateinit var minutes:TextView
    private lateinit var text:TextView
    private lateinit var end:TextView
    private lateinit var progress:ProgressBar
    private lateinit var blackScreen:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)
        sharedPreference = PrefManager(this)
        val authToken = sharedPreference.getAuthCode()
        viewModel = ViewModelProvider(this).get(CountdownViewModel::class.java)
        demo = findViewById(R.id.Demo)
        heading = findViewById(R.id.heading)
        days1 = findViewById(R.id.days1)
        hours1 = findViewById(R.id.hours1)
        minutes1 = findViewById(R.id.minutes1)
        days2 = findViewById(R.id.days2)
        end = findViewById(R.id.text_end)
        play = findViewById(R.id.playNow)
        hours2 = findViewById(R.id.hours2)
        minutes2 = findViewById(R.id.minutes2)
        text = findViewById(R.id.text_begin)
        days = findViewById(R.id.days)
        hours = findViewById(R.id.hours)
        minutes = findViewById(R.id.minutes)
        blackScreen  = findViewById(R.id.overlay)
        progress = findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)

        val shader1 : Shader = LinearGradient(0f, 0f, 0f, heading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f, 0.7f), Shader.TileMode.REPEAT)
        val shader2 : Shader= LinearGradient(0f, 0f, 0f, demo.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f, 0.7f), Shader.TileMode.REPEAT)
        val shader3 : Shader= LinearGradient(0f, 0f, 0f, days1.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.countdown_yellow), this.getColor(R.color.countdown_brown)), floatArrayOf(0.05f, 0.9f), Shader.TileMode.REPEAT)
        heading.paint.shader = shader1
        days1.paint.shader = shader3
        days2.paint.shader = shader3
        hours1.paint.shader = shader3
        hours2.paint.shader = shader3
        minutes1.paint.shader = shader3
        minutes2.paint.shader = shader3
        days.paint.shader = shader3
        hours.paint.shader = shader3
        minutes.paint.shader = shader3
        demo.paint.shader = shader2
        text.paint.shader = shader2
        end.paint.shader = shader2
        play.paint.shader = shader2


        init()
        viewModel.getEnigmaStatus("Token $authToken")
        play.setOnClickListener {
            sharedPreference.setBackIndicator(0)
            startActivity(Intent(this, InstructionActivity::class.java))
            finish()
        }

        viewModel.enigmaStatus.observe(this, {
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE
            if (it != null) {
                sharedPreference.setHuntStarted(it.data.enigmaStarted)
                Log.e("ResponseCounter", "$it")
                if (it.data.date > 0 && it.data.enigmaStarted == false) {
                    play.visibility = View.INVISIBLE
                    demo.visibility = View.VISIBLE
                    sharedPreference.setEnigmaStatus(it.data.enigmaStarted)
                    currentTime = currentCalendar.timeInMillis
                    eventStartTime = currentTime + (it.data.date) * 1000.toLong()

                    Log.e("eventstart", eventStartTime.toString())
                    Log.e("current", currentTime.toString())

                    timeLeft = eventStartTime - currentTime

                    startTimer(timeLeft)
                } else if (it.data.date > 0 && it.data.enigmaStarted == true) {
                    play.visibility = View.VISIBLE
                    demo.visibility = View.INVISIBLE
                    currentTime = currentCalendar.timeInMillis
                    eventStartTime = currentTime + (it.data.date) * 1000.toLong()

                    Log.e("eventstart", eventStartTime.toString())
                    Log.e("current", currentTime.toString())

                    timeLeft = eventStartTime - currentTime

                    startTimer(timeLeft)
                } else {
                    demo.visibility = View.VISIBLE
                    play.visibility = View.INVISIBLE
                }


            }
        })
        demo.setOnClickListener {
            val intent = Intent(this, DemoQuestionActivity::class.java)
            startActivity(intent)
            finish()
        }
//        viewModel.getEnigmaStatus("Token ${sharedPreference.getAuthCode()}")
//
//        viewModel.enigmaStatus.observe(this@CountdownActivity, {
//            Log.e("ResponseTimer","$it")
//            if(it.data.enigmaStarted) {
//                Log.e("jdjsddsj","dksjdkdskdsn")
//                play.visibility = View.VISIBLE
//                demo.visibility = View.INVISIBLE
//                text.visibility = View.INVISIBLE
//                end.visibility  = View.VISIBLE
//                Log.e("Inside",it.data.enigmaStarted.toString())
//
//            }
//            else if(!it.data.enigmaStarted) {
//                play.visibility = View.INVISIBLE
//                demo.visibility = View.VISIBLE
//                text.visibility = View.INVISIBLE
//                end.visibility  = View.VISIBLE
//                Log.e("Inside",it.data.enigmaStarted.toString())
//
//            }
//        })


    }
    private fun init() {

        currentCalendar = Calendar.getInstance(TimeZone.getDefault())
        eventCalendar = Calendar.getInstance(TimeZone.getDefault())
    }

    private fun startTimer(timeDifference: Long) {

        val countdownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                days1.text = ((millisUntilFinished / (24 * 60 * 60 * 1000))/10).toString()
                days2.text = ((millisUntilFinished / (24 * 60 * 60 * 1000))%10).toString()
                hours1.text = ((millisUntilFinished / (60 * 60 * 1000) % 24)/10).toString()
                hours2.text= ((millisUntilFinished / (60 * 60 * 1000) % 24)%10).toString()
                minutes1.text = ((millisUntilFinished / (60 * 1000) % 60)/10).toString()
                minutes2.text = ((millisUntilFinished / (60 * 1000) % 60)%10).toString()
            }

            override fun onFinish() {
                viewModel.getEnigmaStatus("Token ${sharedPreference.getAuthCode()}")
                Log.e("jdjsddsj", "dksjdkdskdsn")
                play.visibility = View.VISIBLE
                demo.visibility = View.INVISIBLE
                text.visibility = View.INVISIBLE
                end.visibility  = View.VISIBLE

            }

        }
        countdownTimer.start()

    }


}