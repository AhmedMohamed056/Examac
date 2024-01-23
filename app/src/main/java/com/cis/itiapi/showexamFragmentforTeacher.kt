package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.itiapi.databinding.FragmentShowexamFragmentforTeacherBinding
import com.cis.itiapi.models.Adapters.teacherAdaptershowexam
import com.cis.itiapi.models.Adapters.teacheradaptershowoldexam
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class showexamFragmentforTeacher : Fragment() {
    private lateinit var binding: FragmentShowexamFragmentforTeacherBinding
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
        binding= FragmentShowexamFragmentforTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.accessexamteacherbtn.setOnClickListener {
                val examdate=binding.enterexamDate.text.toString()
                val examid=binding.enterexamid.text.toString()
                if (examdate.isEmpty()){
                    binding.enterexamDate.error="please enter exam date"
                }
                if (examid.isEmpty()){
                    binding.enterexamid.error="please enter exam id"
                }
                if (examid.isNotEmpty()&&examdate.isNotEmpty()) {
                //send data to another fragment
                val bundle = Bundle()
                bundle.putString("examdateforupdated", examdate)
                bundle.putString("examidforupdated", examid)
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_exam_to_showQuistionsFragmentteacher,bundle)
                val teacheractivity = activity as TeacherActivity
                teacheractivity.hideNavBar()
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            showexamFragmentforTeacher().apply {
                arguments = Bundle().apply {

                }
            }
    }
}