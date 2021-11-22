package com.ieeevit.enigma8.view.demoQuestion

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.view.timer.CountdownActivity
import com.ieeevit.enigma8.viewModel.QuestionViewModel


@Suppress("DEPRECATION")
class DemoQuestionActivity: AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    lateinit var viewModel: QuestionViewModel
    lateinit var submit_btn:Button
    lateinit var question_no:TextView
    lateinit var question:TextView
    lateinit var roomNo:TextView
    lateinit var questionImg:ImageView
    lateinit var ans:EditText
    lateinit var hint_txt:TextView
    lateinit var hint_btn:ImageButton
    lateinit var wrong:TextView
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView
    lateinit var btn:Button
    var count = 0
    var answer:String=""
    var questionNo=0
    private lateinit var progress:ProgressBar
    private lateinit var blackScreen:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_question)
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
        back = findViewById(R.id.back_btn)
        submit_btn = findViewById(R.id.submit_btn)
        btn = findViewById(R.id.submit_btn)
        ans =findViewById(R.id.Answerfield)
        wrong = findViewById(R.id.wrong_answer)
        hint_btn = findViewById(R.id.hint_Btn)
        hint_txt = findViewById(R.id.hint_text)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this, CountdownActivity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener{
            val intent = Intent(this,CountdownActivity::class.java)
            startActivity(intent)
        }
        ans.setOnClickListener {
            wrong.visibility = View.INVISIBLE
        }
        val answerList:MutableList<String> = mutableListOf()
        answerList.add("Denmark")
        answerList.add("denmark")
        answerList.add("norway")
        answerList.add("Norway")
        answerList.add("NORWAY")
        answerList.add("DENMARK")
        val closeList:MutableList<String> = mutableListOf()
        closeList.add("Harald")
        closeList.add("harald")
        closeList.add("bluetooth")
        closeList.add("Bluetooth")
        closeList.add("HARALD")
        closeList.add("BLUETOOTH")
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
            dialog.show()
            view.findViewById<Button>(R.id.confirm_btn).setOnClickListener {
                hint_txt.text = "Hint: Caesar cipher with key 5"
                hint_txt.setVisibility(View.VISIBLE)
                dialog.dismiss()
            }
            view.findViewById<ImageView>(R.id.close).setOnClickListener {
                dialog.dismiss()
            }
        }
        submit_btn.setOnClickListener{
            answer = ans.text.toString().trim()
            if(answer in answerList ) {
                wrong.setVisibility(View.INVISIBLE)
                val view = View.inflate(this, R.layout.demo_question_dialog, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)
                val dialog = builder.create()
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.0f
                dialog.window!!.attributes = lp
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
                dialog.show()
                view.findViewById<ImageView>(R.id.close).setOnClickListener {
                    dialog.dismiss()
                }
            }
            else if(answer in closeList) {
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
            }
            else {
                wrong.visibility = View.VISIBLE
            }

        }
    }

//        sharedPreferences = PrefManager(this)
//        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
//        ans = findViewById(R.id.Answerfield)
//        hint_btn = findViewById(R.id.hint_show)
//        submit_btn = findViewById(R.id.submit_btn)
//        question_no = findViewById(R.id.question_no)
//        roomNo = findViewById(R.id.room_no)
//        hint_txt = findViewById(R.id.hint_text)
//        wrong =findViewById(R.id.wrong_answer)
//        question = findViewById(R.id.question)
//        background = findViewById(R.id.blurBackground)

//        val authToken = sharedPreferences.getAuthCode()
//        questionImg = findViewById(R.id.question_image)
//        val questionList:List<QuestionList> = sharedPreferences.getQuestionList()
//
//        Log.e("Roomid", "${sharedPreferences.getRoomid()}")
//        Log.e("Questionlist", "${sharedPreferences.getQuestionList()}")
//
//        Log.e("powerupResponse","${sharedPreferences.getPowerup()}")
//
//
//
//
//
//
//        hint_btn.setOnClickListener {
//            viewModel.getHintDetails(
//                    sharedPreferences.getRoomid().toString(),
//                    "Bearer ${authToken.toString()}"
//            )
//            viewModel.hintResponse.observe(this, {
//                if (it != null) {
//                    val view = View.inflate(this, R.layout.confirm_use_hint, null)
//                    val builder = AlertDialog.Builder(this)
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    val lp = dialog.window!!.attributes
//                    lp.dimAmount = 0.0f
//                    dialog.window!!.attributes = lp
//                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//                    dialog.show()
//
////                    background.setVisibility(View.VISIBLE)
//
//                    var temp = it.data.hint
//                    view.findViewById<Button>(R.id.confirm_btn).setOnClickListener {
//                        hint_txt.text = temp
//                        hint_txt.setVisibility(View.VISIBLE)
//                        hint_btn.setClickable(false)
//                        dialog.dismiss()
////                        background.setVisibility(View.INVISIBLE)
//
//                    }
//                    view.findViewById<Button>(R.id.close).setOnClickListener {
//                        dialog.dismiss()
////                        background.setVisibility(View.INVISIBLE)
//                    }
//
//                }
//                Log.e("Hint", "$it")
//            })
//        }
//        viewModel.getQuestionDetails(
//                sharedPreferences.getRoomid().toString(),
//                "Bearer ${authToken.toString()}"
//        )
//        viewModel.questionResponse.observe(this, {
//            if (it != null) {
//
//                roomNo.text = "Room No" + "${sharedPreferences.getRoomNo()}"
//                question.text = it.data.text
//                question_no.text = "Q" + "${it.data.questionNo}"
//                Picasso.get().load("${it.data.media}").into(questionImg)
//                sharedPreferences.setRoomNo(it.data.questionNo)
//                viewModel.getQuestionNo(it.data.questionNo)
//
//            }
//            Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
//        })
//        for(item in questionList.indices) {
//            if(questionList[item].roomid == sharedPreferences.getRoomid()) {
//                count = item
//            }
//
//        }
////        Log.e("count","$count")
//        submit_btn.setOnClickListener {
//            answer = ans.text.toString().trim()
//            if (TextUtils.isEmpty(answer)) {
//                ans.error = "Enter a valid answer"
//                return@setOnClickListener
//
//            }
//            val submitRequest = SubmitRequest(sharedPreferences.getRoomid().toString(), answer)
//            viewModel.sendQuestionDetails("Bearer ${authToken.toString()}", submitRequest)
//            Log.e("cOUNT", "$count")
//        }
//
//
//        viewModel.submitResponse.observe(this, {
//            if (it.data!!.correctAnswer == false && it.data!!.closeAnswer == false) {
//                wrong.setVisibility(View.VISIBLE)
//            } else if (it.data!!.closeAnswer == true) {
//                wrong.setVisibility(View.INVISIBLE)
//                val view = View.inflate(this, R.layout.close_answer, null)
//                val builder = AlertDialog.Builder(this)
//                builder.setView(view)
//
//                val dialog = builder.create()
//                val lp = dialog.window!!.attributes
//                lp.dimAmount = 0.0f
//                dialog.window!!.attributes = lp
//                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//
//                dialog.show()
////                background.setVisibility(View.VISIBLE)
//                view.findViewById<ImageButton>(R.id.close).setOnClickListener {
//                    dialog.dismiss()
////                    background.setVisibility(View.INVISIBLE)
//                }
//            } else if (it.data.correctAnswer == true) {
//                wrong.setVisibility(View.INVISIBLE)
//                if (questionList[count].questionList[0] == it.data.questionId) {
//                    if (it.data.nextRoomUnlocked == false) {
//                        val view = View.inflate(this, R.layout.right_answer_first, null)
//                        val builder = AlertDialog.Builder(this)
//                        builder.setView(view)
//                        val dialog = builder.create()
//                        val lp = dialog.window!!.attributes
//                        lp.dimAmount = 0.0f
//                        dialog.window!!.attributes = lp
//                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//
//                        dialog.show()
//
////                        background.setVisibility(View.VISIBLE)
//                        view.findViewById<Button>(R.id.close).setOnClickListener {
//                            viewModel.getQuestionDetails(
//                                    sharedPreferences.getRoomid().toString(),
//                                    "Bearer ${authToken}"
//                            )
//                            viewModel.questionResponse.observe(this, {
//                                if (it != null) {
//                                    roomNo.text = "Room No" + "${sharedPreferences.getRoomNo()}"
//                                    question.text = it.data.text
//                                    question_no.text = "Q" + "${it.data.questionNo}"
//                                    Picasso.get().load("${it.data.media}").into(questionImg)
//                                }
//                            })
//                            dialog.dismiss()
////                            background.setVisibility(View.INVISIBLE)
//
//                        }
//
//                    } else if (it.data.nextRoomUnlocked == true) {
//                        val view = View.inflate(this, R.layout.right_answer, null)
//                        val builder = AlertDialog.Builder(this)
//                        builder.setView(view)
//                        val dialog = builder.create()
//                        val lp = dialog.window!!.attributes
//                        lp.dimAmount = 0.0f
//                        dialog.window!!.attributes = lp
//                        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//
//
//                        dialog.show()
////                        background.setVisibility(View.VISIBLE)
//
//                        view.findViewById<Button>(R.id.close).setOnClickListener {
//                            viewModel.getQuestionDetails(
//                                    sharedPreferences.getRoomid().toString(),
//                                    "Bearer ${authToken}"
//                            )
//                            viewModel.questionResponse.observe(this, {
//                                if (it != null) {
//                                    roomNo.text = "Room No" + "${sharedPreferences.getRoomNo()}"
//                                    question.text = it.data.text
//                                    question_no.text = "Q" + "${it.data.questionNo}"
//                                    Picasso.get().load("${it.data.media}").into(questionImg)
//                                    Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
//                                    viewModel.getQuestionNo(it.data.questionNo)
//
//
//                                }
//                                Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
//                            })
//
//                            dialog.dismiss()
////                            background.setVisibility(View.INVISIBLE)
//                        }
//
//                    }
//
//                } else if (questionList[count].questionList[1] == it.data.questionId && it.data.nextRoomUnlocked == true) {
//                    val view = View.inflate(this, R.layout.right_answer, null)
//                    val builder = AlertDialog.Builder(this)
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    val lp = dialog.window!!.attributes
//                    lp.dimAmount = 0.0f
//                    dialog.window!!.attributes = lp
//                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//
//
//                    dialog.show()
////                    background.setVisibility(View.VISIBLE)
//
//                    view.findViewById<Button>(R.id.continue_room).setOnClickListener {
//                        viewModel.getQuestionDetails(
//                                sharedPreferences.getRoomid().toString(),
//                                "Bearer ${authToken}"
//                        )
//                        viewModel.questionResponse.observe(this, {
//                            if (it != null) {
//                                roomNo.text = "Room No" + "${sharedPreferences.getRoomNo()}"
//                                question.text = it.data.text
//                                question_no.text = "Q" + "${it.data.questionNo}"
//                                Picasso.get().load("${it.data.media}").into(questionImg)
//                                Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
//                                viewModel.getQuestionNo(it.data.questionNo)
//
//
//                            }
//                            Log.e("com.ieeevit.enigma8.model.question.QuestionResponse", "$it")
//                        })
//
//                        dialog.dismiss()
////                        background.setVisibility(View.INVISIBLE)
//                    }
//
//                    view.findViewById<Button>(R.id.another_room).setOnClickListener {
//                        val intent = Intent(this, FirstActivity::class.java)
//                        startActivity(intent)
//                    }
//
//                } else if (questionList[count].questionList[2] == it.data.questionId && it.data.nextRoomUnlocked == false) {
//                    val view = View.inflate(this, R.layout.right_answer, null)
//                    val builder = AlertDialog.Builder(this)
//                    builder.setView(view)
//                    Log.e("3rd", "true")
//                    var dialog = builder.create()
//                    val lp = dialog.window!!.attributes
//                    lp.dimAmount = 0.0f
//                    dialog.window!!.attributes = lp
//                    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//
//                    dialog.show()
////                    background.setVisibility(View.VISIBLE)
//
//                    view.findViewById<Button>(R.id.continue_room).setVisibility(View.INVISIBLE)
//
//
//                    view.findViewById<Button>(R.id.another_room).setOnClickListener {
//                        val intent = Intent(this, FirstActivity::class.java)
//                        startActivity(intent)
////                        background.setVisibility(View.INVISIBLE)
//                    }
//
//                }
//            }
//
//        })
//    }
////    fun blurBackground() {
////        Blurry.with(this)
////                .radius(10)
////                .sampling(8)
////                .async()
////                .animate(500)
////                .onto(findViewById(R.id.root_view))
////        val radius = 20f
////
////        val decorView = window.decorView
////        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
////        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
////          val rootView = findViewById<View>(R.id.root_view) as ViewGroup
////        //Set drawable to draw in the beginning of each blurred frame (Optional).
////        //Can be used in case your layout has a lot of transparent space and your content
////        //gets kinda lost after after blur is applied.
////        //Set drawable to draw in the beginning of each blurred frame (Optional).
////        //Can be used in case your layout has a lot of transparent space and your content
////        //gets kinda lost after after blur is applied.
////        val windowBackground = decorView.background
////
////        background.setupWith(rootView)
////            .setFrameClearDrawable(windowBackground)
////            .setBlurAlgorithm(RenderScriptBlur(this))
////            .setBlurRadius(radius)
////            .setBlurAutoUpdate(true)
////            .setHasFixedTransformationMatrix(true) // Or false if it's in a scrolling container or might be animated
//
////    }
//
//
//    //    fun DB1(authToken:String) {
////
////
////    }
////    fun DB2(authToken: String) {
////
////
////    }
////    fun DB3() {
////
////
////    }
//    fun isLast(roomNo: Int): Boolean{
//
//        if (roomNo%3==0) {
//            return true
//        }
//        return false
//    }

}