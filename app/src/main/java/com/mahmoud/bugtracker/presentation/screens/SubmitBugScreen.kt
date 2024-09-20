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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mahmoud.bugtracker.presentation.viewModels.BugViewModel

@Composable
fun SubmitBugScreen(
    viewModel: BugViewModel,
    onSelectingImage: (() -> Unit)? = null,
    imageUri: Uri?,
    onSubmit: ((String) -> Unit)? = null
) {
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        TextField(
            value = viewModel.description.value,
            onValueChange = { viewModel.setDescription(it) },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)      
        )
        Spacer(modifier = Modifier.height(16.dp))
        viewModel.imageUri.value?.let {
            Image(
                modifier = Modifier.size(80.dp),
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Selected Image"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), 
            horizontalArrangement = Arrangement.SpaceBetween 
        ) {
            Button(onClick = { onSelectingImage?.invoke() }) {
                Text("Select Screenshot")
            }
            
            if (isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = { onSubmit?.invoke(viewModel.description.value) }, enabled = !isLoading) {
                Text("Submit Bug")
            }
        }
    }
}