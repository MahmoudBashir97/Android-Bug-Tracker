package com.mahmoud.bugtracker.data.repository

import com.mahmoud.bugtracker.data.remote.GoogleSheetsApi
import retrofit2.Response

class BugRepository(private val api: GoogleSheetsApi) {
    suspend fun uploadBugData(description: String, imageUri: String): Response<Void> {
        return api.appendBugData(description=description, imageUri=imageUri)
    }
}