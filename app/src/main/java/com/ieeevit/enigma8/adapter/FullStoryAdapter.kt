package com.ieeevit.enigma8.adapter

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.Full_Story
import java.util.*

class FullStoryAdapter (
        var context: Context,
        var Story: List<Full_Story>
) :
        RecyclerView.Adapter<FullStoryAdapter.Holder>() {

    var MSG_TYPE_RIGHT : Int = 0
    var MSG_TYPE_LEFT : Int = 1
    var MSG_TYPE_VOICE: Int = 2
    var MSG_TYPE_NARRATOR: Int = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        if (viewType == MSG_TYPE_RIGHT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character2, parent, false)
            return Holder(view)
        } else if (viewType == MSG_TYPE_LEFT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character1, parent, false)
            return Holder(view)
        } else if (viewType == MSG_TYPE_NARRATOR) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.narrator, parent, false)
            return Holder(view)
        } else if (viewType == MSG_TYPE_VOICE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.voice_card_story, parent, false)
            return Holder(view)
        }
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_full_story, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val story  = Story[position]

        holder.show_message.text = story.message

        val shader : Shader = LinearGradient(0f, 0f,0f,holder.name.lineHeight.toFloat(), intArrayOf(context.getColor(R.color.light_yellow), context.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        holder.name.paint.shader = shader
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


            if (Story[position].sender.toUpperCase() == "Jones") {
                return MSG_TYPE_LEFT
            } else if (Story[position].sender.toUpperCase() == "ALI") {
                return MSG_TYPE_RIGHT
            } else if (Story[position].sender.toUpperCase() == "NARRATOR") {
                return MSG_TYPE_NARRATOR
            } else if (Story[position].sender.toUpperCase() == "VOICE") {
                return MSG_TYPE_VOICE
            } else {
                return MSG_TYPE_LEFT
            }

    }
        }
