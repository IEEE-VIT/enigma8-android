package com.ieeevit.enigma8.view.leaderboard

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
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
import com.ieeevit.enigma8.ProgressBarAnimation
import java.util.*


class LeaderboardFragment : Fragment() {
    private lateinit var progress:ProgressBar
    private lateinit var blackScreen:ImageView
    private lateinit var days1: TextView
    private lateinit var days2: TextView
    private lateinit var hours1: TextView
    private lateinit var hours2: TextView
    private lateinit var minutes1: TextView
    private lateinit var minutes2: TextView
    private lateinit var currentCalendar: Calendar
    private lateinit var eventCalendar: Calendar
    private  var timeLeft:Long = 0
    private var currentTime: Long = 0
    private var eventStartTime: Long = 0



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


        adapter = LeaderboardAdapter(requireContext(), dataList)

        val root = inflater.inflate(R.layout.fragment_leadeboard, container , false)
        var scrollView = root.findViewById<ScrollView>(R.id.scrollView2)

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
        days1 = root.findViewById(R.id.days1)
        hours1 = root.findViewById(R.id.hours1)
        minutes1 = root.findViewById(R.id.minutes1)
        days2 = root.findViewById(R.id.days2)
        hours2 = root.findViewById(R.id.hours2)
        minutes2 = root.findViewById(R.id.minutes2)
        val progressBar = root.findViewById<ProgressBar>(R.id.progressBar)
        init()
        viewModel.getEnigmaStatus("Token $authToken")

        viewModel.enigmaStatus.observe(viewLifecycleOwner, {
            if (it != null) {

                Log.e("ResponseCounter","$it")

                if(it.data.date > 0 && it.data.enigmaStarted == true) {
                    currentTime = currentCalendar.timeInMillis
                    eventStartTime = currentTime + (it.data.date) * 1000.toLong()

                    Log.e("eventstart", eventStartTime.toString())
                    Log.e("current", currentTime.toString())

                    timeLeft = eventStartTime - currentTime

                    startTimer(timeLeft)
                }
                else {

                }


            }
        })

        val shader3 : Shader= LinearGradient(0f, 0f,0f,days1.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.4f,0.6f),Shader.TileMode.REPEAT)
        days1.paint.shader = shader3
        days2.paint.shader = shader3
        hours1.paint.shader = shader3
        hours2.paint.shader = shader3
        minutes1.paint.shader = shader3
        minutes2.paint.shader = shader3





        val searchIcon = root.findViewById<ImageView>(R.id.search_mag_icon)
        val userRank = root.findViewById<TextView>(R.id.user_rankvar)
        val userName = root.findViewById<TextView>(R.id.user_namevar)
        val userSolved = root.findViewById<TextView>(R.id.user_solvedvar)
        val userScore = root.findViewById<TextView>(R.id.user_scorevar)
        val headtext = root.findViewById<TextView>(R.id.head_leaderboard)
        val search = root.findViewById<SearchView>(R.id.user_Search)
        val cancelIcon = search.findViewById<ImageView>(R.id.search_close_btn)
        val textView = search.findViewById<TextView>(R.id.search_src_text)
        val ranktt = root.findViewById<TextView>(R.id.ranktable)
        val usertt = root.findViewById<TextView>(R.id.usernametable)
        val solvedtt = root.findViewById<TextView>(R.id.solvedtable)
        val scorett = root.findViewById<TextView>(R.id.scoretable)
        textView.text = ""

        val shader2 : Shader= LinearGradient(0f, 0f,0f,ranktt.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.4f,0.6f),Shader.TileMode.REPEAT)
        ranktt.paint.shader = shader2
        usertt.paint.shader = shader2
        solvedtt.paint.shader = shader2
        scorett.paint.shader = shader2

        val shader4 : Shader= LinearGradient(0f, 0f,0f,userRank.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.4f,0.6f),Shader.TileMode.REPEAT)
        userRank.paint.shader = shader4
        userName.paint.shader = shader4
        userSolved.paint.shader = shader4
        userScore.paint.shader = shader4

        val painthead = headtext.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,headtext.lineHeight.toFloat(), intArrayOf(requireContext().getColor(R.color.light_yellow), requireContext().getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),Shader.TileMode.REPEAT)
        painthead.shader = shader1


        viewModel.getLeaderboardDetails(
            page,
            10,
            "Bearer $authToken",
            null
        )



        viewModel.eleaderboardResponse.observe(viewLifecycleOwner,{
            if(it == "Please set a username first!"){

                userRank.text = "0"
                userName.text = "no username"
                userSolved.text = "0"
                userScore.text = "0"

            }
            else if (it == "page number not valid"){
                dataList.clear()
                scrollView.background = resources.getDrawable(R.drawable.nouserexists_leaderboard)
                adapter = LeaderboardAdapter(requireContext(), dataList)
                leaderboardView.layoutManager = LinearLayoutManager(context)
                leaderboardView.adapter = adapter
                adapter.notifyDataSetChanged()
            }


        })
        count = 0
        viewModel.leaderboardResponse.observe(viewLifecycleOwner, {
            count++

            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE
            search.visibility = View.VISIBLE

            scrollView.background = resources.getDrawable(R.drawable.ic_scrollview_bg)
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
                Log.e("page inside if", "$page $totalPage")
                if (page <= totalPage) {
                    for (item in it.data!!.leaderboard) {
                        if (item !in dataList  ) {
                            Log.e("hih","$datam")
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
                Log.e("datam","$datam")
                adapter = LeaderboardAdapter(requireContext(), dataList)
                leaderboardView.layoutManager = LinearLayoutManager(context)
                leaderboardView.adapter = adapter


            }
            Log.e("Response","$it")

//    ----------------------------------------------------------------------------------------------------------------------------------------




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
//                    val total = adapter.itemCount

                    if (!isLoading) {
                        if (visibleItemCount != null) {
                            if (page <= totalPage) {
//                                        page++

                                    isLoading = true


                                    viewModel.getLeaderboardDetails(
                                        page,
                                        10,
                                        "Bearer $authToken",
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
//
                                    }, 500)
                                }

                            }
                        }

                    }

                    super.onScrolled(recyclerView, dx, dy)
                }

            search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dataList.clear()
                    page = 1
                    viewModel.getLeaderboardDetails(
                        1,
                        30,
                        "Bearer $authToken",
                        query
                    )


//                 var newList: MutableList<Leaderboard> = mutableListOf()
//            viewModel.leaderboardResponse.observe(viewLifecycleOwner, {
//
//
//
//                    for (item in it.data!!.leaderboard) {
//                        Log.e("item", "$item")
//                        Log.e("que1","$query")
//                        if (query != null) {
//                            Log.e("que2","$query")
//                            if (query in item.username && item !in newList) {
//                                newList.add(
//                                    Leaderboard(
//                                        item.username,
//                                        item.score,
//                                        item.questionsSolved,
//                                        item.rank
//                                    )
//                                )
//
//                            }
//                        }
//                    }
//                Log.e("newList","$newList")
//
//                dataList = newList
//                Log.e("datalist3","$dataList")
//                    adapter = LeaderboardAdapter(requireContext(), newList)
//                    leaderboardView.layoutManager = LinearLayoutManager(context)
//                    leaderboardView.adapter = adapter
//
//            })




                return false
            }

            override fun onQueryTextChange(newText: String?) :Boolean{
                if (newText == ""){


                }
                return false
            }


        })
            //        cancelIcon.setColorFilter(getResources().getColor(R.color.light_yellow))
//        textView.setTextColor(
//            getResources().getColor(R.color.light_yellow))

//        searchIcon.setColorFilter(getResources().getColor(R.color.light_yellow))



            cancelIcon?.setOnClickListener {
                searchIcon.visibility = View.VISIBLE
                dataList.clear()
                page = 1
                scrollView.background = resources.getDrawable(R.drawable.ic_scrollview_bg)
                viewModel.getLeaderboardDetails(
                    page,
                    10,
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imphc3NpbXNoYW1pbUBnbWFpbC5jb20iLCJpYXQiOjE2MzczODc2Mjh9.bWeW2T_iu4x8qDpLVjqax_DDspzL5N-JbgDm4A6HwW4",
                    null
                )
//            dataList = datam
//            val adapter2 = LeaderboardAdapter(requireContext(), dataList)
//            leaderboardView.adapter = adapter2
                textView.text = ""
            }

        })



        return root
    }

    private fun init() {

        currentCalendar = Calendar.getInstance(TimeZone.getDefault())
        eventCalendar = Calendar.getInstance(TimeZone.getDefault())
    }

    private fun startTimer(timeDifference: Long) {

        val countdownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                days1.text = ((millisUntilFinished / (24 * 60 * 60 * 1000))/10).toString()
                days2.text = ((millisUntilFinished / (24 * 60 * 60 * 1000))%10).toString()
                hours1.text = ((millisUntilFinished / (60 * 60 * 1000) % 24)/10).toString()
                hours2.text= ((millisUntilFinished / (60 * 60 * 1000) % 24)%10).toString()
                minutes1.text = ((millisUntilFinished / (60 * 1000) % 60)/10).toString()
                minutes2.text = ((millisUntilFinished / (60 * 1000) % 60)%10).toString()
            }

            override fun onFinish() {
                viewModel.getEnigmaStatus("Token ${sharedPreferences.getAuthCode()}")



            }

        }
        countdownTimer.start()

    }
}

    //-----------------------------------------------------------------------------





