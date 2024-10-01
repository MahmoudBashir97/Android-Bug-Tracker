package com.mahmoud.bugtracker.presentation.viewModels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.bugtracker.domain.usecase.UploadBugDataUseCase
import com.mahmoud.bugtracker.domain.usecase.UploadImageUriUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class BugViewModel(
    private val uploadBugDataUseCase: UploadBugDataUseCase,
    private val uploadImageUriUseCase: UploadImageUriUseCase
) : ViewModel() {

    var imageUri = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)
    var description = mutableStateOf("")

    var uploaderResult = mutableStateOf(false)

    fun setImageUri(uri: String?) {
        imageUri.value = uri
    }

    fun setDescription(newDescription: String) {
        description.value = newDescription
    }

    fun uploadBugData(description: String, imageUrl: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true

                val uploadedUrl = uploadImageUrl(Uri.parse(imageUrl)).await()

                if (!uploadedUrl.isNullOrEmpty()) {
                    val response: Response<Void> =
                        uploadBugDataUseCase(description, uploadedUrl.toString())
                    uploaderResult.value = response.isSuccessful
                    if (response.isSuccessful) {
                        Log.d("detectingRequest", " success")
                        resetDataOnView()
                    } else {
                        Log.d("detectingRequest", " failed: ${response.errorBody()?.string()}")
                    }
                } else {
                    Log.d("detectingRequest", " failed: Uploaded URL is empty or null")
                }
            } catch (e: Exception) {
                Log.e("detectingRequest", "Exception occurred: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun uploadImageUrl(imageUri: Uri) = viewModelScope.async {
        return@async try {
            val result = uploadImageUriUseCase.invoke(imageUri, "Done")

            if (result.isSuccess) {
                result.getOrNull()?.link
            } else {
                Log.d(
                    "detectingRequest",
                    "Image upload failed: ${result.exceptionOrNull()?.message}"
                )
                null
            }
        } catch (e: Exception) {
            Log.e("detectingRequest", "Exception in image upload: ${e.message}")
            null
        }
    }


    private fun resetDataOnView() {
        setImageUri(null)
        setDescription("")
    }
}