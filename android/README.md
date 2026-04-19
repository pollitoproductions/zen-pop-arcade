# Android WebView Wrapper - Zen Pop Arcade

This is an Android App Bundle (AAB) wrapper for the Zen Pop Arcade HTML game, ready for distribution on Google Play Store.

## Project Structure

```
android/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/pollitoproductions/zenpoparcade/
│   │       │   └── MainActivity.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   ├── mipmap-*/
│   │       │   │   └── ic_launcher.png
│   │       │   └── values/
│   │       │       ├── colors.xml
│   │       │       ├── strings.xml
│   │       │       └── styles.xml
│   │       ├── assets/
│   │       │   └── index.html
│   │       └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── gradle.properties
```

## Requirements

- **Java 17** or higher
- **Gradle 8.1.1**
- **Android SDK** (API 34 for compilation, API 24 minimum for runtime)
- **Android Build Tools 34.0.0** or higher

## Setup Instructions

### 1. Add App Icons

The app icon should fill the entire icon space without padding.

Download the provided icon image and convert it to PNG files at the following sizes:

- `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48×48)
- `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72×72)
- `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96×96)
- `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144×144)
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192×192)

**Using ImageMagick (Linux/Mac):**
```bash
# For the received icon, resize without padding
for size in 48 72 96 144 192; do
  if [ "$size" = "48" ]; then dpi="mdpi"; fi
  if [ "$size" = "72" ]; then dpi="hdpi"; fi
  if [ "$size" = "96" ]; then dpi="xhdpi"; fi
  if [ "$size" = "144" ]; then dpi="xxhdpi"; fi
  if [ "$size" = "192" ]; then dpi="xxxhdpi"; fi
  
  convert icon.png -resize "${size}x${size}!" \
    "app/src/main/res/mipmap-${dpi}/ic_launcher.png"
done
```

**Using Online Tools:**
- Use [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html) to generate icons
- Upload your icon image and download the packaged resources

### 2. Create a Keystore for Signing

Generate a keystore for signing the release AAB:

```bash
keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias android
```

Move the `keystore.jks` to the `android/` directory.

### 3. Configure Signing

Create `android/keystore.properties` with your keystore details:

```properties
storeFile=keystore.jks
storePassword=your_keystore_password
keyAlias=android
keyPassword=your_key_password
```

### 4. Build the AAB

Navigate to the `android` directory and run:

```bash
cd android

# Debug build
./gradlew assembleDebug

# Release AAB for Google Play
./gradlew bundleRelease
```

The generated AAB will be located at:
```
app/build/outputs/bundle/release/app-release.aab
```

### 5. Verify the Build

Check the generated AAB:
```bash
# List contents
unzip -l app/build/outputs/bundle/release/app-release.aab

# Analyze with bundletool (optional)
bundletool validate --bundle-path=app/build/outputs/bundle/release/app-release.aab
```

## Configuration

### App Details
- **Package Name:** `com.pollitoproductions.zenpoparcade`
- **App Name:** Zen Pop Arcade
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Compile SDK:** 34
- **Java Version:** 17

### Build Configuration

Edit `app/build.gradle` to customize:

```gradle
defaultConfig {
    applicationId "com.pollitoproductions.zenpoparcade"
    minSdk 24
    targetSdk 34
    versionCode 1          // Increment for each release
    versionName "1.0.0"    // Follow semantic versioning
}
```

### Permissions

The app requires:
- `INTERNET` - Load external resources
- `ACCESS_NETWORK_STATE` - Check network connectivity

These are already configured in `AndroidManifest.xml`.

## WebView Configuration

The `MainActivity` includes optimizations for:
- Hardware acceleration enabled
- JavaScript enabled (required for the game)
- DOM storage enabled for game data persistence
- Immersive fullscreen mode for optimal gameplay
- Landscape orientation forced
- Screen stays on during gameplay

## Optimization Features

- **ProGuard rules** - Code minification and obfuscation in release builds
- **Resource shrinking** - Removes unused resources in release builds
- **Bundle splits** - Optimizes download size by device capabilities:
  - Language split
  - Density split
  - ABI split

## Google Play Store Submission

1. Generate the release AAB as described above
2. Test with Google Play Console:
   - Upload to internal testing track
   - Test on various devices
3. Prepare Store Listing:
   - App name: "Zen Pop Arcade"
   - Description: "Relax to lounge music and perfect your throws to eliminate the balloons in as few, or as many throws as you feel."
   - Screenshots (1080×1920 recommended)
   - Icon (512×512 PNG, no padding)
4. Submit for review

## Troubleshooting

### Build Fails with Java Version Error
Ensure Java 17 is set:
```bash
export JAVA_HOME=/path/to/java17
```

### WebView Content Not Loading
- Check `assets/index.html` exists
- Verify `android:usesCleartextTraffic="false"` in manifest
- Ensure INTERNET permission is granted

### Icon Not Displaying
- Verify PNG files exist in all `mipmap-*` directories
- Ensure images are actual PNG files (not compressed webp)
- Check file names match: `ic_launcher.png`

### AAB Not Generating
- Ensure `keystore.jks` is in the `android/` directory
- Verify `keystore.properties` credentials are correct
- Check all Java source files compile without errors

## License

© 2026 Pollito Productions. All rights reserved.
