package com.ieeevit.enigma8.view.question

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer.OnPreparedListener
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.question.*
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.viewModel.QuestionViewModel
import com.ieeevit.enigma8.webactivity.HintwebActivity
import com.ieeevit.enigma8.webactivity.WebActivity
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
    var simpleVideoView: VideoView? = null
    var mediaControls: MediaController? = null
    private lateinit var progress:ProgressBar
    private lateinit var blackScreen:ImageView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
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

        powerupIcon = findViewById(R.id.powerup_icon)
        sharedPreferences = PrefManager(this)
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        ans = findViewById(R.id.Answerfield)
        hint_btn = findViewById(R.id.hint)
        powerup = findViewById(R.id.powerup_name)
        simpleVideoView = findViewById<View>(R.id.simpleVideoView) as VideoView
        submit_btn = findViewById(R.id.submit_btn)
        tabHeading = findViewById(R.id.tabHeading)
        question_no = findViewById(R.id.question_no)
        roomNo = findViewById(R.id.room_no)
        answerField = findViewById(R.id.Answerfield)
        hint_txt = findViewById(R.id.hint_text)
        wrong =findViewById(R.id.wrong_answer)
        question = findViewById(R.id.question)
        blackScreen  = findViewById(R.id.overlay)
        progress = findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)
//        background = findViewById(R.id.blurBackground)
        val shader2 : Shader = LinearGradient(0f, 0f, 0f, tabHeading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        tabHeading.paint.shader = shader2
        val shader1 : Shader = LinearGradient(0f, 0f, 0f, roomNo.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        roomNo.paint.shader = shader1
        val shader3 : Shader = LinearGradient(0f, 0f, 0f, powerup.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        roomNo.paint.shader = shader3

        val authToken = sharedPreferences.getAuthCode()
        questionImg = findViewById(R.id.question_image)
        val questionList:List<QuestionList> = sharedPreferences.getQuestionList()
        answerField.setOnClickListener {
            wrong.visibility = View.INVISIBLE
            answerField.setText("")
        }

        simpleVideoView!!.setMediaController(mediaControls)
        val rNo = convertRoman(sharedPreferences.getRoomNo())
        Log.e("RNO", "$rNo")
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this, RoomsActvity::class.java)
            startActivity(intent)
            finish()
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
            finish()
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
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            dialog.show()

            view.findViewById<Button>(R.id.confirm_btn).setOnClickListener {
                viewModel.getHintDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken.toString()}")
            }
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
        Log.e("RoomIDCurrent", "${sharedPreferences.getRoomid()}")
        viewModel.getQuestionDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken.toString()}")
        viewModel.questionResponse.observe(this, {
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE
            if (it != null) {

                roomNo.text = "Room " + "$rNo"
                Log.e("Room No", "${sharedPreferences.getRoomNo()}")
                question.text = it.data.question.text
                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                if (it.data.question.mediaType == "image/png") {
                    simpleVideoView!!.visibility = View.GONE
                    questionImg.visibility = View.VISIBLE
                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                } else if (it.data.question.mediaType == "video/mp4") {
                    simpleVideoView!!.visibility = View.VISIBLE
                    questionImg.visibility = View.GONE
                    simpleVideoView!!.setVideoPath(it.data.question.media)
                    simpleVideoView!!.start()
                    simpleVideoView!!.setOnCompletionListener {
                        Toast.makeText(applicationContext, "Vido completed", Toast.LENGTH_SHORT).show()
                    }
                    simpleVideoView!!.setOnErrorListener { mp, what, extra ->
                        Toast.makeText(applicationContext, "An Error Occured " +
                                "While Playing Video !!!", Toast.LENGTH_LONG).show()
                        false
                    }

                    simpleVideoView!!.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })

                }

                sharedPreferences.setRoomNo(it.data.question.questionNo.toString())
                viewModel.getQuestionNo(it.data.question.questionNo)
                powerup.text = it.data.powerupDetails.name
                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                if (it.data.powerupUsed == "no") {
                    powerup.setOnClickListener {
                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                    }

                } else {
                    powerup.setClickable(false)
                }

            }
            Log.e("Question Response", "$it")
        })
        viewModel.upowerupResponse.observe(this, {
            showDialog(it.data.powerUp, it.data)
        })
        for(item in questionList.indices) {
            if(questionList[item].roomid == sharedPreferences.getRoomid()) {
                count = item
            }

        }
        Log.e("CureentRoomNo", "$count")
        submit_btn.setOnClickListener {
            answer = ans.text.toString().trim()
            if (TextUtils.isEmpty(answer)) {
                ans.error = "Enter a valid answer"
                return@setOnClickListener
            }
            Log.e("SubitRequest Roomid", "${sharedPreferences.getRoomid()}")
            val submitRequest = SubmitRequest(sharedPreferences.getRoomid().toString(), answer)
            viewModel.sendQuestionDetails("Bearer ${authToken.toString()}", submitRequest)
            Log.e("cOUNTSubmit", "$count")
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
                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

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
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        view.findViewById<TextView>(R.id.earn_key).text = "You’ve earned ${it.data.scoreEarned} points and a key!"
                        dialog.show()
                        view.findViewById<ImageView>(R.id.close).setOnClickListener {
                            Log.e("GetQuestion", "${sharedPreferences.getRoomid()}")
                            viewModel.getQuestionDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken}")
                            dialog.dismiss()
                        }
                        viewModel.questionResponse.observe(this, {
                            progress.visibility = View.GONE
                            blackScreen.visibility = View.GONE
                            if (it != null) {
                                roomNo.text = "Room " + "$rNo"
                                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                question.text = it.data.question.text
                                hint_txt.visibility = View.INVISIBLE
                                if (it.data.question.mediaType == "image/png") {
                                    simpleVideoView!!.visibility = View.GONE
                                    questionImg.visibility = View.VISIBLE
                                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                                } else if (it.data.question.mediaType == "video/mp4") {
                                    simpleVideoView!!.visibility = View.VISIBLE
                                    questionImg.visibility = View.GONE
                                    simpleVideoView!!.setVideoPath(it.data.question.media)
                                    simpleVideoView!!.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
                                    simpleVideoView!!.start()
                                    simpleVideoView!!.setOnCompletionListener {
                                        Toast.makeText(applicationContext, "Vido completed", Toast.LENGTH_SHORT).show()
                                    }
                                    simpleVideoView!!.setOnErrorListener { mp, what, extra ->
                                        Toast.makeText(applicationContext, "An Error Occured " +
                                                "While Playing Video !!!", Toast.LENGTH_LONG).show()
                                        false
                                    }

                                }

                                powerup.text = it.data.powerupDetails.name
                                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                if (it.data.powerupUsed == "no") {
                                    powerup.setOnClickListener {
                                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                    }

                                } else {
                                    powerup.setClickable(false)
                                }
                            }
                        })

                    } else if (it.data.nextRoomUnlocked == true) {
                        val view = View.inflate(this, R.layout.right_answer, null)
                        val builder = AlertDialog.Builder(this)
                        builder.setView(view)
                        val dialog = builder.create()
                        val lp = dialog.window!!.attributes
                        lp.dimAmount = 0.0f
                        dialog.window!!.attributes = lp
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.show()
                        view.findViewById<TextView>(R.id.earn_key).text = "You’ve earned ${it.data.scoreEarned} points and a key!"
                        view.findViewById<Button>(R.id.continue_room).setOnClickListener {
                            viewModel.getQuestionDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken}")
                            dialog.dismiss()
                        }

                        viewModel.questionResponse.observe(this, {
                            progress.visibility = View.GONE
                            blackScreen.visibility = View.GONE
                            if (it != null) {
                                roomNo.text = "Room " + "$rNo"
                                Log.e("Room No", "${sharedPreferences.getRoomNo()}")
                                question.text = it.data.question.text
                                hint_txt.visibility = View.INVISIBLE
                                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                if (it.data.question.mediaType == "image/png") {
                                    simpleVideoView!!.visibility = View.GONE
                                    questionImg.visibility = View.VISIBLE
                                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                                } else if (it.data.question.mediaType == "video/mp4") {
                                    simpleVideoView!!.visibility = View.VISIBLE
                                    questionImg.visibility = View.GONE
                                    simpleVideoView!!.setVideoPath(it.data.question.media)
                                    simpleVideoView!!.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
                                    simpleVideoView!!.start()
                                    simpleVideoView!!.setOnCompletionListener {
                                        Toast.makeText(applicationContext, "Vido completed", Toast.LENGTH_SHORT).show()
                                    }
                                    simpleVideoView!!.setOnErrorListener { mp, what, extra ->
                                        Toast.makeText(applicationContext, "An Error Occured " +
                                                "While Playing Video !!!", Toast.LENGTH_LONG).show()
                                        false
                                    }

                                }
                                Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
                                viewModel.getQuestionNo(it.data.question.questionNo)
                                powerup.text = it.data.powerupDetails.name
                                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                if (it.data.powerupUsed == "no") {
                                    powerup.setOnClickListener {
                                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                    }
                                } else {
                                    powerup.setClickable(false)
                                }
                            }
                        })
                        view.findViewById<Button>(R.id.another_room).setOnClickListener {
                            val intent = Intent(this, RoomsActvity::class.java)
                            startActivity(intent)
                            dialog.dismiss()
                        }
                    }

                } else if (questionList[count].questionList[1] == it.data.questionId) {
                    if (it.data.nextRoomUnlocked == true) {
                        val view = View.inflate(this, R.layout.right_answer, null)
                        val builder = AlertDialog.Builder(this)
                        builder.setView(view)
                        val dialog = builder.create()
                        val lp = dialog.window!!.attributes
                        lp.dimAmount = 0.0f
                        dialog.window!!.attributes = lp
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        dialog.show()
                        view.findViewById<TextView>(R.id.earn_key).text = "You’ve earned ${it.data.scoreEarned} points and a key!"
                        view.findViewById<Button>(R.id.continue_room).setOnClickListener {
                            viewModel.getQuestionDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken}")
                            dialog.dismiss()
                        }
                        viewModel.questionResponse.observe(this, {
                            progress.visibility = View.GONE
                            blackScreen.visibility = View.GONE
                            if (it != null) {
                                roomNo.text = "Room " + "$rNo"
                                Log.e("Room No", "${sharedPreferences.getRoomNo()}")
                                question.text = it.data.question.text
                                hint_txt.visibility = View.INVISIBLE
                                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                if (it.data.question.mediaType == "image/png") {
                                    simpleVideoView!!.visibility = View.GONE
                                    questionImg.visibility = View.VISIBLE
                                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                                } else if (it.data.question.mediaType == "video/mp4") {
                                    simpleVideoView!!.visibility = View.VISIBLE
                                    questionImg.visibility = View.GONE
                                    simpleVideoView!!.setVideoPath(it.data.question.media)
                                    simpleVideoView!!.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
                                    simpleVideoView!!.start()
                                    simpleVideoView!!.setOnCompletionListener {
                                        Toast.makeText(applicationContext, "Vido completed", Toast.LENGTH_SHORT).show()
                                    }
                                    simpleVideoView!!.setOnErrorListener { mp, what, extra ->
                                        Toast.makeText(applicationContext, "An Error Occured " +
                                                "While Playing Video !!!", Toast.LENGTH_LONG).show()
                                        false
                                    }

                                }
                                Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
                                viewModel.getQuestionNo(it.data.question.questionNo)
                                powerup.text = it.data.powerupDetails.name
                                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                if (it.data.powerupUsed == "no") {
                                    powerup.setOnClickListener {
                                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                    }
                                } else {
                                    powerup.setClickable(false)
                                }
                            }
                        })
                        view.findViewById<Button>(R.id.another_room).setOnClickListener {
                            val intent = Intent(this, RoomsActvity::class.java)
                            startActivity(intent)
                        }
                    } else if (it.data.nextRoomUnlocked == false) {
                        val view = View.inflate(this, R.layout.right_answer_first, null)
                        val builder = AlertDialog.Builder(this)
                        builder.setView(view)
                        val dialog = builder.create()
                        val lp = dialog.window!!.attributes
                        lp.dimAmount = 0.0f
                        dialog.window!!.attributes = lp
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

                        dialog.show()
                        view.findViewById<TextView>(R.id.earn_key).text = "You’ve earned ${it.data.scoreEarned} points and a key!"
                        view.findViewById<ImageView>(R.id.close).setOnClickListener {
                            Log.e("GetQuestion", "${sharedPreferences.getRoomid()}")
                            viewModel.getQuestionDetails(sharedPreferences.getRoomid().toString(), "Bearer ${authToken}")
                            dialog.dismiss()
                        }
                        viewModel.questionResponse.observe(this, {
                            progress.visibility = View.GONE
                            blackScreen.visibility = View.GONE
                            if (it != null) {
                                roomNo.text = "Room " + "$rNo"
                                question_no.text = "Q" + " ${convertRoman(it.data.question.questionNo.toString())}"
                                question.text = it.data.question.text
                                hint_txt.visibility = View.INVISIBLE
                                if (it.data.question.mediaType == "image/png") {
                                    simpleVideoView!!.visibility = View.GONE
                                    questionImg.visibility = View.VISIBLE
                                    Picasso.get().load("${it.data.question.media}").into(questionImg)
                                } else if (it.data.question.mediaType == "video/mp4") {
                                    simpleVideoView!!.visibility = View.VISIBLE
                                    questionImg.visibility = View.GONE
                                    simpleVideoView!!.setVideoPath(it.data.question.media)
                                    simpleVideoView!!.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
                                    simpleVideoView!!.start()
                                    simpleVideoView!!.setOnCompletionListener {
                                        Toast.makeText(applicationContext, "Vido completed", Toast.LENGTH_SHORT).show()
                                    }
                                    simpleVideoView!!.setOnErrorListener { mp, what, extra ->
                                        Toast.makeText(applicationContext, "An Error Occured " +
                                                "While Playing Video !!!", Toast.LENGTH_LONG).show()
                                        false
                                    }
                                }
                                powerup.text = it.data.powerupDetails.name
                                Picasso.get().load("${it.data.powerupDetails.icon}").into(powerupIcon)
                                if (it.data.powerupUsed == "no") {
                                    powerup.setOnClickListener {
                                        viewModel.getUsePowerupDeatils(sharedPreferences.getRoomid(), "Bearer ${authToken.toString()}")
                                    }
                                } else {
                                    powerup.setClickable(false)
                                }
                            }
                        })
                    }
                } else if (questionList[count].questionList[2] == it.data.questionId) {
                    val view = View.inflate(this, R.layout.right_answer, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    Log.e("3rd", "true")
                    var dialog = builder.create()
                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    val lp = dialog.window!!.attributes
                    lp.dimAmount = 0.0f
                    dialog.window!!.attributes = lp
                    dialog.show()
                    view.findViewById<TextView>(R.id.earn_key).text = "You’ve earned ${it.data.scoreEarned} points and a key!"
                    view.findViewById<Button>(R.id.continue_room).setVisibility(View.GONE)
                    view.findViewById<Button>(R.id.another_room).setOnClickListener {
                        val intent = Intent(this, RoomsActvity::class.java)
                        startActivity(intent)
                    }

                }
            }

        })
        viewModel.upowerupResponse.observe(this, {
            showDialog(it.data.powerUp, it.data)
        })
    }
    fun showDialog(i: PowerUp, its: UsePowerupData) {

        val view = View.inflate(this, R.layout.use_powerup, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        var dialog = builder.create()
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.0f
        dialog.window!!.attributes = lp
        dialog.show()
        val icon = view.findViewById<ImageView>(R.id.powerup_icon)
        Picasso.get().load(Uri.parse(i.icon)).into(icon)
        val powerup_txt = view.findViewById<TextView>(R.id.powerup_txt)
        if(i.name == "Onuris"){
            powerup_txt.text = its.text +" "+its.data
            powerup_txt.setOnClickListener {
                val intent = Intent(this, HintwebActivity::class.java)
                intent.putExtra("URL", its.data)
                startActivity(intent)
            }
        }
        if(its.data == null) {
            view.findViewById<TextView>(R.id.powerup_txt).text = its.text
        }
        else if(its.data!=null) {
            view.findViewById<TextView>(R.id.powerup_txt).text = its.text+" "+its.data
        }
        val img= view.findViewById<ImageView>(R.id.img_url)
        if(its.imgUrl != null) {
            img.visibility = View.VISIBLE
            Picasso.get().load(Uri.parse(its.imgUrl)).into(img)
        }
        else if (its.imgUrl==null){
            img.visibility = View.GONE
        }
        view.findViewById<ImageView>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
    }

    fun convertRoman(n: String?):String?{
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