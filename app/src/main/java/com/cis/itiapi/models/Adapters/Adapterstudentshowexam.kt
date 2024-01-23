package com.cis.itiapi.models.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.cis.itiapi.R
import com.cis.itiapi.models.AnswerData
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.examdstudentetaild
import com.cis.itiapi.models.remotedata.RetrofitConfig
import com.cis.itiapi.models.uploadexamsstudentdata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Adapterstudentshowexam(
    private val context: Context,
    private val listofquestionretrived: ArrayList<examdetaild>
):RecyclerView.Adapter<Adapterstudentshowexam.showstudentexamVH>() {
    private val studentanswerslist:MutableList<String> = mutableListOf()
    private val studentdegreeslist:MutableList<String> = mutableListOf()
    private val examstudentdetailed:MutableList<examdstudentetaild> = mutableListOf()
    inner class showstudentexamVH(itemView: View):RecyclerView.ViewHolder(itemView){
        var showquestion: TextView?=null
        var enteranswer: TextView?=null
        var showdegree: TextView?=null
        init {
            showquestion=itemView.findViewById(R.id.showquestionforstudent)
            enteranswer=itemView.findViewById(R.id.addstudentanswer)
            showdegree=itemView.findViewById(R.id.addstudentdegree)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): showstudentexamVH {
        val view=LayoutInflater.from(context).inflate(R.layout.testforstudent,parent,false)
        return showstudentexamVH(view)
    }

    override fun getItemCount(): Int {
        return listofquestionretrived.size
    }

    override fun onBindViewHolder(holder: showstudentexamVH, position: Int) {
        val item=listofquestionretrived[position]
        holder.showquestion?.text=item.examquestions
        holder.itemView.setOnClickListener {
            val builder=AlertDialog.Builder(context)
            builder.setView(R.layout.studentdialoganswer)
            val alertDialog=builder.show()
            val showquestionforstudent=alertDialog.findViewById<TextView>(R.id.showquestionforstudenttoaddanswer)
            showquestionforstudent?.text=item.examquestions
            val addstudentanswer=alertDialog.findViewById<EditText>(R.id.addstudentanswertofirbase)
            val studentanswer=addstudentanswer?.text
            val btnsendqstudentanswer=alertDialog.findViewById<Button>(R.id.btnsendqstudentanswer)
            btnsendqstudentanswer?.setOnClickListener {
                studentanswerslist.add(position,studentanswer.toString())
                holder.enteranswer?.text=studentanswerslist[position]
                val answerdata=AnswerData(item.exambestanswers,studentanswerslist[position])
                val coroutinScope= CoroutineScope(Dispatchers.Main)
                coroutinScope.launch {
                    val response=RetrofitConfig.getServiceInstance().getAnswerLabel(answerdata)
                    if (response.isSuccessful){
                        val bodyresponse=response.body()
                       val label= bodyresponse?.label
                        studentdegreeslist.add(position,label!!)
                        holder.showdegree?.text=studentdegreeslist[position]
                        //Toast.makeText(context,studentdegreeslist[position],Toast.LENGTH_LONG).show()
                        examstudentdetailed.add(examdstudentetaild(item.examquestions,item.exambestanswers,studentanswerslist[position],studentdegreeslist[position]))

                    }
                }
                alertDialog.dismiss()
            }


        }
    }
}