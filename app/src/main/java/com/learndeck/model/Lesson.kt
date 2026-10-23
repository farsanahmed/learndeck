package com.learndeck.model

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val id: String,
    val title: String,
    val track: String,
    val order: Int,
    val summary: String,
    val body: String
)

@Serializable
data class LessonFrontMatter(
    val title: String,
    val track: String,
    val order: Int,
    val summary: String
)

enum class Track(val id: String, val displayName: String, val summary: String) {
    LINUX("linux", "Linux", "Command line, file system, permissions, and scripting"),
    CODING("coding", "Coding (Python)", "Python basics, variables, loops, functions, and data structures"),
    AGENTS("agents", "AI Agents", "How AI agents work, prompts, tools, and orchestration")
}
