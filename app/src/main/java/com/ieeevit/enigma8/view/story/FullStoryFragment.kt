package com.ieeevit.enigma8.view.story

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.FullStoryAdapter
import com.ieeevit.enigma8.adapter.RoomsAdapter
import com.ieeevit.enigma8.adapter.StoryAdapter
import com.ieeevit.enigma8.model.FullStory
import com.ieeevit.enigma8.model.Full_Story
import com.ieeevit.enigma8.model.story.Story
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.CurrentStoryViewModel
import com.ieeevit.enigma8.viewModel.FullStoryViewModel
import com.ieeevit.enigma8.viewModel.RoomViewModel


class FullStoryFragment : Fragment() {
    private lateinit var sharedPreferences: PrefManager


    private lateinit var adapter: FullStoryAdapter
    private lateinit var fullStoryView: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var blackScreen: ImageView

//    lateinit var nxt_btn: Button
//    lateinit var continue_btn: Button
//    var count:Int = 1

    private val viewModel: FullStoryViewModel by lazy {
        ViewModelProvider(this, FullStoryViewModel.Factory())
            .get(FullStoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_full_story, container, false)


        sharedPreferences = PrefManager(requireContext())
        fullStoryView = root.findViewById(R.id.recycle)
//        continue_btn = root.findViewById(R.id.conti)
        var dataList: MutableList<Full_Story> = mutableListOf()
        var DataList: MutableList<Full_Story> = mutableListOf()
        val authToken = sharedPreferences.getAuthCode()

        val actionbartxt = root.findViewById<TextView>(R.id.tabHeading)
        val jones = root.findViewById<TextView>(R.id.character1_name)
        val ali = root.findViewById<TextView>(R.id.character2_name)
        blackScreen  = root.findViewById(R.id.overlay)
        progress =root.findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)


        val shader2 : Shader = LinearGradient(0f, 0f,0f,jones.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        jones.paint.shader = shader2
        ali.paint.shader = shader2


        viewModel.getFullStoryDetails(
            sharedPreferences.getRoomid().toString(),
            "Bearer $authToken"
        )
        viewModel.fullstoryResponse.observe(viewLifecycleOwner, {
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE
            dataList.clear()
            if (it != null) {
                for (item in it.data.story) {
                    if (item.roomNo != 0) {

                        dataList.add(Full_Story(item.roomNo, item.sender, item.message))
                    }
                }


                    adapter = FullStoryAdapter(requireContext(), dataList)
                    fullStoryView.layoutManager = LinearLayoutManager(requireContext())
                    fullStoryView.adapter = adapter
                    adapter.notifyDataSetChanged()

//
//                continue_btn.setOnClickListener {
//                    val intent = Intent(this, ProfileActivity::class.java)
//                    startActivity(intent)
//                }


            }
            Log.e("StoryResponse", "$it")

        })
return root
    }
}