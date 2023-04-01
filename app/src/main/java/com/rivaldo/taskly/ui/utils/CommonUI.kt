package com.rivaldo.taskly.ui.utils

import android.widget.TextClock
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rivaldo.taskly.data.local.dummy.DataDummyProvider
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import com.rivaldo.taskly.ui.theme.DarkCharcoal
import com.rivaldo.taskly.ui.theme.GreyText
import com.rivaldo.taskly.ui.theme.PapayaWhip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


enum class NavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}


@Composable
fun ExpandedSearchView(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val focusManager = LocalFocusManager.current

    val textFieldFocusRequester = remember { FocusRequester() }

    SideEffect {
        textFieldFocusRequester.requestFocus()
    }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(searchDisplay, TextRange(searchDisplay.length)))
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onExpandedChanged(false)
            onSearchDisplayClosed()
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back icon",
                tint = tint
            )
        }
        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onSearchDisplayChanged(it.text)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = tint
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(textFieldFocusRequester),
            label = {
                Text(text = "Search", color = tint)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetTopAppBarColor(): TopAppBarColors {
    return TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = Color.White,
        actionIconContentColor = Color.White
    )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(task: TaskModel = DataDummyProvider.listTask[0], onClick: (TaskModel) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(unbounded = true)
            .padding(8.dp)
            .clickable { onClick(task) },
        colors = CardDefaults.cardColors(
            containerColor = PapayaWhip,
            contentColor = Color.Black,
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            when (task.status) {
                StatusTask.ACTIVE -> Text(text = task.taskName)
                StatusTask.COMPLETED -> Text(
                    text = task.taskName,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarModalWithCloseButton(title: String, onCloseButtonClicked: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = Color.White,
            titleContentColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(
                onClick = onCloseButtonClicked, enabled = true, modifier = Modifier
                    .size(25.dp)
                    .clip(
                        CircleShape
                    )
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )

            }
        },
        title = {
            Text(text = title)
        }
    )


}


@Composable
fun DialogRadioString(
    currentSelectedIndex: Int,
    listOption: List<String>,
    onConfirm: (String, Int) -> Unit,
    onCancel: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(currentSelectedIndex) }
    Dialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Scaffold(
            modifier = Modifier
                .padding(16.dp)
                .requiredHeightIn(min = 250.dp, max = 350.dp)
                .clip(RoundedCornerShape(8.dp)),
            topBar = {
                TopBarModalWithCloseButton(title = "Select Status Task") {
                    onCancel()
                }
            },
            bottomBar = {
                Button(
                    onClick = {
                        onConfirm(listOption[selectedOption], selectedOption)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Confirm")
                }

            }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                itemsIndexed(listOption) { index, option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = index
                            }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            modifier = Modifier.size(24.dp),
                            selected = selectedOption == index,
                            onClick = {
                                selectedOption = index
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                }
            }

        }
    }

}



