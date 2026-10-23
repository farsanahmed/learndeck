package com.learndeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learndeck.data.LessonRepository
import com.learndeck.model.Track
import com.learndeck.ui.home.HomeScreen
import com.learndeck.ui.lesson.LessonScreen
import com.learndeck.ui.theme.LearnDeckTheme
import com.learndeck.ui.track.TrackScreen

class MainActivity : ComponentActivity() {

    private val repository: LessonRepository by lazy { LessonRepository(this) }
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnDeckTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, "home") {
                        composable("home") {
                            HomeScreen(onTrackClick = { track ->
                                navController.navigate("track/${track.id}")
                            })
                        }
                        composable(
                            route = "track/{trackId}",
                            arguments = listOf(androidx.navigation.navArgument("trackId") { type = androidx.navigation.NavType.StringType })
                        ) { backStackEntry ->
                            val trackId = backStackEntry.getString() ?: Track.LINUX.id
                            val track = Track.values().find { it.id == trackId } ?: Track.LINUX
                            TrackScreen(
                                trackTitle = track.displayName,
                                lessons = viewModel.getLessonsForTrack(track),
                                onLessonClick = { lesson ->
                                    navController.navigate("lesson/${track.id}/${lesson.id}")
                                }
                            )
                        }
                        composable(
                            route = "lesson/{trackId}/{lessonId}",
                            arguments = listOf(
                                androidx.navigation.navArgument("trackId") { type = androidx.navigation.NavType.StringType },
                                androidx.navigation.navArgument("lessonId") { type = androidx.navigation.NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val trackId = backStackEntry.getString() ?: Track.LINUX.id
                            val lessonId = backStackEntry.getString() ?: ""
                            val track = Track.values().find { it.id == trackId } ?: Track.LINUX
                            val lesson = viewModel.getLesson(track, lessonId)
                            lesson?.let {
                                LessonScreen(lesson = it, onBack = { navController.popBackStack() })
                            }
                        }
                    }
                }
            }
        }
    }
}
