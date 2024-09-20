package com.mahmoud.bugtracker.domain.usecase

import com.mahmoud.bugtracker.data.repository.BugRepository
import retrofit2.Response

class UploadBugDataUseCase(private val repository: BugRepository) {
    suspend operator fun invoke(description: String, imageUri: String): Response<Void> {
        return repository.uploadBugData(description, imageUri)
    }
}