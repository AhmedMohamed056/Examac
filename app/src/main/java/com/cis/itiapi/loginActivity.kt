package com.cis.itiapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.cis.itiapi.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class loginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        databaseReference= FirebaseDatabase.getInstance().reference
        mAuth=FirebaseAuth.getInstance()
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupviewpager()
        binding.tablayoutstudent.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.studientpager.currentItem=tab!!.position
                if (tab.position==0){
                binding.tablayoutstudent.setSelectedTabIndicator(R.drawable.tabtowde)}
                else{
                    binding.tablayoutstudent.setSelectedTabIndicator(R.drawable.tabde)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }


        })
        binding.studientpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tablayoutstudent.selectTab(binding.tablayoutstudent.getTabAt(position))
            }
        })
    }


//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
//    }




    private fun setupviewpager() {
        val adapter:StudentAdapterPager=StudentAdapterPager(supportFragmentManager,lifecycle )
        binding.tablayoutstudent.addTab(binding.tablayoutstudent.newTab().setText(R.string.Home).setIcon(R.drawable.ic_baseline_home_24))
        binding.tablayoutstudent.addTab(binding.tablayoutstudent.newTab().setText(R.string.Exam).setIcon(R.drawable.ic_baseline_find_in_page_24))
        binding.studientpager.adapter=adapter

    }
    // Move from fragment
    fun showexamfragment(examdate: String, examid: String, examstart: String) {
        binding.studientpager.visibility= View.INVISIBLE
        binding.tablayoutstudent.visibility=View.INVISIBLE
        val bundel=Bundle()
        bundel.putString("examdatetoshow",examdate)
        bundel.putString("examidtoshow",examid)
        bundel.putString("examstart",examstart)
        val fragmentI=FragmentI()
        fragmentI.arguments=bundel
        supportFragmentManager.beginTransaction().replace(R.id.loginActivity,fragmentI).commit()


    }

    fun getsettingsfragment() {
        binding.studientpager.visibility= View.INVISIBLE
        binding.tablayoutstudent.visibility=View.INVISIBLE
        val intent=Intent(this,SettingsActivityStudent::class.java)
        startActivity(intent)

    }

    fun backtofindexamfragment( fragmentshowexam: FrameLayout) {
        binding.studientpager.visibility=View.VISIBLE
        binding.tablayoutstudent.visibility=View.VISIBLE
        //Toolbar.visibility=View.INVISIBLE
        fragmentshowexam.visibility=View.INVISIBLE
        //supportFragmentManager.beginTransaction().replace(R.id.loginActivity,FragmentF()).commit()
    }

    fun backtohomefragment(showstudentexamfragment: FrameLayout) {
        binding.studientpager.visibility=View.VISIBLE
        binding.tablayoutstudent.visibility=View.VISIBLE
        showstudentexamfragment.visibility=View.INVISIBLE

    }


}
