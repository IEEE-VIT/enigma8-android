package com.ieeevit.enigma8.view.feedback

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.ProgressBarAnimation
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
    private lateinit var progress:ProgressBar
    private lateinit var blackScreen:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        val heading : TextView = findViewById<TextView>(R.id.feedback)
        val text1 : TextView = findViewById<TextView>(R.id.feedback_text)
        val shader1 : Shader = LinearGradient(0f, 0f,0f,heading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f,0.6f),Shader.TileMode.REPEAT)
        val shader2 : Shader= LinearGradient(0f, 0f,0f,text1.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_blue), this.getColor(R.color.dark_blue)), floatArrayOf(0.4f,0.6f),Shader.TileMode.REPEAT)
        heading.paint.shader = shader1
        text1.paint.shader = shader2
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
        sharedPreferences = PrefManager(this)
        viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        submit_btn = findViewById(R.id.submit)
        yes = findViewById(R.id.yes)
        no = findViewById(R.id.no)
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        blackScreen = findViewById(R.id.overlay)
        progress = findViewById(R.id.progressBar)
        five = findViewById(R.id.five)
        layout = findViewById(R.id.yes_layout)
        reg_no = findViewById(R.id.reg_no)
        mail_id = findViewById(R.id.mail_id)
        desc = findViewById(R.id.desc)
        suggestion = findViewById(R.id.suggestion)
        yes.setOnClickListener {
            layout.visibility = View.VISIBLE
            value = true
        }
        no.setOnClickListener {
            layout.visibility = View.GONE
            value = false
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
            emailid = mail_id.text.toString().trim()
            description = desc.text.toString().trim()
            improve = suggestion.text.toString().trim()

            reg = reg_no.text.toString().trim()
            if(value == false) {
                reg = "Not a vit student"
                emailid = "Not a vit student"
            }

               val feedbackRequest = FeedbackRequest(value,reg,emailid,rate,description,improve)
                viewModel.sendFeedbackDetails("Bearer ${authCode.toString()}",feedbackRequest)
                viewModel.FeedbackResponse.observe(this,{
                    if(it.success == true) {
                        Toast.makeText(applicationContext,"Feedback Submitted",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,NotificationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(!it.success) {
                        Toast.makeText(applicationContext,"You Already Submitted the feedback",Toast.LENGTH_SHORT).show()
                    }

                    if(it!=null) {
                        Log.e("Feedback","$it")
                    }
                })





        }






    }

}