package com.cis.itiapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class uploadexamsteacherdata (
    var examid:String="",
    var examdate:String=""
): Parcelable
@Parcelize
class uploadexamsstudentdata (
    var examid:String="",
    var examdate:String="",
    var examstart:String="",
    var examend:String="",
    var examenddate:String=""
): Parcelable


@Parcelize
class examdetaild(
    var examquestions: String ="",
    var exambestanswers: String =""
):Parcelable
@Parcelize
class examdstudentetaild(
    var examquestions: String ="",
    var exambestanswers: String ="",
    var studentanswer:String="",
    var degree:String=""
):Parcelable

