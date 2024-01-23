package com.cis.itiapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cis.itiapi.databinding.ActivityTeacherBinding
import com.cis.itiapi.models.teachersignupdata
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class TeacherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherBinding
    private var pressedtime:Long=0
    private lateinit var navController: NavController
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var currentuser=FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        navController=findNavController(R.id.nav_fragment)
        val navBar=binding.navbar
        navBar.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp().and(hideactionbar()).and(showNavbar()) || super.onSupportNavigateUp()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (pressedtime+2000>System.currentTimeMillis()) {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }else{
            Toast.makeText(this, getString(R.string.backbtnpress), Toast.LENGTH_LONG).show()
        }
        pressedtime=System.currentTimeMillis()
    }

    fun hideNavBar() {
        binding.navbar.visibility=View.INVISIBLE
    }

    fun showNavbar(): Boolean {
        binding.navbar.visibility=View.VISIBLE
        return true
    }
    fun hideactionbar(): Boolean {
        supportActionBar?.hide()
        return true
    }

    fun showactionbar() {
        supportActionBar?.show()
    }


}