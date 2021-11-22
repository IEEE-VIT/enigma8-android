package com.ieeevit.enigma8.view.feedback

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.feedback.FeedbackRequest
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.notification.NotificationActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.viewModel.FeedbackViewModel


class FeedbackActivity:AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    lateinit var viewModel: FeedbackViewModel
    lateinit var submit_btn: Button
    lateinit var feedback_input:EditText
    lateinit var yes:RadioButton
    lateinit var no:RadioButton
    lateinit var one:RadioButton
    lateinit var two:RadioButton
    lateinit var three:RadioButton
    lateinit var four:RadioButton
    lateinit var five:RadioButton
    lateinit var layout: LinearLayout
    lateinit var reg_no:EditText
    lateinit var mail_id:EditText
    lateinit var desc:EditText
    lateinit var suggestion:EditText
    var value:Boolean = false
    var rate:Int = 0
    var reg:String = ""
    var description:String =""
    var emailid:String =""
    var improve:String=""
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        sharedPreferences = PrefManager(this)
        viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        submit_btn = findViewById(R.id.submit)
        yes = findViewById(R.id.yes)
        no = findViewById(R.id.no)
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        layout = findViewById(R.id.yes_layout)
        reg_no = findViewById(R.id.reg_no)
        mail_id = findViewById(R.id.mail_id)
        desc = findViewById(R.id.desc)
        suggestion = findViewById(R.id.suggestion)
        mail_id.setOnClickListener {
            emailid = mail_id.text.toString().trim()
        }
        desc.setOnClickListener {
            description = desc.text.toString().trim()
        }
        suggestion.setOnClickListener {
            improve = suggestion.text.toString().trim()
        }
        reg_no.setOnClickListener {
            reg = reg_no.text.toString().trim()
        }
        yes.setOnClickListener {
            layout.visibility = View.VISIBLE
            value = yes.isChecked
        }
        no.setOnClickListener {
            layout.visibility = View.GONE
            value = no.isChecked
        }
        one.setOnClickListener {
            rate = 1
        }
        two.setOnClickListener {
            rate = 2
        }
        three.setOnClickListener {
            rate = 3
        }
        four.setOnClickListener {
            rate=4
        }
        five.setOnClickListener {
            rate=5
        }
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }

        val authCode: String? = sharedPreferences.getAuthCode()
        submit_btn.setOnClickListener {
            val feedbackRequest = FeedbackRequest(value,rate,description,improve,reg,emailid)
            viewModel.sendFeedbackDetails("Bearer ${authCode.toString()}",feedbackRequest)
            viewModel.FeedbackResponse.observe(this,{
                if(it!=null) {
                    Log.e("Feedback","$it")
                }
            })
        }






    }

}