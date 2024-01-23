package com.cis.itiapi

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cis.itiapi.databinding.FragmentGBinding
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.teachersignupdata
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate


class FragmentG : Fragment() {
    private lateinit var binding:FragmentGBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var currentuser= FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.createexambtn.setOnClickListener {
            val examname=binding.enterexamname.text.toString()
            val examdate= LocalDate.now().toString()
            if (examname.isEmpty()){
                binding.enterexamname.error="please enter exam name"
            }
            if (examname.isNotEmpty()) {
                val typeIndicator = object : GenericTypeIndicator<List<uploadexamsteacherdata>>() {}
                databaseReference.child("users").child(currentuser!!.uid).addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val teacherdata=snapshot.getValue(teachersignupdata::class.java)
                       val examid= teacherdata?.Phone+examname
                        //send data to another fragment
                        val bundle = Bundle()
                        bundle.putString("examdate", examdate)
                        bundle.putString("examid", examid)
                        Navigation.findNavController(it)
                            .navigate(R.id.action_navigation_upload_to_createExamFragment,bundle)
                        ////
                        val modeldataclass =uploadexamsteacherdata(examid,examdate)
                        databaseReference.child("exams").child(currentuser!!.uid).runTransaction(object :Transaction.Handler{
                            override fun doTransaction(currentData: MutableData): Transaction.Result {
                                val list2:List<uploadexamsteacherdata> = listOf(modeldataclass)
                                val list = currentData.getValue(typeIndicator) ?: mutableListOf()
                                val finallist=list+list2
                                currentData.value=finallist
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
                                    showtoast( "Transaction succeeded.")
                                }
                            }
                        })
                    }

                    override fun onCancelled(error: DatabaseError) {
                        showtoast(error.message)
                    }
                })
                val teacheractivity = activity as TeacherActivity
                teacheractivity.hideNavBar()
            }
        }
        binding.uploadexambtn.setOnClickListener {
            val examname = binding.enterexamname.text.toString()
            val examdate = LocalDate.now().toString()
            if (examname.isEmpty()) {
                binding.enterexamname.error = "please enter exam name"
            }
            if (examname.isNotEmpty()) {
                val typeIndicator = object : GenericTypeIndicator<List<uploadexamsteacherdata>>() {}
                databaseReference.child("users").child(currentuser!!.uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val teacherdata = snapshot.getValue(teachersignupdata::class.java)
                            val examid = teacherdata?.Phone + examname
                            //send data to another fragment
                            val bundle = Bundle()
                            bundle.putString("examdate", examdate)
                            bundle.putString("examid", examid)
                            Navigation.findNavController(it)
                                .navigate(
                                    R.id.action_navigation_upload_to_fragmentUploadExam,
                                    bundle
                                )
                            ////
                            val modeldataclass =uploadexamsteacherdata(examid,examdate)
                            databaseReference.child("exams").child(currentuser!!.uid).runTransaction(object :Transaction.Handler{
                                override fun doTransaction(currentData: MutableData): Transaction.Result {
                                    val list2:List<uploadexamsteacherdata> = listOf(modeldataclass)
                                    val list = currentData.getValue(typeIndicator) ?: mutableListOf()
                                    val finallist=list+list2
                                    currentData.value=finallist
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
                                        showtoast( "Transaction succeeded.")
                                    }
                                }
                            })
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showtoast(error.message)
                        }
                    })
                val teacheractivity = activity as TeacherActivity
                teacheractivity.hideNavBar()
            }
        }
        }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentG().apply {
                arguments = Bundle().apply {

                }
            }
    }
}