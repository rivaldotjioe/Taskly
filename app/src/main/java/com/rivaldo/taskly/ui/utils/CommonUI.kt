package com.rivaldo.taskly.ui.utils

import android.widget.TextClock
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
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

@Composable
fun NavigationRailDrawer(
    selectedDestination: DestinationScreen,
    onNavigateTo: (DestinationScreen) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    val navigationRailItemColors = NavigationRailItemDefaults.colors(
        indicatorColor = DarkCharcoal,
        selectedTextColor = Color.White,
        unselectedTextColor = DarkCharcoal,
        unselectedIconColor = DarkCharcoal,
        selectedIconColor = Color.White,
        disabledIconColor = Color.Gray,
        disabledTextColor = Color.Gray,
    )
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = Color.White
    ) {
        NavigationRailItem(selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        )
        listNavigationItem.forEach { navigationItem ->
            NavigationRailItem(
                selected = selectedDestination == navigationItem.destination,
                onClick = { onNavigateTo(navigationItem.destination) },
                icon = {
                    Icon(
                        imageVector = if (selectedDestination == navigationItem.destination) navigationItem.iconSelected else navigationItem.icon,
                        contentDescription = null
                    )
                },
                colors = navigationRailItemColors
            )
        }

    }

}


@Composable
fun BottomNavigationBar(
    selectedDestination: DestinationScreen,
    onNavigateTo: (DestinationScreen) -> Unit
) {
    val bottomNavBarColor = NavigationBarItemDefaults.colors(
        indicatorColor = DarkCharcoal,
        selectedTextColor = Color.White,
        unselectedTextColor = DarkCharcoal,
        unselectedIconColor = DarkCharcoal,
        selectedIconColor = Color.White,
        disabledIconColor = Color.Gray,
        disabledTextColor = Color.Gray,
    )
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        listNavigationItem.forEach { navigationItem ->
            val selected = selectedDestination == navigationItem.destination
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateTo(navigationItem.destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) navigationItem.iconSelected else navigationItem.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = navigationItem.title,
                        color = DarkCharcoal,
                        fontSize = 10.sp
                    )
                },
                colors = bottomNavBarColor
            )
        }

    }
}


@Composable
fun NavigationDrawerContent(
    selectedDestination: DestinationScreen,
    modifier: Modifier = Modifier,
    onNavigateTo: (DestinationScreen) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    val isVisibleCloseDrawer = false
    Column(
        modifier
            .wrapContentWidth(unbounded = false)
            .fillMaxHeight()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // header Navigation drawer
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                val currentDate = SimpleDateFormat(
                    "dd MMMM yyyy",
                    Locale("id", "ID")
                ).format(System.currentTimeMillis())
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Taskly", fontSize = 24.sp, color = DarkCharcoal)
                    if (isVisibleCloseDrawer) {
                        IconButton(
                            onClick = onDrawerClicked,
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(imageVector = Icons.Default.MenuOpen, contentDescription = null)
                        }
                    }
                }
                Text(text = currentDate, fontSize = 12.sp, color = GreyText)
                MyTextClock(Modifier.padding(vertical = 8.dp))
            }

        }
        val navigationItemsColor =
            NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                selectedContainerColor = DarkCharcoal,
                selectedTextColor = Color.White,
                unselectedTextColor = DarkCharcoal,
                unselectedIconColor = DarkCharcoal,
                selectedIconColor = Color.White
            )
        listNavigationItem.forEach { navigationItem ->
            NavigationDrawerItem(
                label = {
                    Text(
                        text = navigationItem.title,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = {
                    val icon = if (selectedDestination == navigationItem.destination) {
                        navigationItem.iconSelected
                    } else {
                        navigationItem.icon
                    }
                    Icon(imageVector = icon, contentDescription = null)
                },
                selected = navigationItem.destination == selectedDestination,
                colors = navigationItemsColor,
                onClick = { onNavigateTo(navigationItem.destination) })
        }


    }
}


@Composable
fun MyTextClock(modifier: Modifier = Modifier) {
    AndroidView(factory = { context ->
        TextClock(context).apply {
            format24Hour?.let {
                this.format24Hour = "HH: mm: ss"
            }
            timeZone?.let { this.timeZone = it }
            textSize.let { this.textSize = 24f }
        }
    }, modifier = modifier)
}

@Composable
fun WrapperUI(
    navigationType: NavigationType,
    drawerState: DrawerState,
    scope: CoroutineScope,
    destinationScreen: DestinationScreen,
    onNavigateTo: (DestinationScreen) -> Unit,
    content: @Composable () -> Unit
) {
    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    NavigationDrawerContent(
                        selectedDestination = destinationScreen,
                        onNavigateTo = onNavigateTo,
                        onDrawerClicked = {
                            scope.launch {
                                val currentDrawerState = drawerState.currentValue
                                if (currentDrawerState == DrawerValue.Open) {
                                    drawerState.close()
                                } else {
                                    drawerState.open()
                                }
                            }
                        })
                }
            }
        ) {
            content()
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerContent(
                        selectedDestination = destinationScreen,
                        onNavigateTo = onNavigateTo,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            },
            drawerState = drawerState,
            gesturesEnabled = navigationType == NavigationType.NAVIGATION_RAIL
        ) {
            content()
        }
    }


}

@Composable
fun ContentContainer(
    navigationType: NavigationType,
    destinationScreen: DestinationScreen,
    onNavigateTo: (DestinationScreen) -> Unit,
    onDrawerClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {},
        bottomBar = {
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(
                    selectedDestination = destinationScreen,
                    onNavigateTo = onNavigateTo
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                NavigationRailDrawer(
                    selectedDestination = destinationScreen,
                    onNavigateTo = onNavigateTo,
                    onDrawerClicked = onDrawerClicked
                )
            }
            content()
        }
    }

}
