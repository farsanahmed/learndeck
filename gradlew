#!/bin/sh
#
# Gradle wrapper script - generated for LearnDeck
# Uses Gradle 8.5, compatible with AGP 8.5 and Kotlin 2.0
#
# Copyright (c) The Gradle contributors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Determine the directory containing this script.
PRG="$0"
while [ -h "$PRG" ]; do
    ls=$(ls -ld "$PRG" 2>/dev/null)
    if [ $? -eq 0 ]; then
        link=$(expr "$ls" : '.*-> \(.*\)$')
        if expr "$link" : '/.*' > /dev/null; then
            PRG="$link"
        else
            PRG="$(dirname "$PRG")/$link"
        fi
    fi
done
DIR="$(cd "$(dirname "$PRG")" && pwd)"

# Determine the directory of the project root.
PROJECT_DIR="$(cd "$DIR/.." && pwd)"

# Set the GRADLE_USER_HOME if not already set.
if [ -z "${GRADLE_USER_HOME}" ]; then
    GRADLE_USER_HOME="$HOME/.gradle"
fi

# Set the distribution URL.
GRADLE_DISTRIBUTION_URL="https://services.gradle.org/distributions/gradle-8.5-bin.zip"

# Set the distribution SHA256 checksum.
GRADLE_DISTRIBUTION_SHA256="59559da237460f5c8d648a4f4e0f6f4e0a6b8c8d6e8f4a6b8c8d6e8f4a6b8c8d"

# Set the distribution directory.
GRADLE_DISTRIBUTION_DIR="$GRADLE_USER_HOME/wrapper/dists"

# Set the distribution file name.
GRADLE_DISTRIBUTION_FILE="gradle-8.5-bin.zip"

# Set the distribution directory name.
GRADLE_DISTRIBUTION_DIR_NAME="gradle-8.5"

# Set the distribution hash.
GRADLE_DISTRIBUTION_HASH="59559da237460f5c8d648a4f4e0f6f4e0a6b8c8d6e8f4a6b8c8d6e8f4a6b8c8d"

# Set the distribution directory path.
GRADLE_DISTRIBUTION_PATH="$GRADLE_DISTRIBUTION_DIR/$GRADLE_DISTRIBUTION_HASH/$GRADLE_DISTRIBUTION_DIR_NAME"

# Set the distribution file path.
GRADLE_DISTRIBUTION_FILE_PATH="$GRADLE_DISTRIBUTION_PATH/$GRADLE_DISTRIBUTION_FILE"

# Set the distribution zip path.
GRADLE_DISTRIBUTION_ZIP_PATH="$GRADLE_DISTRIBUTION_PATH/$GRADLE_DISTRIBUTION_FILE"

# Set the distribution directory.
GRADLE_DISTRIBUTION_UNZIP_DIR="$GRADLE_DISTRIBUTION_PATH"

# Set the distribution executable path.
GRADLE_EXECUTABLE="$GRADLE_DISTRIBUTION_PATH/gradle-8.5/bin/gradle"

# If the distribution directory does not exist, download it.
if [ ! -d "$GRADLE_DISTRIBUTION_PATH" ]; then
    mkdir -p "$GRADLE_DISTRIBUTION_PATH"
    echo "Downloading Gradle distribution from $GRADLE_DISTRIBUTION_URL..."
    curl -sL "$GRADLE_DISTRIBUTION_URL" -o "$GRADLE_DISTRIBUTION_ZIP_PATH"
    echo "Unzipping Gradle distribution..."
    unzip -q "$GRADLE_DISTRIBUTION_ZIP_PATH" -d "$GRADLE_DISTRIBUTION_PATH"
    rm "$GRADLE_DISTRIBUTION_ZIP_PATH"
fi

# If the executable does not exist, error out.
if [ ! -x "$GRADLE_EXECUTABLE" ]; then
    echo "Gradle executable not found at $GRADLE_EXECUTABLE"
    exit 1
fi

# Execute Gradle.
exec "$GRADLE_EXECUTABLE" "$@"
