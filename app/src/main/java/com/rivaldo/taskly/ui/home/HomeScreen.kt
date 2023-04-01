package com.rivaldo.taskly.ui.home

import android.content.pm.CrossProfileApps
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rivaldo.taskly.data.local.dummy.DataDummyProvider
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.listIteratedStatus
import com.rivaldo.taskly.domain.listRadioStatus
import com.rivaldo.taskly.domain.model.TaskModel
import com.rivaldo.taskly.ui.theme.TitleTopBar
import com.rivaldo.taskly.ui.utils.*
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showDialogFilter = remember { mutableStateOf(false) }
    val currentTypeFilter = remember { mutableStateOf(0) }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.initialize()
    })
    Scaffold(
        topBar = {
            TopBarHomeScreen(
                title = "Taskly",
                onAddClick = {
                    navController.navigate(DestinationScreen.ADD_TASK.getRoute())
                },
                onTextSearchChanged = { query -> viewModel.search(keyword = query) },
                onSearchClosed = { viewModel.initialize() },
                onFilterClick = { showDialogFilter.value = true }
            )
        },
        contentColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            listIteratedStatus.forEach { status ->
                val list =
                    if (status == StatusTask.ACTIVE) uiState.listActiveTask else uiState.listCompletedTask
                ListSection(list = list, textSection = status.getText()) {
                    navController.navigate(
                        DestinationScreen.DETAIL.getRoute()
                            .replace("{" + DestinationScreen.ID_TASK_KEY + "}", it.id)
                    )
                }

            }
//            item {
//                Text(text = "Active Task", style = TitleTopBar, color = Color.Black, modifier = Modifier.padding(8.dp))
//            }
//            items(uiState.listActiveTask) { task ->
//                TaskCard(task)
//            }
//            item {
//                Text(text = "Completed Task", style = TitleTopBar, color = Color.Black, , modifier = Modifier.padding(8.dp))
//            }
//            items(uiState.listCompletedTask) { task ->
//                TaskCard(task)
//            }

        }
    }

    if (showDialogFilter.value) {
        DialogRadioString(
            currentSelectedIndex = currentTypeFilter.value,
            listOption = listRadioStatus,
            onConfirm = { status, index ->
                currentTypeFilter.value = index
                val status = StatusTask.fromString(status)
                if (status != null) {
                    viewModel.filterByStatus(status)
                } else {
                    viewModel.initialize()
                }
                showDialogFilter.value = false

            },
            onCancel = {
                showDialogFilter.value = false
            })
    }


}

fun LazyListScope.ListSection(
    list: List<TaskModel>,
    textSection: String,
    onItemClicked: (TaskModel) -> Unit = {}
) {
    item {
        Text(
            text = textSection + " (${list.size})",
            style = TitleTopBar,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    }
    items(list) { task ->
        TaskCard(task, onClick = onItemClicked)
    }
}

@Composable
fun TopBarHomeScreen(
    title: String,
    onAddClick: () -> Unit,
    onTextSearchChanged: (String) -> Unit,
    onSearchClosed: () -> Unit,
    onFilterClick: () -> Unit
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    Crossfade(targetState = expanded) { isExpandedSearch ->
        if (isExpandedSearch) {
            ExpandedSearchView(
                searchDisplay = "",
                onSearchDisplayChanged = onTextSearchChanged,
                onSearchDisplayClosed = onSearchClosed,
                onExpandedChanged = setExpanded
            )
        } else {
            TopBarHomeCollapse(
                title = title,
                onAddClick = onAddClick,
                onSearchClick = { setExpanded(true) },
                onFilterClick = onFilterClick
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHomeCollapse(
    title: String,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TitleTopBar,
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
            IconButton(onClick = onFilterClick) {
                Icon(imageVector = Icons.Default.FilterAlt, contentDescription = null)
            }
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)

            }
        },
        colors = GetTopAppBarColor()
    )
}
