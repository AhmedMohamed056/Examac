package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.cis.itiapi.databinding.FragmentShowoldexamteacherBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class showoldexamteacherFragment : Fragment() {
    private lateinit var binding:FragmentShowoldexamteacherBinding
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
        binding= FragmentShowoldexamteacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        val teacherActivity=activity as TeacherActivity
//        teacherActivity.setSupportActionBar(binding.showExamToolbar)
        binding.showoldexamteacher.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_showoldexamteacherFragment_to_navigation_home)
            teacherActivity.showNavbar()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            showoldexamteacherFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}