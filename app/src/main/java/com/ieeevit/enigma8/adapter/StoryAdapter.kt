package com.ieeevit.enigma8.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.CurrentStory
import com.ieeevit.enigma8.model.Story
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class StoryAdapter (
        var context: Context?,
        var Story: List<Story>
) :
        RecyclerView.Adapter<StoryAdapter.Holder>() {

    var MSG_TYPE_RIGHT : Int = 0
    var MSG_TYPE_LEFT : Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        if(viewType == MSG_TYPE_RIGHT) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.character2, parent, false)
            return Holder(view)
        }
        else if(viewType == MSG_TYPE_LEFT){
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.character1, parent, false)
            return Holder(view)
        }
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_story, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val story  = Story[position]
        holder.name.text = story.sender

        holder.show_message.text = story.message
//        Glide.with(context!!).load(imageurl).into(holder.profile_image)

    }
    override fun getItemCount(): Int {
        return Story.size
    }
    class Holder(itemView: View ) : RecyclerView.ViewHolder(itemView){
        val show_message: TextView = itemView.findViewById(R.id.show_messag)
        val name : TextView = itemView.findViewById(R.id.name)
//        val profile_image: ImageView = itemView.findViewById(R.id.profile_image)

    }

    override fun getItemViewType(position: Int): Int {
        val char1: String = Story[0].sender

        if(Story[position].sender == char1) {
                return MSG_TYPE_RIGHT
            }
            else {
                return MSG_TYPE_LEFT

            }
        }
        }
