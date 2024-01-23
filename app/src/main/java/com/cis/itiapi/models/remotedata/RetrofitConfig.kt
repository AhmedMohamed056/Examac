package com.cis.itiapi.models.remotedata

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitConfig {
    private fun getRetrofitInstance():Retrofit{
        return Retrofit.Builder().baseUrl("https://fastapi-khaled5321.vercel.app")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    fun getServiceInstance():AnswerApi{
        return getRetrofitInstance().create<AnswerApi>()
    }
}