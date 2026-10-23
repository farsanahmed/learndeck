---
title: "The Linux File System"
track: "linux"
order: 2
summary: "Understand the Linux directory structure and navigation"
---

# The Linux File System

Linux organizes files in a hierarchical tree structure starting from the root directory `/`.

## Key Directories

| Directory | Purpose |
|-----------|---------|
| `/` | Root directory - top of the tree |
| `/home` | User home directories |
| `/etc` | System configuration files |
| `/var` | Variable data (logs, caches) |
| `/usr` | User programs and libraries |
| `/bin` | Essential system binaries |
| `/tmp` | Temporary files |

## Navigation Commands

```bash
# Go to home directory
cd ~

# Go to root directory
cd /

# Go up one level
cd ..

# Go to previous directory
cd -
```

## Absolute vs Relative Paths

- **Absolute path**: Starts with `/` (e.g., `/home/user/documents`)
- **Relative path**: Relative to current directory (e.g., `documents/notes.txt`)

## Practice Exercise

```bash
# Explore the file system
ls /
ls /home
ls /etc
cd /tmp
pwd
```

## Summary

The Linux file system is a tree with `/` at the root. Understanding the standard directories helps you navigate and find files efficiently. Next, we'll cover file permissions.
