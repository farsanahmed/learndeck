package com.learndeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learndeck.data.LessonRepository
import com.learndeck.model.Lesson
import com.learndeck.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: LessonRepository) : ViewModel() {

    private val _lessonsByTrack = MutableStateFlow<Map<String, List<Lesson>>>(emptyMap())
    val lessonsByTrack = _lessonsByTrack.asStateFlow()

    fun loadLessons(track: Track) {
        viewModelScope.launch {
            try {
                val lessons = repository.getLessonsForTrack(track)
                _lessonsByTrack.value = _lessonsByTrack.value.toMutableMap().apply {
                    put(track.id, lessons)
                }
            } catch (e: Exception) {
                _lessonsByTrack.value = _lessonsByTrack.value.toMutableMap().apply {
                    put(track.id, emptyList())
                }
            }
        }
    }

    fun getLessonsForTrack(track: Track): List<Lesson> {
        return _lessonsByTrack.value[track.id] ?: emptyList()
    }

    suspend fun getLesson(track: Track, lessonId: String): Lesson? {
        return repository.getLesson(track, lessonId)
    }

    companion object {
        class Factory(private val repository: LessonRepository) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }
}
