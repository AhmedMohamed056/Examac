package com.cis.itiapi.models.Adapters

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cis.itiapi.R
import com.cis.itiapi.models.uploadexamsstudentdata


class Adapterstudentoldexam(private val context: Context, private val listofoldexaminhome:ArrayList<uploadexamsstudentdata>):RecyclerView.Adapter<Adapterstudentoldexam.ShowoldexamVH>() {
    inner class ShowoldexamVH(itemView:View):RecyclerView.ViewHolder(itemView){
        var studentexamid: TextView?=null
        var studentexamdate: TextView?=null
        var studentendexamdate:TextView?=null
        var examstart:TextView?=null
        var examend:TextView?=null
        var deletestudentoldexam: ImageView?=null
       init {
            studentexamid=itemView.findViewById(R.id.studentexamid)
            studentexamdate=itemView.findViewById(R.id.studentexamdate)
            deletestudentoldexam=itemView.findViewById(R.id.deletestudentoldexam)
            studentendexamdate=itemView.findViewById(R.id.studentendexamdate)
            examstart=itemView.findViewById(R.id.examstart)
            examend=itemView.findViewById(R.id.examend)
}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowoldexamVH {
        val view=LayoutInflater.from(context).inflate(R.layout.showexaminhomeforstudent,parent,false)
        return ShowoldexamVH(view)
    }

    override fun getItemCount(): Int {
        return listofoldexaminhome.size
    }

    override fun onBindViewHolder(holder: ShowoldexamVH, position: Int) {
        val item=listofoldexaminhome[position]
        holder.studentexamid?.text=item.examid
        holder.studentexamdate?.text=item.examdate
        holder.studentendexamdate?.text=item.examenddate
        holder.examstart?.text=item.examstart
        holder.examend?.text=item.examend
        holder.deletestudentoldexam?.setOnClickListener {
            listofoldexaminhome.removeAt(position)
            notifyItemRemoved(position)
//            val builder=AlertDialog.Builder(context)
//            builder.setTitle("Warning").setIcon(R.drawable.baseline_warning_24)
//            .setMessage("are you sure you want to delete this exam?")
//            .setPositiveButton("Ok"
//                ) { dialog, which ->
//                    listofoldexaminhome.removeAt(position)
//                    notifyItemRemoved(position)
//                }.setNegativeButton("Cansel"
//                ) { dialog, which -> }


        }
    }

}