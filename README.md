# LearnDeck

**LearnDeck** is an open-source, offline-first Android app that teaches Linux, Coding (Python), and AI Agents. No internet required, no ads, no tracking, no accounts — just learning.

## Features

- **Three learning tracks**: Linux command line, Python programming, AI Agents
- **Offline-first**: All lessons stored locally in the app assets
- **Markdown rendering**: Clean, readable lessons with syntax-highlighted code blocks
- **Copy code**: One-tap copy button on every code block
- **Dark/Light theme**: Follows system setting automatically
- **Zero permissions**: No internet access, no storage access, no analytics
- **Open source**: MIT licensed, contributions welcome

## Screenshots

| Home | Track | Lesson |
|------|-------|--------|
| Three track cards | Lesson list per track | Markdown with code blocks |

## Building the App

### Prerequisites

- Android Studio Ladybug (2024.2) or later
- JDK 17+
- Android SDK 34

### Build Steps

```bash
# Clone the repository
git clone https://github.com/yourusername/LearnDeck.git
cd LearnDeck

# Open in Android Studio
# File > Open > Select the LearnDeck folder

# Build and run
./gradlew assembleDebug
# or use the Run button in Android Studio
```

### Command Line Build

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires signing config)
./gradlew assembleRelease
```

The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

## Project Structure

```
LearnDeck/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/learndeck/
│   │   │   │   ├── data/           # LessonRepository - loads & parses lessons
│   │   │   │   ├── model/          # Lesson, LessonFrontMatter, Track
│   │   │   │   ├── ui/
│   │   │   │   │   ├── home/       # HomeScreen - track selection
│   │   │   │   │   ├── track/      # TrackScreen - lesson list
│   │   │   │   │   ├── lesson/     # LessonScreen - markdown viewer
│   │   │   │   │   └── theme/      # Theme.kt, Type.kt
│   │   │   │   ├── MainActivity.kt # Navigation host
│   │   │   │   └── MainViewModel.kt
│   │   │   ├── assets/lessons/     # Markdown lesson files
│   │   │   │   ├── linux/
│   │   │   │   ├── coding/
│   │   │   │   └── agents/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
├── settings.gradle.kts
├── README.md
├── LICENSE
├── CONTRIBUTING.md
└── .gitignore
```

## Adding a New Lesson

1. **Choose a track**: `linux`, `coding`, or `agents`
2. **Create a Markdown file** in `app/src/main/assets/lessons/<track>/`
3. **Name it** with a two-digit order prefix: `NN-slug.md` (e.g., `03-permissions.md`)
4. **Add front matter** at the top:

```markdown
---
title: "Lesson Title"
track: "linux"          # linux, coding, or agents
order: 3                # numeric order in the track
summary: "One-line description shown in the lesson list"
---

# Lesson Title

Your lesson content in Markdown...

## Code Example

```bash
echo "Code blocks get syntax highlighting and a copy button"
```
```

5. **Rebuild** — No code changes needed! The app discovers lessons at runtime.

## Lesson Guidelines

- Keep lessons beginner-friendly (~150-300 words)
- Use clear headings (`##`, `###`)
- Include practical code examples
- Use tables for comparisons
- Add a "Summary" section at the end
- Test that the markdown renders correctly

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM with ViewModel + Repository
- **Navigation**: Navigation Compose
- **Markdown**: Markwon (offline, no WebView)
- **Serialization**: Kotlinx Serialization (YAML for front matter)
- **Min SDK**: 28 (Android 9+)
- **Target SDK**: 34

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on:
- Adding lessons
- Code style
- Pull request process
- Reporting issues

## License

MIT License — see [LICENSE](LICENSE) for details.

## Acknowledgments

- [Markwon](https://github.com/noties/Markwon) for Markdown rendering
- [Material 3](https://m3.material.io/) for design system
- All contributors who add lessons and improvements
