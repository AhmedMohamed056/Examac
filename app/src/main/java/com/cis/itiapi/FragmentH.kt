package com.cis.itiapi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.itiapi.databinding.FragmentHBinding
import com.cis.itiapi.models.Adapters.teacheradaptershowoldexam
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.teachersignupdata
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.showtoast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class FragmentH : Fragment() {
    private lateinit var binding:FragmentHBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage: StorageReference
    private var currentuser= FirebaseAuth.getInstance().currentUser
    //private lateinit var adapter:teacheradaptershowoldexam
    private var listofoldexaminhome:ArrayList<uploadexamsteacherdata> = arrayListOf()
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
        binding=FragmentHBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance().reference.child("images/image.png")
        setteachdataprofile()
        accessoldexamdata()
        setuprecyclerview()
        //binding.progressbarteacherhome.visibility=View.VISIBLE
        val teacherActivity=activity as TeacherActivity
        binding.teacherProfileImage.setOnClickListener {
            ImagePicker.with(this)
                .crop() //Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

        }
//        binding.recyclerviewteacherhome.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_showoldexamteacherFragment)
//            teacherActivity.hideNavBar()
//
//        }
        binding.teachersettings.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_settingsFragmentteacher)
            teacherActivity.hideNavBar()
            teacherActivity.showactionbar()
        }

    }



//    private fun setimageinimageview() {
//        val ONE_MEGABYTE: Long = 1024 * 1024
//        storage.child(currentuser!!.uid).getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
//            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//            binding.teacherProfileImage.setImageBitmap(bitmap)
//        }.addOnFailureListener {
//            showtoast(it.message!!)
//        }
//    }

    private fun setteachdataprofile() {
        if (currentuser!=null) {
            val uid = currentuser!!.uid
            databaseReference.child("users").child(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val teachersignupdata = snapshot.getValue<teachersignupdata>(teachersignupdata::class.java)
                    binding.teachernameid.text = teachersignupdata?.Name
                    binding.teacheremailid.text=teachersignupdata?.Email
                    binding.teacherphoneid.text=teachersignupdata?.Phone
                }

                override fun onCancelled(error: DatabaseError) {
                    showtoast(error.message)
                }
            })
        }
    }
    private fun accessoldexamdata() {
        if (currentuser!=null) {
            val uid = currentuser!!.uid
            databaseReference.child("exams").child(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listofoldexaminhome.clear()
                    if (snapshot.exists()) {
                        for (datasnap in snapshot.children){
                        val uploadexamsteacherdata = datasnap.getValue<uploadexamsteacherdata>(uploadexamsteacherdata::class.java)
                        listofoldexaminhome.add(uploadexamsteacherdata!!)
                        //binding.progressbarteacherhome.visibility=View.GONE
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
    private fun setuprecyclerview() {
        val adapter=teacheradaptershowoldexam(requireContext(),listofoldexaminhome)
        binding.recyclerviewteacherhome.layoutManager= LinearLayoutManager(context)
        binding.recyclerviewteacherhome.adapter=adapter
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Image Uri will not be null for RESULT_OK
        if (resultCode== FragmentActivity.RESULT_OK){
            val uri: Uri = data?.data!!//upload image to cloud storage database
            val uploadimageprofile=storage.child(currentuser!!.uid).putFile(uri)
            uploadimageprofile.addOnSuccessListener {
                storage.downloadUrl.addOnSuccessListener {
                    Picasso.get().load(uri).into(binding.teacherProfileImage)
                }.addOnFailureListener {
                    showtoast(it.message!!)
                }
                showtoast("image upload successfully")
            }.addOnFailureListener {
                showtoast("image not upload")
            }
            // Use Uri object instead of File to avoid storage permissions
            binding.teacherProfileImage.setImageURI(uri)}
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentH().apply {
                arguments = Bundle().apply {

                }
            }
    }
}