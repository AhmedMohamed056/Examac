package com.cis.itiapi.models.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cis.itiapi.CreateExamFragment
import com.cis.itiapi.R
import com.cis.itiapi.models.examdetaild

class Adaptershowquestionadded(private val context: Context,private val listofquestionadded:MutableList<examdetaild> ) : RecyclerView.Adapter<Adaptershowquestionadded.showVH>() {
    //private var listofdata= mutableListOf<examdetaild>()
    inner class showVH(itemView:View):RecyclerView.ViewHolder(itemView){
        var showquestion:TextView?=null
        var showanswer:TextView?=null
        var delete:ImageView?=null
        init {
            showquestion=itemView.findViewById(R.id.showquestions)
            showanswer=itemView.findViewById(R.id.showanswers)
            delete=itemView.findViewById(R.id.deleteexam)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): showVH {
        val view=LayoutInflater.from(context).inflate(R.layout.showteacherquestionadded,parent,false)
        return showVH(view)
    }

    override fun getItemCount(): Int {
        return listofquestionadded.size
    }

    override fun onBindViewHolder(holder: showVH, position: Int) {
        val item=listofquestionadded[position]
        holder.showquestion?.text=item.examquestions
        holder.showanswer?.text=item.exambestanswers
        holder.delete?.setOnClickListener {
            listofquestionadded.removeAt(position)
            notifyItemRemoved(position)
        }

    }


//    fun setData(listofquestionadded: MutableList<examdetaild>) {
//        listofdata.addAll(listofquestionadded)
//        notifyDataSetChanged()
//    }

}