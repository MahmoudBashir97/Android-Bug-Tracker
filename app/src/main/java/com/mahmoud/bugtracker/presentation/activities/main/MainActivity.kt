package com.mahmoud.bugtracker.presentation.activities.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.mahmoud.bugtracker.data.remote.ApiService
import com.mahmoud.bugtracker.data.repository.BugRepository
import com.mahmoud.bugtracker.data.repository.UploadRepositoryImp
import com.mahmoud.bugtracker.domain.usecase.UploadBugDataUseCase
import com.mahmoud.bugtracker.domain.usecase.UploadImageUriUseCase
import com.mahmoud.bugtracker.presentation.viewModels.BugViewModel
import com.mahmoud.bugtracker.presentation.viewModels.BugViewModelFactory
import com.mahmoud.bugtracker.ui.theme.BugTrackerTheme
import com.mahmoud.bugtracker.utils.ScreensRoute

class MainActivity : ComponentActivity() {
    companion object {
        const val IMAGE_PICK_CODE = 1000
    }

    private lateinit var viewModel: BugViewModel

    private fun initializeViewModel() {
        val googleSheetRepo = BugRepository(ApiService.createBug())
        val imageUrl = UploadRepositoryImp(ApiService.createImageUrl(), contentResolver)
        val uploadBugUseCase = UploadBugDataUseCase(googleSheetRepo)
        val uploadImageUriUseCase = UploadImageUriUseCase(imageUrl)
        viewModel = ViewModelProvider(
            this,
            BugViewModelFactory(uploadBugUseCase, uploadImageUriUseCase)
        )[BugViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            BugTrackerTheme {
                MainScreenContent(navController, viewModel) {
                    selectImage()
                }
            }
            handleIncomingIntent {
                navController.navigate(ScreensRoute.SUBMIT.name)
            }
        }
    }


    private fun selectImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let { imageUri ->
                viewModel.setImageUri(imageUri.toString())
            }
        }
    }

    private fun handleIncomingIntent(onUriReceived: (() -> Unit)? = null) {
        if (intent?.action == Intent.ACTION_SEND && intent.type?.startsWith("image/") == true) {
            val receivedUri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
            } else {
                intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            }
            receivedUri?.let {
                viewModel.setImageUri(it.toString())
                onUriReceived?.invoke()
            }
        }
    }
}