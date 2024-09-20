package com.mahmoud.bugtracker.data.remote

import com.mahmoud.bugtracker.data.model.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApi {
    @Multipart
    @POST("/3/upload")
    suspend fun uploadFile(
        @Header("Authorization") auth: String = "Client-ID ${ApiKeys.CLIENT_ID}",
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody? = null
    ): Response<UploadResponse>
}

object ApiKeys{
    const val CLIENT_ID = "a234807f9a4ba28"
}