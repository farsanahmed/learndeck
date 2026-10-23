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

    private val _lessons = MutableStateFlow<List<Lesson>>(emptyList())
    val lessons = _lessons.asStateFlow()

    fun getLessonsForTrack(track: Track): List<Lesson> {
        // Load synchronously for now - in a real app we'd use a coroutine
        return try {
            repository.getLessonsForTrack(track).also { _lessons.value = it }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getLesson(track: Track, lessonId: String): Lesson? {
        return try {
            repository.getLesson(track, lessonId)
        } catch (e: Exception) {
            null
        }
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
