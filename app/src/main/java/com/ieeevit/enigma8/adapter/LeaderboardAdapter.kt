package com.ieeevit.enigma8.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.leaderboard.Leaderboard
import com.ieeevit.enigma8.utils.PrefManager


class LeaderboardAdapter(var context: Context,var Leaderboard: List<Leaderboard>) : RecyclerView.Adapter<LeaderboardAdapter.Holder>() {

    private lateinit var sharedPreferences: PrefManager
    lateinit var view : View



    class Holder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        var rankv: TextView = itemView.findViewById(R.id.rankval)
        var userv: TextView = itemView.findViewById(R.id.usernameval)
        var scorev: TextView = itemView.findViewById(R.id.scoreval)
        var solvedv: TextView = itemView.findViewById(R.id.solvedval)

    }
    var MSG_TYPE_USER: Int = 0
    var MSG_TYPE_NORMAL: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        if (viewType == MSG_TYPE_USER) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.leaderboard_usercard, parent, false)
        }
            else {
                 view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.leader_card, parent, false)
            }

//            var view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.leader_card, parent, false)


        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var leaderboard  = Leaderboard[position]
        holder.userv.text= leaderboard.username
        holder.scorev.text= leaderboard.score.toString()
        holder.solvedv.text= leaderboard.questionsSolved.toString()
        holder.rankv.text= leaderboard.rank.toString()





    }
    override fun getItemCount(): Int {
        return Leaderboard.size
    }


    override fun getItemViewType(position: Int): Int {

        sharedPreferences = PrefManager(context)
        val userrank_name = sharedPreferences.getUsername()

        if(Leaderboard[position].username == userrank_name) {
            return MSG_TYPE_USER
        }
        else {
            return MSG_TYPE_NORMAL

        }
    }
}

//----------------------------------------------------------------------------------
//
//    private val Sl = arrayOf(
//        "RANK" , "01",
//        "02", "03", "04",
//        "05", "06", "07",
//        "08"
//    )
//    private val nameld = arrayOf(
//        "NAME" ,"Jassim", "Archita",
//        "Jatin", "Alok",
//        "irfan", "Rufus",
//        "Amogh", "Bhargav"
//    )
//
//    private val score = arrayOf(
//        "SCORE","3459","4611", "3459", "4611",
//        "3459","4611", "3459", "4611"
//    )
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
//        val v = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.leader_card, viewGroup, false)
//        return ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
//        viewHolder.Sl.text = Sl[i]
//        viewHolder.nameld.text = nameld[i]
//        viewHolder.score.text = score[i]
//
//    }
//
//    override fun getItemCount(): Int {
//        return Sl.size
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var Sl: TextView
//        var nameld: TextView
//        var score: TextView
//
//        init {
//            Sl = itemView.findViewById(R.id.Sl)
//            nameld= itemView.findViewById(R.id.nameld)
//            score = itemView.findViewById(R.id.score)
//
//        }
//    }
//
//}
