package com.ieeevit.enigma8.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.LeaderboardAdapter
/**
 * A simple [Fragment] subclass.
 */
class Leaderboard : Fragment() {

    private lateinit var leaderboardView: RecyclerView
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_leadeboard, container , false)
        leaderboardView = root.findViewById(R.id.Leaderboard_view)
        adapter = LeaderboardAdapter(requireContext())
        leaderboardView.layoutManager = LinearLayoutManager(context)
        leaderboardView.adapter = adapter
        adapter.notifyDataSetChanged()
        return root
    }

}