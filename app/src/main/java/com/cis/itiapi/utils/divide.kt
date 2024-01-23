package com.cis.itiapi.utils

fun divide(x:String): MutableList<String> {
    val list1=x.split(".")
    val list2:List<String> = list1.subList(0,list1.size-1)
    val list3:MutableList<String> = mutableListOf()
//    println(list2)
    for (i in list2.indices){
        //println( list1[i].split("?"))//.substring(1)
        for (z in list2[i].split("?")){
            list3.add(z)
        }
    }
    return list3

}