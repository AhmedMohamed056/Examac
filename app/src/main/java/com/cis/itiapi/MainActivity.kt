package com.cis.itiapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cis.itiapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        if(intent.hasExtra("move to login fragment")){
            supportFragmentManager.beginTransaction().replace(R.id.splashactivity,FragmentC()).commit()
        }


        binding.btnsign.setOnClickListener {
            val intent=Intent(this,signActivity::class.java)
            startActivity(intent)
        }
        binding.btnlog.setOnClickListener {
            binding.btnsign.visibility=View.INVISIBLE
            binding.btnlog.visibility=View.INVISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.splashactivity,FragmentC()).commit()

        }

    }

    fun getstudentactivity() {
        val intent=Intent(this,loginActivity::class.java)
        startActivity(intent)
    }

    fun gosignactivity() {
        val intent=Intent(this,signActivity::class.java)
        startActivity(intent)
    }

    fun getteacheractivity() {
        val intent=Intent(this,TeacherActivity::class.java)
        startActivity(intent)
    }


}