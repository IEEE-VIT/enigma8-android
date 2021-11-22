package com.ieeevit.enigma8.view.question

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.webactivity.HintwebActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.question.*
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.viewModel.QuestionViewModel
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class QuestionActivity: AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    lateinit var viewModel: QuestionViewModel
    lateinit var submit_btn:Button
    lateinit var question_no:TextView
    lateinit var question:TextView
    lateinit var roomNo:TextView
    lateinit var questionImg:ImageView
    lateinit var ans:EditText
    lateinit var hint_txt:TextView
    lateinit var hint_btn:ImageView
    lateinit var powerup:TextView
    lateinit var powerupIcon: ImageView
    lateinit var wrong:TextView
    lateinit var tabHeading : TextView
    lateinit var answerField:EditText
    private lateinit var back:ImageView
    private lateinit var instruction:ImageView
//    lateinit var background:BlurView
    var count = 0
    var answer:String=""
    var questionNo=0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        powerupIcon = findViewById(R.id.powerup_icon)
        sharedPreferences = PrefManager(this)
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        ans = findViewById(R.id.Answerfield)
        hint_btn = findViewById(R.id.hint)
        powerup = findViewById(R.id.powerup_name)
        submit_btn = findViewById(R.id.submit_btn)
        tabHeading = findViewById(R.id.tabHeading)
        question_no = findViewById(R.id.question_no)
        roomNo = findViewById(R.id.room_no)
        answerField = findViewById(R.id.Answerfield)
        hint_txt = findViewById(R.id.hint_text)
        wrong =findViewById(R.id.wrong_answer)
        question = findViewById(R.id.question)
//        background = findViewById(R.id.blurBackground)
        val shader2 : Shader = LinearGradient(0f, 0f, 0f,tabHeading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        tabHeading.paint.shader = shader2
        val shader1 : Shader = LinearGradient(0f, 0f, 0f,roomNo.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        roomNo.paint.shader = shader1
        val shader3 : Shader = LinearGradient(0f, 0f, 0f,powerup.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        powerup.paint.shader = shader3

        val authToken = sharedPreferences.getAuthCode()
        questionImg = findViewById(R.id.question_image)
        val questionList:List<QuestionList> = sharedPreferences.getQuestionList()
        Log.e("powerupNmae","${sharedPreferences.getPowerupName()}")
        Log.e("list","${sharedPreferences.getJourneyList()}")

        Log.e("Roomid", "${sharedPreferences.getRoomid()}")
        Log.e("Questionlist", "${sharedPreferences.getQuestionList()}")

        Log.e("powerupResponse","${sharedPreferences.getPowerup()}")
        answerField.setOnClickListener {
            wrong.visibility = View.INVISIBLE
        }
        val rNo = convertRoman(sharedPreferences.getRoomNo())
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this,RoomsActvity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }



        hint_btn.setOnClickListener {

        


                    val view = View.inflate(this, R.layout.confirm_use_hint, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    val dialog = builder.create()
                    val lp = dialog.window!!.attributes
                    lp.dimAmount = 0.0f
                    dialog.window!!.attributes = lp
                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.show()

                    view.findViewById<Button>(R.id.confirm_btn).setOnClickListener {
                        viewModel.getHintDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken.toString()}")
                        viewModel.hintResponse.observe(this, {
                            if (it != null) {
                                hint_txt.text = "Hint: " + it.data.hint
                                hint_txt.setVisibility(View.VISIBLE)
                                hint_btn.setClickable(false)
                                dialog.dismiss()

                            }
                            Log.e("Hint", "$it")
                        })
                        view.findViewById<ImageView>(R.id.close).setOnClickListener {
                            dialog.dismiss()
                        }
                    }


        }
        viewModel.getQuestionDetails(
                sharedPreferences.getRoomid().toString(), "Bearer ${authToken.toString()}")
        viewModel.questionResponse.observe(this, {
            if (it != null) {

                roomNo.text = "Room "+"$rNo"
                Log.e("Room No","${sharedPreferences.getRoomNo()}")
                question.text = it.data.question.text
                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                Picasso.get().load("${it.data.question.media}").into(questionImg)
                sharedPreferences.setRoomNo(it.data.question.questionNo.toString())
                viewModel.getQuestionNo(it.data.question.questionNo)
                powerup.text = it.data.powerupDetails.name
                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                if(it.data.powerupUsed=="no") {
                    powerup.setOnClickListener {
                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                    }
                    viewModel.upowerupResponse.observe(this, {
                        showDialog(it.data.powerUp,it.data)
                    })
                }

                else {
                    powerup.setClickable(false)
                }

            }
            Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
        })
        for(item in questionList.indices) {
            if(questionList[item].roomid == sharedPreferences.getRoomid()) {
                count = item
            }

        }
        submit_btn.setOnClickListener {
             answer = ans.text.toString().trim()
            if (TextUtils.isEmpty(answer)) {
                ans.error = "Enter a valid answer"
                return@setOnClickListener

            }
            val submitRequest = SubmitRequest(sharedPreferences.getRoomid().toString(), answer)
            viewModel.sendQuestionDetails("Bearer ${authToken.toString()}", submitRequest)
            Log.e("cOUNT", "$count")
        }


        viewModel.submitResponse.observe(this, {
            if (it.data!!.correctAnswer == false && it.data!!.closeAnswer == false) {
                wrong.setVisibility(View.VISIBLE)
            } else if (it.data!!.closeAnswer == true) {
                wrong.setVisibility(View.INVISIBLE)
                val view = View.inflate(this, R.layout.close_answer, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)

                val dialog = builder.create()
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.0f
                dialog.window!!.attributes = lp
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));

                dialog.show()
                view.findViewById<ImageView>(R.id.close).setOnClickListener {
                    dialog.dismiss()
                }
            } else if (it.data.correctAnswer == true) {
                wrong.setVisibility(View.INVISIBLE)
                if (questionList[count].questionList[0] == it.data.questionId) {
                    if (it.data.nextRoomUnlocked == false) {
                        val view = View.inflate(this, R.layout.right_answer_first, null)
                        val builder = AlertDialog.Builder(this)
                        builder.setView(view)
                        val dialog = builder.create()
                        val lp = dialog.window!!.attributes
                        lp.dimAmount = 0.0f
                        dialog.window!!.attributes = lp
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));

                        dialog.show()

                        view.findViewById<ImageView>(R.id.close).setOnClickListener {
                            viewModel.getQuestionDetails(
                                    sharedPreferences.getRoomid().toString(),
                                    "Bearer ${authToken}"
                            )
                            viewModel.questionResponse.observe(this, {
                                if (it != null) {
                                    roomNo.text = "Room "+"$rNo"
                                    question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                    question.text = it.data.question.text
                                    hint_txt.visibility = View.INVISIBLE
                                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                                    powerup.text = it.data.powerupDetails.name
                                    Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                    if(it.data.powerupUsed=="no") {
                                        powerup.setOnClickListener {
                                            viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                        }
                                        viewModel.upowerupResponse.observe(this, {
                                            showDialog(it.data.powerUp,it.data)
                                        })
                                    }

                                    else {
                                        powerup.setClickable(false)
                                    }
                                }
                            })
                            dialog.dismiss()

                        }

                    } else if (it.data.nextRoomUnlocked == true) {
                        val view = View.inflate(this, R.layout.right_answer, null)
                        val builder = AlertDialog.Builder(this)
                        builder.setView(view)
                        val dialog = builder.create()
                        val lp = dialog.window!!.attributes
                        lp.dimAmount = 0.0f
                        dialog.window!!.attributes = lp
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
                        dialog.show()

                        view.findViewById<Button>(R.id.close).setOnClickListener {
                            viewModel.getQuestionDetails(
                                    sharedPreferences.getRoomid().toString(),
                                    "Bearer ${authToken}"
                            )
                            viewModel.questionResponse.observe(this, {
                                if (it != null) {
                                    roomNo.text = "Room "+"$rNo"
                                    Log.e("Room No","${sharedPreferences.getRoomNo()}")
                                    question.text = it.data.question.text
                                    hint_txt.visibility = View.INVISIBLE
                                    question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                                    Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
                                    viewModel.getQuestionNo(it.data.question.questionNo)
                                    powerup.text = it.data.powerupDetails.name
                                    Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                    if(it.data.powerupUsed=="no") {
                                        powerup.setOnClickListener {
                                            viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                        }
                                        viewModel.upowerupResponse.observe(this, {
                                            showDialog(it.data.powerUp,it.data)
                                        })
                                    }

                                    else {
                                        powerup.setClickable(false)
                                    }
                                }
                            })

                            dialog.dismiss()
                        }

                    }

                } else if (questionList[count].questionList[1] == it.data.questionId && it.data.nextRoomUnlocked == true) {
                    val view = View.inflate(this, R.layout.right_answer, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    val dialog = builder.create()
                    val lp = dialog.window!!.attributes
                    lp.dimAmount = 0.0f
                    dialog.window!!.attributes = lp
                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));


                    dialog.show()

                    view.findViewById<Button>(R.id.continue_room).setOnClickListener {
                        viewModel.getQuestionDetails(
                                sharedPreferences.getRoomid().toString(),
                                "Bearer ${authToken}"
                        )
                        viewModel.questionResponse.observe(this, {
                            if (it != null) {
                                roomNo.text = "Room "+"$rNo"
                                Log.e("Room No","${sharedPreferences.getRoomNo()}")
                                question.text = it.data.question.text
                                hint_txt.visibility = View.INVISIBLE
                                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                Picasso.get().load("${it.data.question.media}").into(questionImg)
                                Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
                                viewModel.getQuestionNo(it.data.question.questionNo)
                                powerup.text = it.data.powerupDetails.name
                                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                if(it.data.powerupUsed=="no") {
                                    powerup.setOnClickListener {
                                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                    }
                                    viewModel.upowerupResponse.observe(this, {
                                        showDialog(it.data.powerUp,it.data)
                                    })
                                }
                                else {
                                    powerup.setClickable(false)
                                }
                            }
                        })

                        dialog.dismiss()
                    }

                    view.findViewById<Button>(R.id.another_room).setOnClickListener {
                        val intent = Intent(this, RoomsActvity::class.java)
                        startActivity(intent)
                    }

                } else if (questionList[count].questionList[2] == it.data.questionId && it.data.nextRoomUnlocked == false) {
                    val view = View.inflate(this, R.layout.right_answer, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    Log.e("3rd", "true")
                    var dialog = builder.create()
                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
                    val lp = dialog.window!!.attributes
                    lp.dimAmount = 0.0f
                    dialog.window!!.attributes = lp
                    dialog.show()
                    view.findViewById<Button>(R.id.continue_room).setVisibility(View.INVISIBLE)
                    view.findViewById<Button>(R.id.another_room).setOnClickListener {
                        val intent = Intent(this, RoomsActvity::class.java)
                        startActivity(intent)
                    }

                }
            }

        })
    }


fun showDialog(i:PowerUp,it:UsePowerupData) {

        val view = View.inflate(this, R.layout.use_powerup, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        var dialog = builder.create()
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.0f
        dialog.window!!.attributes = lp
        dialog.show()
        val icon = view.findViewById<ImageView>(R.id.powerup_icon)
        Picasso.get().load(Uri.parse(i.icon)).into(icon)
        view.findViewById<TextView>(R.id.powerup_txt).text = it.text+" "+it.data
         val img= view.findViewById<ImageView>(R.id.img_url)
        if(it.imgUrl != "null") {
            img.visibility = View.VISIBLE
//            Picasso.get().load(Uri.parse(it.imgUrl)).into(img)
        }
        else if (it.imgUrl=="null"){
            img.visibility = View.GONE

        }
        view.findViewById<ImageView>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
}

fun convertRoman(n : String?):String?{
    if(n=="1")
    {
        return "i"
    }
    else if(n=="2")
    {
        return "ii"
    }
    else if(n=="3")
    {
        return "iii"
    }
    else if(n=="4")
    {
        return "iv"
    }
    else if(n=="5")
    {
        return "v"
    }
    else if(n=="6")
    {
        return "vi"
    }
    else if(n=="7")
    {
        return "vii"
    }
    else if(n=="8")
    {
        return "viii"
    }
    else
    {
        return "ix"
    }
    return "ix"
}


}