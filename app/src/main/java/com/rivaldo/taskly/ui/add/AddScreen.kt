package com.rivaldo.taskly.ui.add

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rivaldo.taskly.ui.utils.GetTopAppBarColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavController,
    viewModel: AddViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.operationSuccess) {
        navController.navigateUp()
    }
    val editTaskName = remember { mutableStateOf(uiState.taskNameEdit) }
    val editTaskDesc = remember { mutableStateOf(uiState.taskDescEdit) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Task") },
                colors = GetTopAppBarColor()
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(IntrinsicSize.Min)) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(modifier = Modifier.fillMaxSize().padding(16.dp), onClick = {
                        viewModel.add(editTaskName.value, editTaskDesc.value)
                    }) {
                        Text(text = "Save Task")
                    }
                }
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            val modifierTextField = Modifier
                .fillMaxWidth()
            TextField(
                value = editTaskName.value,
                onValueChange = {
                    editTaskName.value = it },
                label = { Text(text = "Task Name") },
                placeholder = { Text(text = "Enter Task Name") },
                modifier = modifierTextField.padding(8.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = editTaskDesc.value,
                onValueChange = {
                editTaskDesc.value = it },
                label = { Text(text = "Task Description") },
                placeholder = { Text(text = "Enter Task Description") },
                modifier = modifierTextField.padding(8.dp).height(100.dp)
            )
        }
    }

}