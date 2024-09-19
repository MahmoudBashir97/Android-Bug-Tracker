package com.mahmoud.bugtracker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahmoud.bugtracker.ui.theme.BugTrackerTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val IMAGE_PICK_CODE = 1000
    }

    private var imageUri by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            BugTrackerTheme {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("submit") {
                        SubmitBugScreen(
                            onImageSelected = { uri -> imageUri = uri },
                            imageUri = imageUri,
                            onSubmit = { description ->
                               // uploadBugData(description, imageUri)
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate("submit") }) {
                Text("Report a Bug")
            }
        }
    }

    @Composable
    fun SubmitBugScreen(
        onImageSelected: (Uri) -> Unit,
        imageUri: Uri?,
        onSubmit: (String) -> Unit
    ) {
        var description by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { selectImage() }) {
                Text("Select Screenshot")
            }
            imageUri?.let {
                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Selected Image")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onSubmit(description) }) {
                Text("Submit Bug")
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let { imageUri -> /* Handle the selected image URI */ }
        }
    }
}
