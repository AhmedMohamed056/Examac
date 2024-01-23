package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cis.itiapi.databinding.FragmentABinding
import com.cis.itiapi.models.studentsignupdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*



class FragmentA : Fragment() {
    private lateinit var binding:FragmentABinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    //private var currentuser=FirebaseAuth.getInstance().currentUser
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
        binding=FragmentABinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //for clean code
        initViews()
        databaseReference=FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.studentfragmentsignupBtn.setOnClickListener {
            savestusignupdatainfirbase()
        }
    }

    private fun savestusignupdatainfirbase() {
        val studname=binding.studentfragmentsignupName.text.toString()
        val stuemail=binding.studentfragmentsignupEmail.text.toString()
        val stupass=binding.studentfragmentsignupPass.text.toString()
        val stuconpass=binding.studentfragmentsignupConfirmPass.text.toString()
        val type="student"
        //val uid=databaseReference.push().key!!
        if (studname.isEmpty()){
            binding.studentfragmentsignupName.error="please enter name"
        }
        if (stuemail.isEmpty()){
            binding.studentfragmentsignupEmail.error="please enter email"
        }
        if (stupass.isEmpty()){
            binding.studentfragmentsignupPass.error="please enter password"
        }
        if (stuconpass != stupass){
            binding.studentfragmentsignupConfirmPass.error="password should be identical"
        }
       if (studname.isNotEmpty()&&stuemail.isNotEmpty()&&stupass.isNotEmpty()&&stupass==stuconpass){
           mAuth.createUserWithEmailAndPassword(stuemail,stupass).addOnSuccessListener {
               val signupactivity = activity as signActivity
               signupactivity.getstudentactivity()
               val uid=mAuth.currentUser!!.uid
               val modeldataclasssignup = studentsignupdata( studname, stuemail, type)
               databaseReference.child("users").child(uid)
                       .setValue(modeldataclasssignup)
                   showtoast("signup successfully")

           }.addOnFailureListener {
               showtoast(it.message!!)
           }

        }

    }

    private fun initViews() {
        binding.studentyouhaveacount.setOnClickListener {
            val signupactivity=activity as signActivity
            signupactivity.gologinfragment()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentA().apply {
                arguments = Bundle().apply {

                }
            }
    }
}