package com.ieeevit.enigma8.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.main.Leaderboard

class LeaderboardAdapter(var context: Context) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {



    private val Sl = arrayOf(
        "RANK" , "01",
        "02", "03", "04",
        "05", "06", "07",
        "08"
    )

    private val nameld = arrayOf(
        "NAME" ,"Jassim", "Archita",
        "Jatin", "Alok",
        "irfan", "Rufus",
        "Amogh", "Bhargav"
    )

    private val score = arrayOf(
        "SCORE","3459","4611", "3459", "4611",
        "3459","4611", "3459", "4611"
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.leader_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.Sl.text = Sl[i]
        viewHolder.nameld.text = nameld[i]
        viewHolder.score.text = score[i]

    }

    override fun getItemCount(): Int {
        return Sl.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Sl: TextView
        var nameld: TextView
        var score: TextView

        init {
            Sl = itemView.findViewById(R.id.Sl)
            nameld= itemView.findViewById(R.id.nameld)
            score = itemView.findViewById(R.id.score)

        }
    }

}
