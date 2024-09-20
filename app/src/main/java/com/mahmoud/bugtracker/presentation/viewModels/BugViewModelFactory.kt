package com.mahmoud.bugtracker.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.bugtracker.domain.usecase.UploadBugDataUseCase
import com.mahmoud.bugtracker.domain.usecase.UploadImageUriUseCase

class BugViewModelFactory(private val uploadBugDataUseCase: UploadBugDataUseCase,private val uploadImageUriUseCase: UploadImageUriUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BugViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BugViewModel(uploadBugDataUseCase,uploadImageUriUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}