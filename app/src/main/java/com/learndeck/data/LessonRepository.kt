package com.learndeck.data

import android.content.Context
import android.content.res.AssetManager
import com.learndeck.model.Lesson
import com.learndeck.model.LessonFrontMatter
import com.learndeck.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.yaml.Yaml
import kotlinx.serialization.yaml.yaml
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern

class LessonRepository(private val context: Context) {

    private val yaml = Yaml {
        explicitNulls = false
    }

    suspend fun getTracks(): List<Track> = withContext(Dispatchers.IO) {
        Track.values().toList()
    }

    suspend fun getLessonsForTrack(track: Track): List<Lesson> = withContext(Dispatchers.IO) {
        val assetManager = context.assets
        val trackDir = "lessons/${track.id}"
        try {
            val files = assetManager.list(trackDir) ?: emptyArray()
            files
                .filter { it.endsWith(".md") }
                .map { parseLesson(assetManager, trackDir, it) }
                .filterNotNull()
                .sortedBy { it.order }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun parseLesson(assetManager: AssetManager, trackDir: String, fileName: String): Lesson? {
        val path = "$trackDir/$fileName"
        return try {
            assetManager.open(path).use { inputStream ->
                val reader = InputStreamReader(inputStream)
                val content = reader.readText()
                val (frontMatter, body) = parseFrontMatter(content)
                val meta = yaml.decodeFromString<LessonFrontMatter>(frontMatter)
                val id = fileName.substringBefore(".").substringAfter("-")
                Lesson(
                    id = id,
                    title = meta.title,
                    track = meta.track,
                    order = meta.order,
                    summary = meta.summary,
                    body = body.trim()
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun parseFrontMatter(content: String): Pair<String, String> {
        val pattern = Pattern.compile("^---\\s*\\n(.*?)\\n---\\s*\\n(.*)", Pattern.DOTALL)
        val matcher = pattern.matcher(content)
        if (matcher.find()) {
            return matcher.group(1) to matcher.group(2)
        }
        return "" to content
    }

    suspend fun getLesson(track: Track, lessonId: String): Lesson? = withContext(Dispatchers.IO) {
        getLessonsForTrack(track).find { it.id == lessonId }
    }
}
