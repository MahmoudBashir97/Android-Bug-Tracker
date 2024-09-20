package com.mahmoud.bugtracker.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleSheetsApi {

    @GET("macros/s/AKfycbzgwJs2u4gAH_KiWQ0500JyGc1lKVw7WT-wZyBU-ot9p6ap2vilPzpKDOudtf5fsFlS3A/exec")
    suspend fun appendBugData(
        @Query("action") action: String = "create",
        @Query("description") description: String,
        @Query("imageUri") imageUri: String
    ): Response<Void>
}