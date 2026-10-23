# Contributing to LearnDeck

Thank you for contributing! LearnDeck is a community-driven project and every contribution helps.

## Ways to Contribute

- **Add lessons** — The easiest way to help (no code required)
- **Fix bugs** — Report issues and submit fixes
- **Improve UI/UX** — Enhance the learning experience
- **Translate** — Add localization support (future)
- **Documentation** — Improve docs and comments

---

## Adding a Lesson (No Code Required!)

This is the most impactful way to contribute. Lessons live as Markdown files in the assets folder — the app discovers them automatically at runtime.

### Step 1: Choose a Track

| Track | Directory | Topics |
|-------|-----------|--------|
| Linux | `app/src/main/assets/lessons/linux/` | Command line, filesystem, permissions, scripting, package management |
| Coding (Python) | `app/src/main/assets/lessons/coding/` | Variables, control flow, functions, data structures, OOP, modules |
| AI Agents | `app/src/main/assets/lessons/agents/` | Agent concepts, prompts, tools, memory, planning, multi-agent systems |

### Step 2: Create the Lesson File

**File naming**: `NN-descriptive-slug.md` (e.g., `03-file-permissions.md`)

- `NN` = two-digit order number (01, 02, 03...)
- `descriptive-slug` = lowercase, hyphen-separated

### Step 3: Use This Template

Copy the template below and fill in your content:

```markdown
---
title: "Your Lesson Title"
track: "linux"          # linux, coding, or agents
order: 3                # must match the NN in filename
summary: "One-line description shown in the lesson list (max ~80 chars)"
---

# Your Lesson Title

Brief introduction paragraph (2-3 sentences) explaining what this lesson covers and why it matters.

## Section Heading

Explain the concept clearly. Use simple language. Assume the reader is a beginner.

### Subsection (Optional)

More detail if needed.

## Practical Example

Show real code the learner can try:

```bash
# For Linux lessons
ls -la
chmod 755 script.sh
```

```python
# For Python lessons
def greet(name: str) -> str:
    return f"Hello, {name}!"

print(greet("World"))
```

```python
# For Agents lessons (pseudocode or Python)
tools = ["web_search", "run_code"]
agent = Agent(goal="Summarize Python 3.12", tools=tools)
result = agent.run()
```

## Key Points Summary

Use a table for quick reference:

| Concept | Description |
|---------|-------------|
| `ls` | List directory contents |
| `chmod` | Change file permissions |

## Practice Exercise

Give the learner something to try:

```bash
# Try this in your terminal
mkdir practice
cd practice
touch test.txt
chmod 644 test.txt
ls -l
```

## Summary

Wrap up with 2-3 sentences reinforcing what was learned and previewing the next lesson.

---

### Lesson Quality Checklist

Before submitting, verify:

- [ ] Front matter is valid YAML (no tabs, proper indentation)
- [ ] `track` matches the directory (`linux`, `coding`, or `agents`)
- [ ] `order` matches the filename prefix and doesn't conflict
- [ ] `summary` is concise and descriptive
- [ ] Content is ~150-300 words (beginner-friendly depth)
- [ ] Code blocks have language tags (`bash`, `python`, etc.)
- [ ] Headings use `##` and `###` (not `#` — reserved for lesson title)
- [ ] No external links or images (offline-first!)
- [ ] No Unicode characters that might not render on all devices
- [ ] Tested by building the app and viewing the lesson

---

## Code Contributions

### Setup

```bash
git clone https://github.com/yourusername/LearnDeck.git
cd LearnDeck
# Open in Android Studio
```

### Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use `ktlint` (configured in the project)
- Compose previews for UI components
- Meaningful commit messages (see below)

### Pull Request Process

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/my-change`
3. Make your changes
4. Run tests: `./gradlew test`
5. Build debug APK: `./gradlew assembleDebug`
6. Test on device/emulator
7. Submit PR with:
   - Clear title and description
   - Screenshots for UI changes
   - Reference any related issues

### Commit Message Format

```
type(scope): brief description

Longer explanation if needed

Fixes #123
```

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

Examples:
```
feat(lesson): add Linux permissions lesson
fix(ui): correct code block copy button tint in dark mode
docs(readme): update build instructions
```

---

## Reporting Issues

Use the issue tracker for:
- Bug reports (include device, Android version, steps to reproduce)
- Feature requests (explain the use case)
- Lesson suggestions (topic, track, why it fits)
- Documentation improvements

---

## Lesson Template (Copy-Paste Ready)

```markdown
---
title: ""
track: ""          # linux, coding, or agents
order: 0           # match filename prefix
summary: ""
---

# 

## 

## 

```bash
```

## Summary

```

---

## Questions?

Open a GitHub Discussion or issue. We're happy to help!

---

**Thank you for making LearnDeck better!** 🎓
