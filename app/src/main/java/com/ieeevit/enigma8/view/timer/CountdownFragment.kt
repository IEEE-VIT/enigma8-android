package com.ieeevit.enigma8.view.timer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.main.ProfileActivity
import com.ieeevit.enigma8.viewModel.CountdownViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class   CountdownFragment : Fragment() {
    private lateinit var startButton: TextView
    private lateinit var sharedPreference: PrefManager
    private lateinit var currentCalendar: Calendar
    private lateinit var eventCalendar: Calendar
    private lateinit var days:TextView
    private lateinit var hours:TextView
    private lateinit var minutes:TextView
    private lateinit var seconds:TextView
    private  var timeLeft:Long = 0

    private var currentTime: Long = 0
    private var eventStartTime: Long = 0
    private val viewModel: CountdownViewModel by lazy {
        ViewModelProvider(this, CountdownViewModel.Factory())
            .get(CountdownViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        sharedPreference = PrefManager(requireContext())
        val authToken = sharedPreference.getAuthCode()
        startButton = root.findViewById(R.id.startButton)
        days=root.findViewById(R.id.days)
        hours=root.findViewById(R.id.hours)
        minutes=root.findViewById(R.id.minutes)
        seconds=root.findViewById(R.id.seconds)

        init()
        viewModel.getEnigmaStatus("Token $authToken")
        startButton.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
            sharedPreference.setHuntStarted(true)
        }



        viewModel.enigmaStatus.observe(viewLifecycleOwner, {
            if (it != null) {

                sharedPreference.setEnigmaStatus(it.data.enigmaStarted)
                currentTime = currentCalendar.timeInMillis
                eventStartTime = currentTime+(it.data.date)*1000.toLong()



                Log.e("eventstart", eventStartTime.toString())
                Log.e("current", currentTime.toString())

                timeLeft = eventStartTime - currentTime

                startTimer(timeLeft)


            }
        })
        return root
    }



    private fun init() {
        sharedPreference = PrefManager(this.requireActivity())
        currentCalendar = Calendar.getInstance(TimeZone.getDefault())
        eventCalendar = Calendar.getInstance(TimeZone.getDefault())
    }

    private fun startTimer(timeDifference: Long) {

        val countdownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                days.text = (millisUntilFinished / (24 * 60 * 60 * 1000)).toString()
                hours.text = (millisUntilFinished / (60 * 60 * 1000) % 24).toString()
                minutes.text = (millisUntilFinished / (60 * 1000) % 60).toString()
                seconds.text = (millisUntilFinished / (1000) % 60).toString()
            }

            override fun onFinish() {
                viewModel.getEnigmaStatus("Token ${sharedPreference.getAuthCode()}")
                viewModel.enigmaStatus.observe(viewLifecycleOwner, {
                    if(it.data.enigmaStarted) {
                        startButton.visibility = VISIBLE
                        Log.e("Inside",it.data.enigmaStarted.toString())

                    }
                })


            }

        }
        countdownTimer.start()
    }

}