package com.mahmoud.bugtracker.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.bugtracker.domain.usecase.UploadBugDataUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class BugViewModel(private val uploadBugDataUseCase: UploadBugDataUseCase) : ViewModel() {

    var imageUri = mutableStateOf<String?>(null) // MutableState for imageUri
    var isLoading = mutableStateOf(false) // MutableState for loading status

    fun setImageUri(uri: String?) {
        imageUri.value = uri // Update the imageUri
    }

    fun uploadBugData(description: String, imageUrl: String) {
        viewModelScope.launch {
            isLoading.value = true // Set loading to true
            val response: Response<Void> = uploadBugDataUseCase(description, imageUrl)
            if (response.isSuccessful) {
                Log.d("detectingRequest", " success")
            } else {
                Log.d("detectingRequest", " failed")
            }
            isLoading.value = false // Set loading to false after request
        }
    }
}