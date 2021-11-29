package com.ieeevit.enigma8.view.rooms

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.RoomsAdapter
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.question.QuestionList
import com.ieeevit.enigma8.model.question.QustionStatus
import com.ieeevit.enigma8.model.room.CheckResponse
import com.ieeevit.enigma8.model.room.RoomsOuter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.powerup.PowerupActivity
import com.ieeevit.enigma8.view.question.QuestionActivity
import com.ieeevit.enigma8.viewModel.RoomViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomFragment : Fragment() {
    private lateinit var sharedPreferences: PrefManager

    private lateinit var adapter: RoomsAdapter
    private lateinit var roomView: RecyclerView
    private lateinit var starNeed:TextView
    private lateinit var heading:TextView
    private lateinit var roomUnlock:TextView
    private lateinit var starcount :TextView
    private lateinit var progress: ProgressBar
    private lateinit var blackScreen: ImageView

    private val viewModel: RoomViewModel by lazy {
        ViewModelProvider(this, RoomViewModel.Factory())
            .get(RoomViewModel::class.java)
    }




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {



        val root = inflater.inflate(R.layout.fragment_room, container, false)
        sharedPreferences = PrefManager(requireContext())
        val authToken = sharedPreferences.getAuthCode()
        roomView = root.findViewById(R.id.roomView)
        roomUnlock = root.findViewById(R.id.roomUnlock)
        starcount = root.findViewById(R.id.star_counter)
        blackScreen  = root.findViewById(R.id.overlay)
        progress = root.findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)
        heading = root.findViewById(R.id.heading)
        val shader1 : Shader = LinearGradient(0f, 0f, 0f, heading.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
        heading.paint.shader = shader1
        val shader2 : Shader =  LinearGradient(0f, 0f, 0f, roomUnlock.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_blue), requireContext().getColor(R.color.dark_blue)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
        roomUnlock.paint.shader = shader2

        Log.e("Token", "$authToken")



        var star = 0
        var roomid :String = ""
        var roomsDataList:MutableList<String> = mutableListOf()
        val starList:MutableList<Int> = mutableListOf()
        var unlock:MutableList<Boolean> = mutableListOf()
        var questionList:MutableList<QuestionList> = mutableListOf()
        starcount.text = sharedPreferences.getStars().toString()

        viewModel.getRoomDetails("Bearer ${authToken.toString()}")
        var dataList : MutableList<RoomsOuter> = mutableListOf()
        var journeyList : MutableList<QustionStatus> = mutableListOf()
        var torch1 = 0
        var torch2 = 0
        var torch3 = 0



        viewModel.roomStatus.observe(viewLifecycleOwner, {
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE
            dataList.clear()
            journeyList.clear()
            questionList.clear()
            roomsDataList.clear()
            if (it != null) {
                sharedPreferences.setRoomOneId(it.data.data[0].room._id)
                Log.e("allRooms", "$it")
                roomUnlock.text = "New Room unlocks in ${it.data.nextRoomsUnlockedIn}"
                starcount.text = "${it.data.stars}"
                for (item in it.data.data) {
                    torch1 = roomTorch(item.journey.questionsStatus[0])
                    torch2 = roomTorch(item.journey.questionsStatus[1])
                    torch3 = roomTorch(item.journey.questionsStatus[2])
                    roomsDataList.add(item.room._id)
                        dataList.add(RoomsOuter(item.room.title, item.room.media, torch1, torch2, torch3, R.drawable.room_torch, R.drawable.room_torch, R.drawable.room_torch, item.room._id, item.room.roomNo, item.journey.roomUnlocked,item.journey.powerupUsed,item.room.starQuota,item.journey.questionsStatus ,item.journey.powerupSet,item.starLeft))
                        journeyList.add(QustionStatus(item.journey.questionsStatus))
                        questionList.add(QuestionList(item.room._id,item.room.questionId))

                    }

//                sharedPreferences.setRoomList(roomsDataList)
//                Log.e("RoomList", "${sharedPreferences.getRoomList()}")

                adapter = RoomsAdapter(requireContext(), dataList,"Bearer ${authToken.toString()}",viewLifecycleOwner, object: RoomsAdapter.OnRoomClickListner {
                    override fun onRoomClick(id: String) {
                        checkIfRoomUnlocked(id,"Bearer ${authToken.toString()}")
                    }

                })
                roomView.layoutManager = GridLayoutManager(context, 2)
                roomView.adapter = adapter
                adapter.notifyDataSetChanged()
                sharedPreferences.setJourneyList(journeyList)
                sharedPreferences.setQuestionList(questionList)

            }
        })



//        viewModel.checkResponse.observe(viewLifecycleOwner, {
//            if (it.data.status == "complete") {
//                val view = View.inflate(requireContext(), R.layout.room_done_dialog, null)
//                val builder = android.app.AlertDialog.Builder(requireContext())
//                builder.setView(view)
//                val dialog = builder.create()
//                val lp = dialog.window!!.attributes
//                lp.dimAmount = 0.1f
//                dialog.window!!.attributes = lp
//                dialog.show()
//                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//                view.findViewById<ImageView>(R.id.close).setOnClickListener {
//                    dialog.dismiss()
//                }
//            }
//            else if (it.data.status == "canUnlock") {
//                val intent = Intent(requireContext(), PowerupActivity::class.java)
//                startActivity(intent)
//            }
//            else if(it.data.status == "unlocked") {
//                val intent = Intent(requireContext(), QuestionActivity::class.java)
//                startActivity(intent)
//            }
//            else if (it.data.status == "locked") {
//                val view = View.inflate(requireContext(), R.layout.room_unlock_dialog, null)
//                val builder = android.app.AlertDialog.Builder(requireContext())
//                builder.setView(view)
//                val dialog = builder.create()
//                val lp = dialog.window!!.attributes
//                lp.dimAmount = 0.1f
//                dialog.window!!.attributes = lp
//                dialog.show()
//                view.findViewById<TextView>(R.id.room_unlock).text = "You need ${it.data.starsNeeded} more"
//                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//
//                view.findViewById<ImageView>(R.id.close).setOnClickListener {
//                    dialog.dismiss()
//                }
//
//            }
//        })

        return root
    }


    fun checkIfRoomUnlocked(roomid: String, authToken: String) {
        Api.retrofitService.getRoomUnlockDeatils(roomid,authToken)
                .enqueue(object : Callback<CheckResponse> {
                    override fun onResponse(
                            call: Call<CheckResponse>,
                            response: Response<CheckResponse>
                    ) {
                        if (response.body() != null) {
                            val it = response.body()
                            if (it!!.data.status == "complete") {
                                val view = View.inflate(requireContext(), R.layout.room_done_dialog, null)
                                val builder = android.app.AlertDialog.Builder(requireContext())
                                builder.setView(view)
                                val dialog = builder.create()
                                val lp = dialog.window!!.attributes
                                lp.dimAmount = 0.1f
                                dialog.window!!.attributes = lp
                                dialog.show()
//                                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                                view.findViewById<ImageView>(R.id.close).setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                            else if (it.data.status == "canUnlock") {
                                val intent = Intent(requireContext(), PowerupActivity::class.java)
                                startActivity(intent)
                            }
                            else if(it.data.status == "unlocked") {
                                val intent = Intent(requireContext(), QuestionActivity::class.java)
                                startActivity(intent)
                            }
                            else if (it.data.status == "locked") {
                                val view = View.inflate(requireContext(), R.layout.room_unlock_dialog, null)
                                val builder = android.app.AlertDialog.Builder(requireContext())
                                builder.setView(view)
                                val dialog = builder.create()
                                val lp = dialog.window!!.attributes
                                lp.dimAmount = 0.1f
                                dialog.window!!.attributes = lp
                                dialog.show()
                                view.findViewById<TextView>(R.id.room_unlock).text = "You need ${it.data.starsNeeded} more"
//                                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                                view.findViewById<ImageView>(R.id.close).setOnClickListener {
                                    dialog.dismiss()
                                }

                            }


                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<CheckResponse>() {}.type
                            val errorResponse: CheckResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.data
                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })

    }

    fun roomTorch(text: String):Int {
        if(text=="unlocked") {
            return R.drawable.room_fire_orange
        }
        else if (text=="solved"){
            return R.drawable.room_fire_green
        }
        return R.drawable.room_fire_black
    }

}
