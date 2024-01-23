package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cis.itiapi.databinding.FragmentBBinding
import com.cis.itiapi.models.studentsignupdata
import com.cis.itiapi.models.teachersignupdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FragmentB : Fragment() {
    private lateinit var binding:FragmentBBinding
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
        binding=FragmentBBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.teacherfragmentsignupBtn.setOnClickListener {
            saveteachsignupdatainfirbase()

        }
        binding.teacheryouhaveacount.setOnClickListener {
            val signupactivity=activity as signActivity
            signupactivity.gologinfragment()
        }
    }

    private fun saveteachsignupdatainfirbase() {
        val teachname=binding.teacherfragmentsignupName.text.toString()
        val teachemail=binding.teacherfragmentsignupEmail.text.toString()
        val teachphone=binding.teacherfragmentsignupPhone.text.toString()
        val teachpass=binding.teacherfragmentsignupPass.text.toString()
        val teachconpass=binding.teacherfragmentsignupConfirmPass.text.toString()
        val type="teacher"
        //val uid=databaseReference.push().key!!
        if (teachname.isEmpty()){
            binding.teacherfragmentsignupName.error="please enter name"
        }
        if (teachemail.isEmpty()){
            binding.teacherfragmentsignupEmail.error="please enter email"
        }
        if (teachphone.isEmpty()){
            binding.teacherfragmentsignupPhone.error="please enter phone"
        }
        if (teachpass.isEmpty()){
            binding.teacherfragmentsignupPass.error="please enter password"
        }
        if (teachconpass != teachpass){
            binding.teacherfragmentsignupConfirmPass.error="password should be identical"
        }
        if (teachname.isNotEmpty()&&teachemail.isNotEmpty()&&teachphone.isNotEmpty()&&teachpass.isNotEmpty()&&teachpass==teachconpass){
            mAuth.createUserWithEmailAndPassword(teachemail,teachpass).addOnSuccessListener {
                val signupactivity = activity as signActivity
                signupactivity.getteacheractivity()
                val uid=mAuth.currentUser!!.uid
                val modeldataclasssignup = teachersignupdata(teachname, teachemail, teachphone, type)
                    databaseReference.child("users").child(uid)
                        .setValue(modeldataclasssignup)
                    showtoast("signup successfully")
            }.addOnFailureListener {
                showtoast(it.message!!)
            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentB().apply {
                arguments = Bundle().apply {

                }
            }
    }
}