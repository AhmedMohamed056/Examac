package com.cis.itiapi.models.remotedata

import com.cis.itiapi.models.AnswerData
import com.cis.itiapi.models.LabelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AnswerApi {
    @POST("/predict")
    suspend fun getAnswerLabel(@Body answerData:AnswerData): Response<LabelResponse>
}