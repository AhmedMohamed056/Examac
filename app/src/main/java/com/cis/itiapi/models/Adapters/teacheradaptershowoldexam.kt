package com.cis.itiapi.models.Adapters

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cis.itiapi.R
import com.cis.itiapi.models.uploadexamsteacherdata


class teacheradaptershowoldexam(private val context: Context,private val listofoldexaminhome:ArrayList<uploadexamsteacherdata>) :RecyclerView.Adapter<teacheradaptershowoldexam.showoldexamVH>() {
    inner class showoldexamVH(itemView: View):RecyclerView.ViewHolder(itemView){
        var examid: TextView?=null
        var examdate: TextView?=null
        var deleteoldexam:ImageView?=null
        init {
            examid=itemView.findViewById(R.id.examid)
            examdate=itemView.findViewById(R.id.examdate)
            deleteoldexam=itemView.findViewById(R.id.deleteoldexam)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): showoldexamVH {
        val view= LayoutInflater.from(context).inflate(R.layout.showexaminhome,parent,false)
        return showoldexamVH(view)
    }

    override fun getItemCount(): Int {
        return listofoldexaminhome.size
    }

    override fun onBindViewHolder(holder: showoldexamVH, position: Int) {
        val item=listofoldexaminhome[position]
        holder.examid?.text=item.examid
        holder.examdate?.text=item.examdate
        holder.deleteoldexam?.setOnClickListener {
            listofoldexaminhome.removeAt(position)
            notifyItemRemoved(position)
//            val builder= AlertDialog.Builder(context)
//            builder.setTitle("Warning").setIcon(R.drawable.baseline_warning_24)
//            .setMessage("are you sure you want to delete this exam?")
//            .setPositiveButton("Ok"
//                ) { dialog, which ->
//                    listofoldexaminhome.removeAt(position)
//                    notifyItemRemoved(position)
//                }.setNegativeButton("Cancel"
//                ) { dialog, which ->
//                    Toast.makeText(context,"exam don't delete",Toast.LENGTH_LONG).show()
//                }
        }
//        holder.itemView.setOnClickListener {
//            //send data to another fragment
//            val bundle = Bundle()
//            bundle.putString("examdate", item.examdate)
//            bundle.putString("examid", item.examid)
//            Toast.makeText(context,item.examid,Toast.LENGTH_LONG).show()
//            Navigation.findNavController(it)
//                .navigate(R.id.action_navigation_home_to_showoldexamteacherFragment,bundle)
//        }


    }

}