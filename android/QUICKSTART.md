# Quick Start Guide - Zen Pop Arcade Android Build

## TL;DR - Build Steps

### 1. Add Icons
```bash
cd android
./generate_icons.sh /path/to/your/icon.png
# Or on Windows: generate_icons.bat C:\path\to\your\icon.png
```

### 2. Setup Signing
```bash
# Generate keystore
keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias android

# Create keystore.properties
cp keystore.properties.example keystore.properties
# Edit keystore.properties with your passwords
```

### 3. Build Release AAB
```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

---

## Detailed Setup

### Prerequisites
- JDK 17+ installed
- Android SDK installed (via Android Studio)
- ANDROID_HOME environment variable set

Export ANDROID_HOME:
```bash
# Linux/macOS
export ANDROID_HOME=$HOME/Android/Sdk

# Windows - Set in Environment Variables
setx ANDROID_HOME C:\Users\YourUsername\AppData\Local\Android\Sdk
```

### Project Configuration

**Current Configuration:**
- **App ID:** `com.pollitoproductions.zenpoparcade`
- **App Name:** Zen Pop Arcade
- **Gradle:** 8.1.1
- **Java:** 17
- **API Levels:** Min 24, Target 34

### Building

#### Debug Build (for testing)
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

#### Release AAB (for Google Play)
```bash
./gradlew bundleRelease
```
Output: `app/build/outputs/bundle/release/app-release.aab`

#### Clean Build
```bash
./gradlew clean bundleRelease
```

### Gradle Tasks

```bash
# List all available tasks
./gradlew tasks

# Build specific variants
./gradlew assembleRelease      # APK
./gradlew bundleRelease        # AAB (recommended for Play Store)
./gradlew bundleDebug          # Debug bundle

# Check dependencies
./gradlew dependencies

# Run lint checks
./gradlew lint
```

### Troubleshooting

#### "ANDROID_HOME not set"
```bash
# Find your SDK installation
echo $ANDROID_HOME

# Set if missing
export ANDROID_HOME=$HOME/Android/Sdk
```

#### "Gradle wrapper not executable"
```bash
chmod +x gradlew
```

#### "Keystore file not found"
- Ensure `keystore.jks` is in the `android/` directory
- Or update the path in `app/build.gradle`

#### "Icon not showing in app"
- Check files exist in `app/src/main/res/mipmap-*` directories
- Names must be `ic_launcher.png`
- Test with: `./gradlew clean bundleRelease`

---

## Google Play Store Checklist

- [ ] App icons generated and tested
- [ ] Keystore created and backed up safely
- [ ] App signed with release keystore
- [ ] Version code incremented in `app/build.gradle`
- [ ] Tested on multiple devices (or emulator)
- [ ] ProGuard rules verified (no crashes with minification)
- [ ] All permissions justified
- [ ] Privacy policy created
- [ ] App description and screenshots prepared
- [ ] AAB uploaded to Google Play Console
- [ ] Initial testing on internal/alpha tracks completed

---

## File Locations

| File | Purpose |
|------|---------|
| `app/build.gradle` | App-level build configuration |
| `build.gradle` | Project-level build configuration |
| `AndroidManifest.xml` | App manifest and permissions |
| `app/src/main/java/.../*.java` | Source code |
| `app/src/main/res/` | Resources (layouts, icons, values) |
| `app/src/main/assets/` | Web assets (HTML, CSS, JS) |
| `keystore.jks` | Signing key (generated locally) |
| `gradle.properties` | Gradle configuration |

---

## Environment Variables

```bash
# Optional - speeds up builds
export ORG_GRADLE_PROJECT_android_build_tools_version=34.0.0
export ORG_GRADLE_PROJECT_compileSdk=34

# Java configuration
export JAVA_HOME=/path/to/java-17
```

---

## Support

For issues:
1. Check the detailed README.md
2. Review Android documentation
3. Check Gradle build output for specific errors
4. Test with: `./gradlew --debug bundleRelease 2>&1 | tail -100`
