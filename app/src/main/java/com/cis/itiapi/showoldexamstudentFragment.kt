package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.cis.itiapi.databinding.FragmentShowoldexamstudentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class showoldexamstudentFragment : Fragment() {
    private lateinit var binding:FragmentShowoldexamstudentBinding
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_showoldexamstudent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        val loginactivity=activity as loginActivity
//        loginactivity.setSupportActionBar(binding.stuShowExamToolbar)
//        binding.showoldexamstudent.setNavigationOnClickListener {
//            loginactivity.backtohomefragment(binding.showstudentexamfragment)
//        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            showoldexamstudentFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}