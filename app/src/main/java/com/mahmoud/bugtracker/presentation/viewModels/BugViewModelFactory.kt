package com.mahmoud.bugtracker.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.bugtracker.domain.usecase.UploadBugDataUseCase

class BugViewModelFactory(private val uploadBugDataUseCase: UploadBugDataUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BugViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BugViewModel(uploadBugDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}