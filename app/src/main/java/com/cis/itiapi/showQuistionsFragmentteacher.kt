package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.itiapi.databinding.FragmentShowQuistionsFragmentteacherBinding
import com.cis.itiapi.models.Adapters.teacherAdaptershowexam
import com.cis.itiapi.models.Adapters.teacheradaptershowoldexam
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.StorageReference


class showQuistionsFragmentteacher : Fragment() {
    private lateinit var binding: FragmentShowQuistionsFragmentteacherBinding
    private lateinit var adapter:teacherAdaptershowexam
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage: StorageReference
    private var currentuser= FirebaseAuth.getInstance().currentUser
    private var listofoldexaminhome:ArrayList<examdetaild> = arrayListOf()
    private var listofoldexam:ArrayList<examdetaild> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentShowQuistionsFragmentteacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teacherActivity=activity as TeacherActivity
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.progressbarteachershowexam.visibility=View.VISIBLE
//        teacherActivity.setSupportActionBar(binding.showExamToolbar)
        binding.showExamToolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_showQuistionsFragmentteacher_to_navigation_exam)
            teacherActivity.showNavbar()
        }
        //get value
        val examdate = arguments?.getString("examdateforupdated")
        val examid = arguments?.getString("examidforupdated")
        val typeIndicator = object : GenericTypeIndicator<List<examdetaild>>() {}
        databaseReference.child("exams").child("exam").child(examid!!).child(examdate!!).child(currentuser!!.uid).runTransaction(object :Transaction.Handler{
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val list = currentData.getValue(typeIndicator) ?: mutableListOf()
                listofoldexaminhome.addAll(list)
                currentData.value=listofoldexaminhome
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
                    binding.progressbarteachershowexam.visibility=View.GONE
                    setuprecyclerview()
                    showtoast( "Transaction succeeded.")
                }
            }
        })
        binding.addquestionsbtninupdatefragment.setOnClickListener {
            val builder= AlertDialog.Builder(requireContext())
            builder.setView(R.layout.dialogaddquestion)
            val alertDialog=builder.show()
            val button=alertDialog.findViewById<Button>(R.id.btnsendquistionandanswer)
            val addquestiontofirbase=alertDialog.findViewById<EditText>(R.id.addquestiontofirbase)
            val addanswertofirbase=alertDialog.findViewById<EditText>(R.id.addanswertofirbase)
            button?.setOnClickListener {
                binding.progressbarteachershowexam.visibility=View.VISIBLE
                val questionadded=addquestiontofirbase?.text.toString()
                val answeradded=addanswertofirbase?.text.toString()
                listofoldexam.clear()
                listofoldexam.add(examdetaild(questionadded,answeradded))
                databaseReference.child("exams").child("exam").child(examid!!).child(examdate!!).child(currentuser!!.uid).runTransaction(object :Transaction.Handler{
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        val list = currentData.getValue(typeIndicator) ?: mutableListOf()
                        listofoldexaminhome= (list+listofoldexam) as ArrayList<examdetaild>
                        currentData.value=listofoldexaminhome
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
                            binding.progressbarteachershowexam.visibility=View.GONE
                            setuprecyclerview()
                            showtoast( "Transaction succeeded.")
                        }
                    }
                })
                alertDialog.dismiss()

            }

        }
        binding.updateexambtn.setOnClickListener {
            binding.progressbarteachershowexam.visibility=View.VISIBLE
            databaseReference.child("exams").child("exam").child(examid!!).child(examdate!!).child(currentuser!!.uid)
                .runTransaction(object :Transaction.Handler{
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        currentData.value=listofoldexaminhome
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
                            binding.progressbarteachershowexam.visibility=View.GONE
                            setuprecyclerview()
                            showtoast( "Transaction succeeded.")
                        }
                    }
                })
            Navigation.findNavController(it).navigate(R.id.action_showQuistionsFragmentteacher_to_navigation_exam)
            teacherActivity.showNavbar()
        }

    }



    private fun setuprecyclerview() {
        adapter= teacherAdaptershowexam(requireContext(),listofoldexaminhome)
        binding.recyclerviewteachershowexam.layoutManager= LinearLayoutManager(context)
        binding.recyclerviewteachershowexam.adapter=adapter
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            showQuistionsFragmentteacher().apply {
                arguments = Bundle().apply {

                }
            }
    }
}