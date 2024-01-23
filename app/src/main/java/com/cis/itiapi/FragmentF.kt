package com.cis.itiapi

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.cis.itiapi.databinding.FragmentFBinding
import com.cis.itiapi.models.uploadexamsstudentdata
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class FragmentF : Fragment() {
    private lateinit var binding:FragmentFBinding
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
        binding=FragmentFBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.accessexambtn.setOnClickListener {
            val examdate=binding.studententerexamdatetoaccessit.text.toString()
            val examid=binding.studententerexamidtoaccessit.text.toString()
            val formater= DateTimeFormatter.ofPattern("HH:mm")
            val examstart= LocalTime.now().format(formater).toString()
            if (examdate.isEmpty()){
                binding.studententerexamdatetoaccessit.error="please enter exam date"
            }
            if (examid.isEmpty()){
                binding.studententerexamidtoaccessit.error="please enter exam id"
            }
            if (examid.isNotEmpty()&&examdate.isNotEmpty()) {
                val loginactivity= activity as loginActivity
                loginactivity.showexamfragment(examdate,examid,examstart)

            }


        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentF().apply {
                arguments = Bundle().apply {

                }
            }
    }
}