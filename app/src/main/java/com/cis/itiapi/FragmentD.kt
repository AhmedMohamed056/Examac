package com.cis.itiapi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.itiapi.databinding.FragmentDBinding
import com.cis.itiapi.models.Adapters.Adapterstudentoldexam
import com.cis.itiapi.models.studentsignupdata
import com.cis.itiapi.models.uploadexamsstudentdata
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.showtoast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


class FragmentD : Fragment() {
    private lateinit var binding:FragmentDBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage:StorageReference
    private var currentuser= FirebaseAuth.getInstance().currentUser
    private var listofoldexaminhome:ArrayList<uploadexamsstudentdata> = arrayListOf()
    private var listofoldexam:List<uploadexamsteacherdata> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance().reference.child("images/image.png")
        setstudataprofile()
        accessoldexamdata()
        setuprecyclerview()
        //binding.progressbarstudenthome.visibility=View.VISIBLE
        binding.studentProfileImage.setOnClickListener {
            ImagePicker.with(this)
                .crop() //Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
        binding.studentsettings.setOnClickListener {
                    val loginactivity=activity as loginActivity
                    loginactivity.getsettingsfragment()

        }
    }

    private fun setuprecyclerview() {
        val adapter=Adapterstudentoldexam(requireContext(),listofoldexaminhome)
       binding.recyclerviewstudenthome.layoutManager=LinearLayoutManager(context)
        binding.recyclerviewstudenthome.adapter=adapter
    }

    private fun accessoldexamdata() {
        if (currentuser!=null) {
            val uid = currentuser!!.uid
            databaseReference.child("exams").child(uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        listofoldexaminhome.clear()
                        if (snapshot.exists()) {
                            for (datasnap in snapshot.children) {
                                val uploadexamsstudentdata =
                                    datasnap.getValue<uploadexamsstudentdata>(uploadexamsstudentdata::class.java)
                                listofoldexaminhome.add(uploadexamsstudentdata!!)
                                //binding.progressbarstudenthome.visibility=View.GONE
                                setuprecyclerview()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        showtoast(error.message)
                    }
                })
        }
        }

    private fun setimageinimageview() {
        val ONE_MEGABYTE: Long = 1024 * 1024
        storage.child(currentuser!!.uid).getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding.studentProfileImage.setImageBitmap(bitmap)
        }.addOnFailureListener {
            showtoast(it.message!!)
        }
    }

    private fun setstudataprofile() {
        if (currentuser!=null) {
            val uid = currentuser!!.uid
            databaseReference.child("users").child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val studentsignupdata = snapshot.getValue<studentsignupdata>(studentsignupdata::class.java)
                    binding.studentnameid.text=studentsignupdata?.Name
                    binding.studentemailid.text=studentsignupdata?.Email

                }

                override fun onCancelled(error: DatabaseError) {
                    showtoast(error.message)
                }
            })
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Image Uri will not be null for RESULT_OK
        if (resultCode== FragmentActivity.RESULT_OK){
            val uri: Uri = data?.data!!
            //upload image to cloud storage database
            val uploadimageprofile=storage.child(currentuser!!.uid).putFile(uri)
            uploadimageprofile.addOnSuccessListener {
                showtoast("image upload successfully")
            }.addOnFailureListener {
                showtoast("image not upload")
            }
            // Use Uri object instead of File to avoid storage permissions
            binding.studentProfileImage.setImageURI(uri)
            setimageinimageview()
            }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentD().apply {
                arguments = Bundle().apply {

                }
            }
    }
}