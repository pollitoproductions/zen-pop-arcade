# Zen Pop Arcade - Android Build Guide

## Project Overview

This is a complete Android App Bundle (AAB) configuration for **Zen Pop Arcade**, ready for Google Play Store distribution.

**Technology Stack:**
- Gradle 8.1.1
- Java 17
- Android SDK 34 (target)
- Android 7.0+ (minimum)

## What's Included

```
┌─ html/          (Original web game)
│  ├─ index.html
│  └─ README.md
│
└─ android/       (Android wrapper project)
   ├─ app/        (Application code & resources)
   ├─ gradle/     (Gradle wrapper)
   ├── README.md  (Detailed setup guide)
   ├── QUICKSTART.md
   ├── ICON_SETUP.md
   └── *.gradle   (Build configuration)
```

## Quick Build (5 minutes)

### Step 1: Add App Icon
The icon you provided will fill the entire icon space.

**Option A - Automated:**
```bash
cd android/
./generate_icons.sh /path/to/your/icon.png
```

**Option B - Manual:**
Place your icon in these directories (after resizing):
- `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48×48)
- `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72×72)
- `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96×96)
- `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144×144)
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192×192)

See `android/ICON_SETUP.md` for detailed icon setup instructions.

### Step 2: Create Signing Key
```bash
cd android/
keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias android

# Answer the prompts (remember your passwords!)
```

### Step 3: Build Release AAB
```bash
./gradlew bundleRelease
```

**Output:** `app/build/outputs/bundle/release/app-release.aab`

### Step 4: Upload to Google Play Console
1. Go to [Google Play Console](https://play.google.com/console)
2. Create a new app
3. Upload the AAB file to the internal testing track
4. Fill in app details (description, screenshots, etc.)
5. Submit for review

## Project Structure

### App Configuration
- **Package Name:** `com.pollitoproductions.zenpoparcade`
- **App Name:** Zen Pop Arcade
- **Min SDK:** Android 7.0 (API 24)
- **Target SDK:** Android 14 (API 34)

### Key Files
| File | Purpose |
|------|---------|
| `app/src/main/java/MainActivity.java` | WebView activity - loads the HTML game |
| `app/src/main/assets/index.html` | Web game interface |
| `app/build.gradle` | App-level build config (version, SDK versions) |
| `build.gradle` | Project-level config |
| `gradle.properties` | Gradle settings (Java 17, optimization) |

### Resources
- **Icons:** `app/src/main/res/mipmap-*/` (per device density)
- **Layouts:** `app/src/main/res/layout/` (UI XML)
- **Colors/Strings:** `app/src/main/res/values/` (app text & colors)
- **Web Assets:** `app/src/main/assets/` (HTML/CSS/JS)

## Gradle Configuration

### Versions Used
```gradle
// Gradle: 8.1.1
// Java: 17
// Compile SDK: 34
// Min SDK: 24
```

These are set in:
- `gradle/wrapper/gradle-wrapper.properties` → Gradle version
- `gradle.properties` → Java home path
- `app/build.gradle` → SDK versions

### Building Variants

```bash
cd android/

# Debug APK (for testing)
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk

# Release AAB (for Play Store - RECOMMENDED)
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab

# Release APK (alternative)
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

## WebView Configuration

The app displays your HTML game in a WebView with:
- ✅ Full-screen immersive mode
- ✅ Landscape orientation
- ✅ Hardware acceleration (smooth gameplay)
- ✅ Screen always on during gameplay
- ✅ JavaScript enabled (required for game)
- ✅ Auto-looping capability

## Important Notes

### Icon Requirements
- **Must fill entire icon space** - no white padding around edges
- Sizes: 48dp (mdpi) up to 192dp (xxxhdpi)
- Format: PNG
- Safe zone: Keep critical content within 161×161px at 192×192px

### Signing
- Create a **keystore** before first release: `keytool -genkey...`
- **Backup keystore.jks** - you'll need it for app updates
- Never share keystore or passwords
- Store passwords securely (not in git)

### Version Management
Update before each release in `app/build.gradle`:
```gradle
versionCode 1         // Increment by 1 for each release
versionName "1.0.0"   // Use semantic versioning
```

### App Updates
To update the game:
1. Modify `app/src/main/assets/index.html` or add new files
2. Build new AAB: `./gradlew clean bundleRelease`
3. Increment `versionCode` in `app/build.gradle`
4. Upload new AAB to Play Console

## Requirements

### Local Development
- **Java 17** or higher
- **Android SDK** installed (API 34 minimum for compilation)
- **Android Build Tools 34.0.0+**
- 2GB+ disk space for SDK/Gradle cache

### Environment Setup
```bash
# Set ANDROID_HOME (if not already set)
export ANDROID_HOME=$HOME/Android/Sdk

# Or on Windows:
# Set environment variable:  ANDROID_HOME = C:\Users\YourName\AppData\Local\Android\Sdk
```

## Troubleshooting

### "Gradle downloads are failing"
- Check internet connection
- Set proxy in `gradle.properties` if behind corporate firewall

### "Java 17 not found"
```bash
# Check Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/java-17
```

### "Build succeeds but app crashes"
- Check WebView content loads: `app/src/main/assets/index.html` exists
- Check device has "INTERNET" permission (it does in manifest)
- Test on API 24 device first

### "Icon not showing"
- Verify PNG files exist in all `mipmap-*` directories
- File names must be exactly `ic_launcher.png`
- Run `./gradlew clean bundleRelease` to rebuild

## Documentation

- **[README.md](android/README.md)** - Detailed setup & configuration
- **[QUICKSTART.md](android/QUICKSTART.md)** - Quick reference
- **[ICON_SETUP.md](android/ICON_SETUP.md)** - Icon generation guide

## Next Steps

1. ✅ Review this guide
2. 🔨 Add your app icon
3. 🔐 Generate signing key
4. 📦 Build release AAB
5. 🧪 Test on device
6. 🚀 Submit to Google Play Console

## Google Play Requirements

Before submission, ensure:
- [ ] App icon 512×512 PNG (no transparent areas)
- [ ] App description (max 4000 chars)
- [ ] Screenshots (at least 2, up to 8)
- [ ] Short description (max 80 chars)
- [ ] Release notes for version
- [ ] Privacy policy URL (if collecting data)
- [ ] Content rating questionnaire filled
- [ ] Categories selected

## Support

- [Android Developer Docs](https://developer.android.com/)
- [Gradle Documentation](https://gradle.org/documentation)
- [Google Play Console Help](https://support.google.com/googleplay/android-developer)

---

**Project:** Zen Pop Arcade  
**Creator:** Pollito Productions  
**Built:** Android 14 (API 34)  
**Status:** Production-ready for Google Play Store
