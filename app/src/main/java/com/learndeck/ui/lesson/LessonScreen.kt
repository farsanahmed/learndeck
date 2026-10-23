package com.learndeck.ui.lesson

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learndeck.model.Lesson
import com.learndeck.ui.theme.LearnDeckTypography
import io.noties.markwon.Markwon
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.ext.tables.TablePlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LessonScreen(
    modifier: Modifier = Modifier,
    lesson: Lesson,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val markwon = remember {
        Markwon.builder(context)
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create())
            .usePlugin(TablePlugin.create())
            .build()
    }
    val scrollState = rememberScrollState()
    var copied by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = { Text(text = lesson.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            MarkdownRenderer(
                markdown = lesson.body,
                onCodeBlockClick = { code ->
                    copyToClipboard(context, code)
                    copied = true
                    LaunchedEffect(copied) {
                        if (copied) {
                            delay(1500)
                            copied = false
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun MarkdownRenderer(
    markdown: String,
    onCodeBlockClick: (String) -> Unit
) {
    val blocks = parseMarkdown(markdown)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        blocks.forEach { block ->
            when (block) {
                is MarkdownBlock.Text -> {
                    Text(
                        text = block.content,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                }
                is MarkdownBlock.Heading -> {
                    Text(
                        text = block.content,
                        fontSize = when (block.level) {
                            1 -> 28.sp
                            2 -> 24.sp
                            3 -> 20.sp
                            else -> 18.sp
                        },
                        fontWeight = FontWeight.Bold,
                        style = LearnDeckTypography.headlineMedium
                    )
                }
                is MarkdownBlock.CodeBlock -> {
                    CodeBlockView(
                        code = block.content,
                        language = block.language,
                        onCopyClick = { onCodeBlockClick(block.content) }
                    )
                }
                is MarkdownBlock.ListItem -> {
                    Text(
                        text = "• ${block.content}",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CodeBlockView(
    code: String,
    language: String?,
    onCopyClick: () -> Unit
) {
    val isDark = androidx.compose.material3.MaterialTheme.colorScheme.isDark
    val backgroundColor = if (isDark) Color(0xFF1E1E1E) else Color(0xFFF5F5F5)
    val textColor = if (isDark) Color(0xFFD4D4D4) else Color(0xFF1A1A1A)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 8.dp, 12.dp, 0.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    language?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onCopyClick) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = "Copy code",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 0.dp, 12.dp, 12.dp)
                    .background(backgroundColor)
            ) {
                Text(
                    text = code,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    color = textColor,
                    overflow = TextOverflow.Visible,
                    softWrap = true,
                    maxLines = Int.MAX_VALUE,
                    style = LearnDeckTypography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp
                    )
                )
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("code", text)
    clipboard.primaryClip = clip
    android.widget.Toast.makeText(context, "Copied!", android.widget.Toast.LENGTH_SHORT).show()
}

sealed interface MarkdownBlock {
    data class Text(val content: String) : MarkdownBlock
    data class Heading(val level: Int, val content: String) : MarkdownBlock
    data class CodeBlock(val content: String, val language: String?) : MarkdownBlock
    data class ListItem(val content: String) : MarkdownBlock
}

private fun parseMarkdown(markdown: String): List<MarkdownBlock> {
    val blocks = mutableListOf<MarkdownBlock>()
    val lines = markdown.split("\n")
    var i = 0
    var inCodeBlock = false
    var codeContent = ""
    var codeLanguage: String? = null

    while (i < lines.size) {
        val line = lines[i]

        if (line.startsWith("```")) {
            if (!inCodeBlock) {
                inCodeBlock = true
                codeLanguage = line.substring(3).trim().takeIf { it.isNotBlank() }
                codeContent = ""
            } else {
                inCodeBlock = false
                blocks.add(MarkdownBlock.CodeBlock(codeContent.trim(), codeLanguage))
            }
        } else if (inCodeBlock) {
            codeContent += line + "\n"
        } else if (line.startsWith("#")) {
            val level = line.takeWhile { it == '#' }.length
            val content = line.substring(level).trim()
            if (content.isNotBlank()) {
                blocks.add(MarkdownBlock.Heading(level, content))
            }
        } else if (line.startsWith("- ") || line.startsWith("* ")) {
            blocks.add(MarkdownBlock.ListItem(line.substring(2).trim()))
        } else if (line.trim().isNotBlank()) {
            var textContent = line
            var j = i + 1
            while (j < lines.size) {
                val nextLine = lines[j]
                if (nextLine.trim().isBlank() ||
                    nextLine.startsWith("#") ||
                    nextLine.startsWith("```") ||
                    nextLine.startsWith("- ") ||
                    nextLine.startsWith("* ")
                ) break
                textContent += " " + nextLine.trim()
                j++
            }
            blocks.add(MarkdownBlock.Text(textContent))
            i = j - 1
        }
        i++
    }

    return blocks
}
