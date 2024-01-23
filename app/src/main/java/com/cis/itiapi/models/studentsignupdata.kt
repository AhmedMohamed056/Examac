package com.cis.itiapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class studentsignupdata(
    var Name:String="",
    var Email:String="",
    var Type:String=""
):Parcelable
