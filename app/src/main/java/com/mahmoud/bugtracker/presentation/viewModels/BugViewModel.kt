package com.mahmoud.bugtracker.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.bugtracker.domain.usecase.UploadBugDataUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class BugViewModel(private val uploadBugDataUseCase: UploadBugDataUseCase) : ViewModel() {

    var imageUri = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)
    var description = mutableStateOf("")

    fun setImageUri(uri: String?) {
        imageUri.value = uri
    }

    fun setDescription(newDescription: String) {
        description.value = newDescription
    }

    fun uploadBugData(description: String, imageUrl: String) {
        viewModelScope.launch {
            isLoading.value = true
            val response: Response<Void> = uploadBugDataUseCase(description, imageUrl)
            if (response.isSuccessful) {
                Log.d("detectingRequest", " success")
                resetDataOnView()
            } else {
                Log.d("detectingRequest", " failed")
            }
            isLoading.value = false // Set loading to false after request
        }
    }

    private fun resetDataOnView() {
        setImageUri("")
        setDescription("")
    }
}