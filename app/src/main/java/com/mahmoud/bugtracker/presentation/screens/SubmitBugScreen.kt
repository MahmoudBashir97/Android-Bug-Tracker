package com.mahmoud.bugtracker.presentation.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mahmoud.bugtracker.R
import com.mahmoud.bugtracker.presentation.viewModels.BugViewModel

@Composable
fun SubmitBugScreen(
    viewModel: BugViewModel,
    onSelectingImage: (() -> Unit)? = null,
    imageUri: Uri?,
    onSubmit: ((String) -> Unit)? = null
) {
    var description by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()        // Fills the width of the screen
                .height(90.dp)         // Sets the height greater than 50dp (for example, 56dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        viewModel.imageUri.value?.let {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Selected Image"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()          // Fills the width of the screen
                .padding(vertical = 16.dp), // Optional padding
            horizontalArrangement = Arrangement.SpaceBetween // Space between the buttons
        ) {
            Button(onClick = { onSelectingImage?.invoke() }) {
                Text("Select Screenshot")
            }

            // Show loader when the upload is in progress
            if (isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = { onSubmit?.invoke(description) }, enabled = !isLoading) {
                Text("Submit Bug")
            }
        }
    }
}