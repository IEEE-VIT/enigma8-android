package com.ieeevit.enigma8.view.timer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.model.StatusResponse
//import com.ieeevit.enigma8.utils.PrefManager
//import com.ieeevit.enigma8.view.main.MainActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.viewModel.CountdownViewModel
//import android.synthetic.main.fragment_countdown.*
import java.text.SimpleDateFormat
import java.util.*

class CountdownFragment : Fragment() {
    private lateinit var startButton: TextView
    private lateinit var sharedPreference: PrefManager
    private lateinit var currentCalendar: Calendar
    private lateinit var eventCalendar: Calendar
    private var currentTime: Long = 0
    private var eventStartTime: Long = 0
    private val viewModel: CountdownViewModel by lazy {
        ViewModelProvider(this, CountdownViewModel.Factory())
            .get(CountdownViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        val authToken = sharedPreference.getAuthCode()
        startButton = root.findViewById(R.id.startButton)
        init()
        viewModel.getEnigmaStatus("Token $authToken")
        startButton.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            sharedPreference.setHuntStarted(true)
        }

        viewModel.enigmaStatus.observe(viewLifecycleOwner, {
            if (it != null) {
                sharedPreference.setEnigmaStatus(it.status)
                val date: Date = convertStringToDate(it)
                eventCalendar.time = date
                currentTime = currentCalendar.timeInMillis
                eventStartTime = eventCalendar.timeInMillis
                val timeLeft = eventStartTime - currentTime
                startTimer(timeLeft)
            }
        })
        return root
    }

    private fun convertStringToDate(it: StatusResponse): Date {
        val day: String = it.day
        val time: String = it.time
        val finalDate = day + 'T' + time
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return sdf.parse(finalDate)
    }

    private fun init() {
        sharedPreference = PrefManager(this.requireActivity())
        currentCalendar = Calendar.getInstance(TimeZone.getDefault())
        eventCalendar = Calendar.getInstance(TimeZone.getDefault())
    }

    private fun startTimer(timeDifference: Long) {
        val countdownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                days?.text = (millisUntilFinished / (24 * 60 * 60 * 1000)).toString()
                hours?.text = (millisUntilFinished / (60 * 60 * 1000) % 24).toString()
                minutes?.text = (millisUntilFinished / (60 * 1000) % 60).toString()
                seconds?.text = (millisUntilFinished / (1000) % 60).toString()
            }

            override fun onFinish() {
                if (sharedPreference.getEnigmaStatus()) {
                    startButton.visibility = VISIBLE
                }

            }
        }
        countdownTimer.start()
    }

}