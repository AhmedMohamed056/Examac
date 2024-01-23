package com.cis.itiapi.models

import android.os.Parcelable
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.parcelize.Parcelize

@Parcelize
class teachersignupdata(
    var Name:String="",
    var Email:String="",
    var Phone:String="",
    var Type:String=""
):Parcelable
