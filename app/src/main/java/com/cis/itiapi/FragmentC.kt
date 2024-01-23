package com.cis.itiapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cis.itiapi.databinding.FragmentCBinding
import com.cis.itiapi.models.studentsignupdata
import com.cis.itiapi.models.teachersignupdata
import com.cis.itiapi.utils.showtoast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FragmentC : Fragment() {
    private lateinit var binding:FragmentCBinding
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
        binding= FragmentCBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        binding.ifyoudonthaveaccount.setOnClickListener {
            val splashactivity=activity as MainActivity
            splashactivity.gosignactivity()
        }
        binding.loginfragmentbtn.setOnClickListener {
            val loginemail=binding.loginfragmentEmail.text.toString()
            val loginpass=binding.loginfragmentpass.text.toString()
            if (loginemail.isEmpty()){
                binding.loginfragmentEmail.error="email is empty"
            }
            if (loginpass.isEmpty()){
                binding.loginfragmentpass.error="password is empty"
            }
            if (loginemail.isNotEmpty()&&loginpass.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(loginemail, loginpass).addOnSuccessListener {
                    it?.let {
                        if (currentuser != null) {
                            val uid = mAuth.currentUser!!.uid
//                            val uemail = currentuser!!.email!!
//                            val studenttypy=databaseReference.child(uid).child("type").get()
//                            if (studenttypy.result.equals("student")){
//                                val splashactivity = activity as MainActivity
//                                splashactivity.getstudentactivity()
//                                showtoast("hi student")
//                            }
//                            if (studenttypy.result.equals("teacher")){
//                                val splashactivity = activity as MainActivity
//                                splashactivity.getteacheractivity()
//                                showtoast("hi teacher")
//                            }
                            databaseReference.child("users").child(uid).addValueEventListener(object :ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()){
                                            val studenttype= snapshot.getValue(studentsignupdata::class.java)
                                            val teachertype = snapshot.getValue(teachersignupdata::class.java)
                                            if (studenttype?.Type=="student") {
                                                val splashactivity = activity as MainActivity
                                                splashactivity.getstudentactivity()
                                            }
                                            if (teachertype?.Type=="teacher") {
                                                val splashactivity = activity as MainActivity
                                                splashactivity.getteacheractivity()
                                            }
                                    }

                                }

                                override fun onCancelled(error: DatabaseError) {
                                    showtoast(error.message)
                                }
                            })

                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
//            val splashactivity=activity as MainActivity
//            splashactivity.getstudentactivity()
//            val splashactivity=activity as MainActivity
//            splashactivity.getteacheractivity()
//            val studentsignupdata = snapshot.getValue<studentsignupdata>(studentsignupdata::class.java)
//            val teachersignupdata = snapshot.getValue<teachersignupdata>(teachersignupdata::class.java)

        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentC().apply {
                arguments = Bundle().apply {

                }
            }
    }
}