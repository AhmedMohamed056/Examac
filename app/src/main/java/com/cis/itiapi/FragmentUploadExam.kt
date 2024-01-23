package com.cis.itiapi

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.cis.itiapi.databinding.FragmentUploadExamBinding
import com.cis.itiapi.models.examdetaild
import com.cis.itiapi.models.uploadexamsteacherdata
import com.cis.itiapi.utils.divide
import com.cis.itiapi.utils.showtoast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException
import java.time.LocalDate


class FragmentUploadExam : Fragment() {
    private lateinit var binding:FragmentUploadExamBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var currentuser= FirebaseAuth.getInstance().currentUser
    private var listofquestions:MutableList<String> = mutableListOf()
    private var listofanswers:MutableList<String> = mutableListOf()
//    private var listofquestions:List<String> = listOf()
//    private var listofanswers:List<String> = listOf()
    private var listofquestionadded:MutableList<examdetaild> = mutableListOf()
    //for text recognizer
    private lateinit var uri: Uri
    private lateinit var inputImage: InputImage
    private lateinit var textRecognizer: TextRecognizer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUploadExamBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teacherActivity=activity as TeacherActivity
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
//        teacherActivity.setSupportActionBar(binding.uploadExamToolbar)
        binding.uploadExamToolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragmentUploadExam_to_navigation_upload)
            teacherActivity.showNavbar()
        }
        binding.takeexamimgbtn.setOnClickListener {
            ImagePicker.with(this)
                .crop() //Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
        binding.saveexambtn.setOnClickListener {
            val examinonepiece=binding.showtxt.text.toString()
            if (examinonepiece.isEmpty()){
                binding.showtxt.error="please enter answer"
            }
            if (examinonepiece.isNotEmpty()) {
                val builder=AlertDialog.Builder(requireContext())
                builder.setTitle("Warning").setIcon(R.drawable.baseline_warning_24)
                .setMessage("you make sure that question ends with ? and answer ends with .")
                    .setPositiveButton("Ok"
                    ) { dialog, which ->
                        val QA = divide(examinonepiece)
                        for (i in QA.indices)
                            if (i % 2 == 0) {
                                listofquestions.add(QA[i])
                            } else {
                                listofanswers.add(QA[i])
                            }
                        for (i in listofquestions.indices) {
                            listofquestionadded.add(
                                examdetaild(
                                    listofquestions[i],
                                    listofanswers[i]
                                )
                            )
                        }
                        //val examdate = LocalDate.now().toString()
                        databaseReference.child("exams").child(currentuser!!.uid)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val examdate = arguments?.getString("examdate")
                                    val examid = arguments?.getString("examid")
                                    //val modeldata= uploadexamsteacherdata(examdate!!,examid!!)
                                    databaseReference.child("exams")
                                        .child("exam").child(examid!!).child(examdate!!)
                                        .child(currentuser!!.uid)
                                        .setValue(listofquestionadded)
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    showtoast(error.message)
                                }
                            })
                        Navigation.findNavController(it)
                            .navigate(R.id.action_fragmentUploadExam_to_navigation_upload)
                        teacherActivity.showNavbar()
                    }.setNegativeButton("Cansel"
                    ) { dialog, which -> showtoast("your exam don't save") }.show()

            }
        }
    }
    private fun recognizetext() {
        try {
            inputImage = InputImage.fromFilePath(requireContext(), uri)
            textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val result = textRecognizer.process(inputImage)
                .addOnSuccessListener { visionText ->
                    // Task completed successfully
                    binding.showtxt.setText(visionText.text)
                    // move textview to edit text to make it editable
                    //binding.editshowtext.text= binding.showtxt.text as Editable?
                    // Toast.makeText(context,visionText.text,Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== FragmentActivity.RESULT_OK) {
            uri = data?.data!!
            recognizetext()
            // Use Uri object instead of File to avoid storage permissions
            // binding.imgexam.setImageURI(uri)}
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
            FragmentUploadExam().apply {
                arguments = Bundle().apply {

                }
            }
    }
}