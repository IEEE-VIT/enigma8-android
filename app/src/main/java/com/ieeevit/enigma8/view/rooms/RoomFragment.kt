package com.ieeevit.enigma8.view.rooms

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.RoomsAdapter
import com.ieeevit.enigma8.model.question.QuestionList
import com.ieeevit.enigma8.model.question.QustionStatus
import com.ieeevit.enigma8.model.room.RoomsOuter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.RoomViewModel

class RoomFragment : Fragment() {
    private lateinit var sharedPreferences: PrefManager

    private lateinit var adapter: RoomsAdapter
    private lateinit var roomView: RecyclerView
    private lateinit var starNeed:TextView
    private lateinit var heading:TextView
    private lateinit var roomUnlock:TextView
    private lateinit var starcount :TextView

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

                adapter = RoomsAdapter(requireContext(), dataList,viewModel,"Bearer ${authToken.toString()}",viewLifecycleOwner)
                roomView.layoutManager = GridLayoutManager(context, 2)
                roomView.adapter = adapter
                adapter.notifyDataSetChanged()
                sharedPreferences.setJourneyList(journeyList)
                sharedPreferences.setQuestionList(questionList)


            }


        })






        return root
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
