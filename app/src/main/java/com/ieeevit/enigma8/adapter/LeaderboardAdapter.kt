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

//    override fun getFilter():Filter{
//        return object :Filter(){
//            override fun performFiltering(constraint:CharSequence?):FilterResults {
//                val charSearch = constraint?.toString()?.toLowerCase()
//                val filterResult = Filter.FilterResults()
//                filterResult.values = if (charSearch == null || charSearch.isEmpty())
//                    Leaderboard
//                else
//                    Leaderboard.filter {
//                        it.username.toLowerCase().contains(charSearch)
//                    }
//                return filterResult
//            }
//
//
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                Leaderboard = results?.values as ArrayList<com.ieeevit.enigma8.model.Leaderboard>
//                notifyDataSetChanged()
//            }
//        }
//    }

    class Holder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        var rankv: TextView = itemView.findViewById(R.id.rankval)
        var userv: TextView = itemView.findViewById(R.id.usernameval)
        var scorev: TextView = itemView.findViewById(R.id.scoreval)
        var solvedv: TextView = itemView.findViewById(R.id.solvedval)

    }
    var MSG_TYPE_USER: Int = 0
    var MSG_TYPE_NORMAL: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

//        if (viewType == MSG_TYPE_USER) {
//            view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.leaderboard_usercard, parent, false)
//        }
//            else {
//                 view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.leader_card, parent, false)
//            }

        var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.leader_card, parent, false)


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


//    override fun getItemViewType(position: Int): Int {
//
//        sharedPreferences = PrefManager(context)
//        val userrank_name = sharedPreferences.getUsername()
//
//        if(Leaderboard[position].username == userrank_name) {
//            return MSG_TYPE_USER
//        }
//        else {
//            return MSG_TYPE_NORMAL
//
//        }
//    }
}