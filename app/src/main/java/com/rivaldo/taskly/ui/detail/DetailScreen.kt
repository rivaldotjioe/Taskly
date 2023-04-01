package com.rivaldo.taskly.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rivaldo.taskly.ui.theme.AeroBlue
import com.rivaldo.taskly.ui.theme.Melon
import com.rivaldo.taskly.ui.utils.GetTopAppBarColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    idTask: String,
    viewModel: DetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true, block = {
        viewModel.initialize(idTask = idTask)
    })
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Task") },
                colors = GetTopAppBarColor()
            )
        },
        bottomBar = {
            BottomAppBar() {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        uiState.task.let {
                            if (it != null) {
                                viewModel.markComplete(idTask = it.id)
                            }
                        }

                    }) {
                        Text(text = "Mark As Completed")
                    }
                    Button(onClick = {
                        uiState.task.let {
                            if (it != null) {
                                viewModel.delete(idTask = it.id)
                            }
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text(text = "Delete Task")
                    }
                }

            }
        }
    ) { paddingValues ->
        if (uiState.operationSuccess) {
            navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Task Name", fontSize = 12.sp)
            Text(text = uiState.task?.taskName ?: "", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Task Description", fontSize = 12.sp)
            Text(text = uiState.task?.description ?: "", fontSize = 16.sp)
        }
    }
}