package com.cis.itiapi.models.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cis.itiapi.R
import com.cis.itiapi.models.examdetaild

class teacherAdaptershowexam(private val context: Context,private val listofoldexaminhome:ArrayList<examdetaild>):RecyclerView.Adapter<teacherAdaptershowexam.showexamquestion>() {
    inner class showexamquestion(itemView: View):RecyclerView.ViewHolder(itemView){
        var showquestion: TextView?=null
        var showanswer: TextView?=null
        var delete: ImageView?=null
        init {
            showquestion=itemView.findViewById(R.id.showquestionstoupdate)
            showanswer=itemView.findViewById(R.id.showanswerstoupdate)
            delete=itemView.findViewById(R.id.deleteexamtoupdate)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): showexamquestion {
        val view= LayoutInflater.from(context).inflate(R.layout.showteacherexamtoupdate,parent,false)
        return showexamquestion(view)
    }

    override fun getItemCount(): Int {
        return listofoldexaminhome.size
    }

    override fun onBindViewHolder(holder: showexamquestion, position: Int) {
        val item=listofoldexaminhome[position]
        holder.showquestion?.text=item.examquestions
        holder.showanswer?.text=item.exambestanswers
        holder.delete?.setOnClickListener {
            listofoldexaminhome.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}