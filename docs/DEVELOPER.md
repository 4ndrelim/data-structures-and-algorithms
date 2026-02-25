# Developer Setup Guide

If you are a CS2040S student, your IDEA configurations should already be compatible with this project structure. Feel free to clone and use it as you see fit. The configuration below is as per CS2040S PS1 set-up guide.

## Setup Instructions

1. **Choose Java Version 11.0.XX for Project SDK**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/#java11)
   - Create account and login if necessary
   - Make sure to download the correct version compatible with your hardware

2. **Download IntelliJ (Community Edition)** from [JetBrains](https://www.jetbrains.com/idea/download/) if you do not have it

3. **Fork the repo and clone it on your local device**
   ```bash
   git clone https://github.com/YOUR_USERNAME/data-structures-and-algorithms.git
   cd data-structures-and-algorithms
   ```

4. **Launch IntelliJ** on your device and under the `Projects` tab, click `Open`. Navigate to where the local repo is cloned
   1. Configure to Java SDK (if not done) by heading to `File` on the top-left panel
   2. Click on `Project Structure...`
   3. Apply the desired Java SDK in the `SDK:` dropdown. Remember to click `Apply`

5. **Test if everything is properly set-up** with the command:
   ```bash
   ./gradlew clean test
   ```
   All files should be compiled and all test cases should pass.

## Project Structure

```
src/
├── main/java/
│   ├── dataStructures/    # Data structure implementations
│   └── algorithms/        # Algorithm implementations
├── test/java/             # JUnit tests
scripts/                   # Custom input runners
docs/                      # Documentation
```

## Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "dataStructures.avlTree.*"

# Clean and rebuild
./gradlew clean build
```

## Style Checking

Before contributing, ensure your code passes style checks:

```bash
# Check main source code style
./gradlew stylecheckMain

# Check test code style
./gradlew stylecheckTest
```

## Custom Inputs

To run algorithms with custom inputs, see the [scripts folder](../scripts/README.md).
