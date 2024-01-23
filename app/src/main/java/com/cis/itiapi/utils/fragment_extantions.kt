package com.cis.itiapi.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showtoast(message:String){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}