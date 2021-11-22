package com.ieeevit.enigma8.view.leaderboard

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.LeaderboardAdapter
import com.ieeevit.enigma8.model.leaderboard.Leaderboard
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.LeaderboardViewModel
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat.getSystemService
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.view.rooms.RoomsActvity


class LeaderboardFragment : Fragment() {
    private lateinit var progress:ProgressBar
    private lateinit var blackScreen:ImageView


    private lateinit var sharedPreferences: PrefManager
    private lateinit var leaderboardView: RecyclerView
    private lateinit var adapter: LeaderboardAdapter
    private val viewModel : LeaderboardViewModel by lazy {
        ViewModelProvider(this, LeaderboardViewModel.Factory())
            .get(LeaderboardViewModel::class.java)
    }
    var count = 0
    var dataList : MutableList<Leaderboard> = mutableListOf()
    var datam : MutableList<Leaderboard> = mutableListOf()
    var isLoading = false
    var totalPage = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var page = 1

        dataList.clear()
        adapter = LeaderboardAdapter(requireContext(), dataList)

        val root = inflater.inflate(R.layout.fragment_leadeboard, container , false)


        sharedPreferences = PrefManager(requireContext())
        val authToken = sharedPreferences.getAuthCode()
        leaderboardView = root.findViewById(R.id.Leaderboard_view)
        leaderboardView.visibility = View.INVISIBLE
        progress = root.findViewById(R.id.progressBar)
        blackScreen = root.findViewById(R.id.overlay)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)
        Handler().postDelayed({
            leaderboardView.visibility = View.VISIBLE
        }, 1000)
        val userRank = root.findViewById<TextView>(R.id.user_rankvar)
        val userName = root.findViewById<TextView>(R.id.user_namevar)
        val userSolved = root.findViewById<TextView>(R.id.user_solvedvar)
        val userScore = root.findViewById<TextView>(R.id.user_scorevar)
        val headtext = root.findViewById<TextView>(R.id.head_leaderboard)
        val search = root.findViewById<SearchView>(R.id.user_Search)

        val painthead = headtext.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,headtext.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),Shader.TileMode.REPEAT)
        painthead.shader = shader1


            viewModel.getLeaderboardDetails(
                page,
                10,
                "Bearer $authToken",
                null
            )




        viewModel.leaderboardResponse.observe(viewLifecycleOwner, {
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE

            if (it != null) {
                userRank.text = it.data?.userRank?.rank.toString()
                userName.text = it.data?.userRank?.username.toString()
                userSolved.text = it.data?.userRank?.questionsSolved.toString()
                userScore.text = it.data?.userRank?.score.toString()

                sharedPreferences.setUsername(userName.text.toString())

                if (it.data?.totalPage != null) {
                    totalPage = it.data.totalPage
                }
                Log.e("datalist1","$dataList")
//-------------------------------------------------------------------------------------------------------------------------------------------
                if (page <= totalPage) {
                    for (item in it.data!!.leaderboard) {
                        if (item !in dataList) {
                            datam.add(
                                Leaderboard(
                                    item.username,
                                    item.score,
                                    item.questionsSolved,
                                    item.rank
                                )
                            )

                        }
                    }
                }

//                dataList = datam.distinct() as MutableList<Leaderboard>
                dataList = datam
                dataList.sortBy { it.rank }
//                Log.e("dat")

                Log.e("datalist2","$dataList")
                adapter = LeaderboardAdapter(requireContext(), dataList)
                leaderboardView.layoutManager = LinearLayoutManager(context)
                leaderboardView.adapter = adapter
                if (progress != null) {
                    progress.visibility = View.INVISIBLE
                }

            }
            Log.e("Response","$it")

//    ----------------------------------------------------------------------------------------------------------------------------------------
            })



        leaderboardView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.e("page 1", "$page $totalPage")


                Log.e("page 2", "$page $totalPage")
                if (page < totalPage) {
                    page++


                    Log.e("page inside if", "$page $totalPage")

                    val visibleItemCount = leaderboardView.layoutManager?.childCount
                    val pastVisibleItem = 1
//                          LinearLayoutManager(context).findFirstVisibleItemPosition()
                    val total = adapter.itemCount

                    if (!isLoading) {
                        if (visibleItemCount != null) {
                            if ((visibleItemCount + pastVisibleItem) >= total) {
//                                        page++
                                isLoading = true
                                if (progress != null) {
                                    progress.visibility = View.VISIBLE
                                }
                                Log.e("b4 getfunc", "leaderboard")
                                viewModel.getLeaderboardDetails(
                                    page,
                                    10,
                                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imphc3NpbXNoYW1pbUBnbWFpbC5jb20iLCJpYXQiOjE2MzYzOTM2NTN9.K2Va3dAa5DIPGqt-ENTlzoulLcB-_IYE5ApqaaOlH4c",
                                    null
                                )

                                Handler().postDelayed({
                                    if (::adapter.isInitialized) {
                                        adapter.notifyDataSetChanged()
                                    } else {
                                        adapter =
                                            LeaderboardAdapter(requireContext(), dataList)
                                        leaderboardView.adapter = adapter
                                    }
                                    isLoading = false
                                    if (progress != null) {
                                        progress.visibility = View.GONE
                                    }
                                }, 500)
                            }
                        }

                    }

                    super.onScrolled(recyclerView, dx, dy)
                }

            }
        })

        search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getLeaderboardDetails(
                    1,
                    10,
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imphc3NpbXNoYW1pbUBnbWFpbC5jb20iLCJpYXQiOjE2MzYzOTM2NTN9.K2Va3dAa5DIPGqt-ENTlzoulLcB-_IYE5ApqaaOlH4c",
                    query
                )


                 var newList: MutableList<Leaderboard> = mutableListOf()
            viewModel.leaderboardResponse.observe(viewLifecycleOwner, {



                    for (item in it.data!!.leaderboard) {
                        Log.e("item", "$item")
                        Log.e("que1","$query")
                        if (query != null) {
                            Log.e("que2","$query")
                            if (query in item.username && item !in newList) {
                                newList.add(
                                    Leaderboard(
                                        item.username,
                                        item.score,
                                        item.questionsSolved,
                                        item.rank
                                    )
                                )

                            }
                        }
                    }
                Log.e("newList","$newList")

                dataList = newList
                Log.e("datalist3","$dataList")
                    adapter = LeaderboardAdapter(requireContext(), newList)
                    leaderboardView.layoutManager = LinearLayoutManager(context)
                    leaderboardView.adapter = adapter

            })




                return false
            }

            override fun onQueryTextChange(newText: String?) :Boolean{
                if (newText == ""){


                }
                return false
            }


        })


        val cancelIcon = search.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLUE)
        val textView = search.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.RED)
        val searchIcon = search.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLUE)


        return root
    }
}

    //-----------------------------------------------------------------------------





