package com.mahmoud.bugtracker.domain.usecase

import android.net.Uri
import com.mahmoud.bugtracker.data.model.Upload
import com.mahmoud.bugtracker.data.repository.BugRepository
import com.mahmoud.bugtracker.data.repository.UploadRepository
import retrofit2.Response

class UploadBugDataUseCase(private val repository: BugRepository) {
    suspend operator fun invoke(description: String, imageUri: String): Response<Void> {
        return repository.uploadBugData(description, imageUri)
    }
}

class UploadImageUriUseCase(private val repository: UploadRepository) {
    suspend operator fun invoke(imageUri: Uri, title: String? = null): Result<Upload> {
        return repository.uploadFile(imageUri, title)
    }
}