package com.learndeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learndeck.data.LessonRepository
import com.learndeck.model.Track
import com.learndeck.ui.home.HomeScreen
import com.learndeck.ui.lesson.LessonScreen
import com.learndeck.ui.theme.LearnDeckTheme
import com.learndeck.ui.track.TrackScreen
import kotlinx.coroutines.delay

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
                            arguments = listOf(
                                androidx.navigation.navArgument("trackId") {
                                    type = androidx.navigation.NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val trackId = backStackEntry.getString() ?: Track.LINUX.id
                            val track = Track.values().find { it.id == trackId } ?: Track.LINUX

                            LaunchedEffect(track) {
                                viewModel.loadLessons(track)
                            }

                            val lessons = viewModel.getLessonsForTrack(track)
                            TrackScreen(
                                trackTitle = track.displayName,
                                lessons = lessons,
                                onLessonClick = { lesson ->
                                    navController.navigate("lesson/${track.id}/${lesson.id}")
                                }
                            )
                        }
                        composable(
                            route = "lesson/{trackId}/{lessonId}",
                            arguments = listOf(
                                androidx.navigation.navArgument("trackId") {
                                    type = androidx.navigation.NavType.StringType
                                },
                                androidx.navigation.navArgument("lessonId") {
                                    type = androidx.navigation.NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val trackId = backStackEntry.getString() ?: Track.LINUX.id
                            val lessonId = backStackEntry.getString() ?: ""
                            val track = Track.values().find { it.id == trackId } ?: Track.LINUX

                            var lesson by remember { mutableStateOf<Lesson?>(null) }

                            LaunchedEffect(trackId, lessonId) {
                                lesson = viewModel.getLesson(track, lessonId)
                            }

                            if (lesson != null) {
                                LessonScreen(
                                    lesson = lesson!!,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
