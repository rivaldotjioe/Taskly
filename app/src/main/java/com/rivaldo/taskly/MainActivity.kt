package com.rivaldo.taskly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rivaldo.taskly.ui.detail.DetailScreen
import com.rivaldo.taskly.ui.home.HomeScreen
import com.rivaldo.taskly.ui.home.HomeViewModel
import com.rivaldo.taskly.ui.theme.TasklyTheme
import com.rivaldo.taskly.ui.utils.DestinationScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasklyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    val navController = rememberNavController()
                    val scope = rememberCoroutineScope()
                    NavHost(
                        navController = navController,
                        startDestination = DestinationScreen.HOME.getRoute()
                    ) {
                        composable(DestinationScreen.HOME.getRoute()) { navBackStackEntry ->
                            HomeScreen(navController = navController)
                        }
                        composable(DestinationScreen.ADD_TASK.getRoute()) { navBackStackEntry ->


                        }
                        composable(DestinationScreen.DETAIL.getRoute()) { navBackStackEntry ->
                            val taskId =
                                navBackStackEntry.arguments?.getString(DestinationScreen.ID_TASK_KEY)
                            taskId?.let {
                                DetailScreen(navController = navController, idTask = it)
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TasklyTheme {
        Greeting("Android")
    }
}