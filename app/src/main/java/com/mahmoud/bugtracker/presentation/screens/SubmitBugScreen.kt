package com.mahmoud.bugtracker.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mahmoud.bugtracker.presentation.viewModels.BugViewModel

@Composable
fun SubmitBugScreen(
    viewModel: BugViewModel,
    onSelectingImage: (() -> Unit)? = null,
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    width = 2.dp,
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.Black.copy(alpha = 0.1f))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.imageUri.value != null) {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = rememberAsyncImagePainter(model = viewModel.imageUri.value),
                    contentDescription = "Selected Image"
                )
            } else {
                Text(
                    text = "No Image Selected",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
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
