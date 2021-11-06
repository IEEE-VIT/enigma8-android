package com.ieeevit.enigma8.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.RoomsAdapter
import com.ieeevit.enigma8.model.RoomsOuter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.RoomViewModel

class RoomFragment : Fragment() {
    private lateinit var sharedPreferences: PrefManager

    private lateinit var adapter: RoomsAdapter
    private lateinit var roomView: RecyclerView

    private val viewModel: RoomViewModel by lazy {
        ViewModelProvider(this,RoomViewModel.Factory())
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
        Log.e("Token","$authToken")

        viewModel.getRoomDetails("Bearer ${authToken.toString()}")
        var dataList : MutableList<RoomsOuter> = mutableListOf()


        viewModel.roomStatus.observe(viewLifecycleOwner, {

            if (it != null) {
                for (item in it.data.data) {
                    dataList.add(
                        RoomsOuter(
                            item.room.title,
                            item.room.media,
                            R.drawable.room_fire_black,
                            R.drawable.room_fire_black,
                            R.drawable.room_fire_black,
                            R.drawable.room_torch,
                            R.drawable.room_torch,
                            R.drawable.room_torch,
                                item.room._id
                        )
                    )

                }
                adapter = RoomsAdapter(requireContext(), dataList)
                roomView.layoutManager = GridLayoutManager(context,2)
                roomView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            Log.e("Response","$it")
        })
        return root
    }

}
