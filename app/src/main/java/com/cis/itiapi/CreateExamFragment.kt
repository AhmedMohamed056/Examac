package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.itiapi.databinding.FragmentCreateExamBinding
import com.cis.itiapi.models.Adapters.Adaptershowquestionadded
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CreateExamFragment : Fragment() {
    private lateinit var binding:FragmentCreateExamBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var currentuser= FirebaseAuth.getInstance().currentUser
    private var listofquestions:List<String> = listOf()
    private var listofanswers:List<String> = listOf()
    private var listofquestionadded:MutableList<examdetaild> = mutableListOf()
    private lateinit var adapter:Adaptershowquestionadded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCreateExamBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        val teacherActivity=activity as TeacherActivity
//        teacherActivity.setSupportActionBar(binding.createExamToolbar)
        binding.createExamToolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_createExamFragment_to_navigation_upload)
            teacherActivity.showNavbar()
        }
        binding.btnaddquestions.setOnClickListener {
            val quistions=binding.questiondesgneid.text.toString()
            val answers=binding.answersdesgneid.text.toString()
            if (quistions.isEmpty()){
                binding.questiondesgneid.error="please enter question"
            }
            if (answers.isEmpty()){
                binding.answersdesgneid.error="please enter answer"
            }
            if (quistions.isNotEmpty()&&answers.isNotEmpty()) {
                listofquestions= listOf(quistions)
                listofanswers= listOf(answers)
                binding.answersdesgneid.setText("")
                binding.questiondesgneid.setText("")
                //return question and answer as one object
                for (i in listofquestions.indices) {
                    listofquestionadded.add(examdetaild(listofquestions[i], listofanswers[i]))
                }
                setuprecyclerview()
                //setupdata()
            }
        }

        binding.btnsavequestions.setOnClickListener {
            if (listofquestions.isNotEmpty()&&listofanswers.isNotEmpty()) {
                databaseReference.child("exams").child(currentuser!!.uid)
                    .addValueEventListener(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            //get value
                            val examdate = arguments?.getString("examdate")
                            val examid = arguments?.getString("examid")
                            //
                            //val modeldata=uploadexamsteacherdata(examdate!!,examid!!)
                            databaseReference.child("exams")
                                .child("exam").child(examid!!).child(examdate!!).child(currentuser!!.uid)
                                .setValue(listofquestionadded)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showtoast(error.message)
                        }
                    })
            }
            Navigation.findNavController(it).navigate(R.id.action_createExamFragment_to_navigation_upload)
            teacherActivity.showNavbar()
        }
    }


    private fun setuprecyclerview() {
         adapter = Adaptershowquestionadded(requireContext(),listofquestionadded)
        binding.recyclerviewshowquiandans.layoutManager=LinearLayoutManager(context)
        binding.recyclerviewshowquiandans.adapter=adapter

    }
//    private fun setupdata() {
//        if (::adapter.isInitialized){
//            adapter.setData(listofquestionadded)
//        }
//    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateExamFragment().apply {
                arguments = Bundle().apply {

                }
            }

    }
}
