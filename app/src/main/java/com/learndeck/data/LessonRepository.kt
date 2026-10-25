package com.learndeck.data

import android.content.Context
import android.content.res.AssetManager
import com.learndeck.model.Lesson
import com.learndeck.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.util.regex.Pattern

class LessonRepository(private val context: Context) {

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
                .mapNotNull { parseLesson(assetManager, trackDir, it) }
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
                val meta = parseFrontMatter(content)
                val id = fileName.substringBefore(".").substringAfter("-")
                Lesson(
                    id = id,
                    title = meta.title,
                    track = meta.track,
                    order = meta.order,
                    summary = meta.summary,
                    body = meta.body.trim()
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun parseFrontMatter(content: String): ParsedMeta {
        val pattern = Pattern.compile(
            "^---\\s*\\n(.*?)\\n---\\s*\\n(.*)",
            Pattern.DOTALL
        )
        val matcher = pattern.matcher(content)
        if (!matcher.find()) {
            return ParsedMeta("", "", 0, "", content)
        }
        val frontMatter = matcher.group(1)
        val body = matcher.group(2)
        return ParsedMeta(
            title = frontMatter.line("title") ?: "",
            track = frontMatter.line("track") ?: "",
            order = frontMatter.line("order")?.toIntOrNull() ?: 0,
            summary = frontMatter.line("summary") ?: "",
            body = body
        )
    }

    private fun String.line(key: String): String? {
        val pattern = Pattern.compile("^\\s*$key:\\s*(.+)$", Pattern.MULTILINE)
        val matcher = pattern.matcher(this)
        return if (matcher.find()) matcher.group(1).trim() else null
    }

    private data class ParsedMeta(
        val title: String,
        val track: String,
        val order: Int,
        val summary: String,
        val body: String
    )

    suspend fun getLesson(track: Track, lessonId: String): Lesson? = withContext(Dispatchers.IO) {
        getLessonsForTrack(track).find { it.id == lessonId }
    }
}
