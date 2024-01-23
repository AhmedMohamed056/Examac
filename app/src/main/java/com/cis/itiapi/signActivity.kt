package com.cis.itiapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cis.itiapi.databinding.ActivitySignBinding
import com.google.android.material.tabs.TabLayoutMediator

class signActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private  var titles:Array<Int> = arrayOf(0,1)
    //private val titles:MutableList<String> = mutableListOf(getString(R.string.student),getString(R.string.teacher)) //resources.getStringArray(R.array.titels)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter= MyAdapter(this)

        binding.signpager.adapter=adapter
        TabLayoutMediator(binding.tablayout,binding.signpager){ tab ,position->
            if (titles[position]==0){
                tab.setText(R.string.student)
            }else{
                tab.setText(R.string.teacher)
            }
        }.attach()
    }

    fun getstudentactivity() {
        val intent=Intent(this,loginActivity::class.java)
        startActivity(intent)
    }

    fun getteacheractivity() {
        val intent=Intent(this,TeacherActivity::class.java)
        startActivity(intent)
    }

    fun gologinfragment() {
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra("move to login fragment","")
        startActivity(intent)
    }
}