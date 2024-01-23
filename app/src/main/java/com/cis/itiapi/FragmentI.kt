package com.cis.itiapi

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.itiapi.databinding.FragmentIBinding
import com.cis.itiapi.models.Adapters.Adapterstudentshowexam
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.examdstudentetaild
import com.cis.itiapi.models.uploadexamsstudentdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class FragmentI : Fragment() {
    private lateinit var binding:FragmentIBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var currentuser= FirebaseAuth.getInstance().currentUser
    private lateinit var adapter:Adapterstudentshowexam
    private lateinit var examstudentdata:uploadexamsstudentdata
    private var listofquestionretrived:ArrayList<examdetaild> = arrayListOf()
    private var listofanswersretrived:ArrayList<String> = arrayListOf()
    private var listofallstudentexamdetaild:ArrayList<examdstudentetaild> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentIBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.progressbarstudentshowexam.visibility=View.VISIBLE
        setuprecyclerview()
        val loginactivity=activity as loginActivity
        val formater= DateTimeFormatter.ofPattern("HH:mm")
        val examend= LocalTime.now().format(formater).toString()
        val examenddate=LocalDate.now().toString()
        val bundel=arguments
        val examdate=bundel?.getString("examdatetoshow")
        val examid=bundel?.getString("examidtoshow")
        val examstart=bundel?.getString("examstart")
//        loginactivity.setSupportActionBar(binding.stuShowExamToolbar)
        binding.stuShowExamToolbar.setNavigationOnClickListener {
            loginactivity.backtofindexamfragment(binding.fragmentshowexam)
        }
        databaseReference.child("exams").child("exam").child(examid!!).child(examdate!!).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (datasnap in snapshot.children){
                        for (data in datasnap.children) {
                            val examdetaild = data.getValue<examdetaild>(examdetaild::class.java)
                            //val examquestions=examdetaild!!.examquestions
                            val examanswer=examdetaild!!.exambestanswers
                            listofquestionretrived.add(examdetaild!!)
                            listofanswersretrived.add(examanswer)
                            binding.progressbarstudentshowexam.visibility=View.GONE
                            setuprecyclerview()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showtoast(error.message)
            }
        })

        binding.btnsavestudentanswers.setOnClickListener {
            examstudentdata=uploadexamsstudentdata(examid!!,examdate!!,examstart!!,examend,examenddate)
            val typeIndicator=object :GenericTypeIndicator<List<uploadexamsstudentdata>>(){}
            val modeldataclass = uploadexamsstudentdata(examid!!,examdate!!,examstart!!,examend,examenddate)
            databaseReference.child("exams").child(currentuser!!.uid).runTransaction(object :Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        val list2: List<uploadexamsstudentdata> = listOf(modeldataclass)
                        val list = currentData.getValue(typeIndicator) ?: mutableListOf()
                        val finallist = list + list2
                        currentData.value = finallist
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(
                        error: DatabaseError?,
                        committed: Boolean,
                        currentData: DataSnapshot?
                    ) {
                        if (error != null) {
                            showtoast("Transaction failed: ${error.message}")
                        } else {
                            showtoast("Transaction succeeded.")
                        }
                    }
                })
            loginactivity.backtofindexamfragment(binding.fragmentshowexam)

        }
    }

    private fun setuprecyclerview() {
        adapter= Adapterstudentshowexam(requireContext(),listofquestionretrived)
        binding.recyclerviewstudentshowexam.layoutManager=LinearLayoutManager(context)
        binding.recyclerviewstudentshowexam.adapter=adapter
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentI().apply {
                arguments = Bundle().apply {

                }
            }
    }
}