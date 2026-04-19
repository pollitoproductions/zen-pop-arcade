#!/usr/bin/env markdown
# 🎮 Zen Pop Arcade - Android Build Quick Card

## What Was Built

Complete Android App Bundle (AAB) project for Google Play Store with:
- ✅ Gradle 8.1.1 & Java 17
- ✅ WebView wrapper for your HTML game
- ✅ Full production setup
- ✅ Code minification & optimization
- ✅ Signing configuration ready

---

## 🚀 Build in 3 Steps

### Step 1: Add Icons (2 min)
```bash
cd android/
./generate_icons.sh ~/your-icon.png
```
Or place 5 PNG files (48×48 to 192×192) in `app/src/main/res/mipmap-*/`

See: [ICON_SETUP.md](android/ICON_SETUP.md)

### Step 2: Create Signing Key (1 min)
```bash
keytool -genkey -v -keystore keystore.jks -keyalg RSA \
  -keysize 2048 -validity 10000 -alias android
```

### Step 3: Build Release AAB (3 min)
```bash
./gradlew bundleRelease
```

**Output:** `app/build/outputs/bundle/release/app-release.aab`

---

## 📂 Project Structure

```
/workspaces/zen-pop-lounge/
├── index.html                    (Your HTML game)
├── README.md                     (Original project)
├── ANDROID_BUILD.md              (Build guide)
├── ANDROID_PROJECT_SUMMARY.md    (What was created)
│
└── android/                      ← Android Project
    ├── app/
    │   ├── src/main/java/        (MainActivity.java - WebView activity)
    │   ├── src/main/res/
    │   │   ├── layout/           (activity_main.xml - UI layout)
    │   │   ├── values/           (colors, strings, styles)
    │   │   ├── mipmap-mdpi/      (48×48 icon) → ADD YOUR ICON
    │   │   ├── mipmap-hdpi/      (72×72 icon) → ADD YOUR ICON
    │   │   ├── mipmap-xhdpi/     (96×96 icon) → ADD YOUR ICON
    │   │   ├── mipmap-xxhdpi/    (144×144 icon) → ADD YOUR ICON
    │   │   └── mipmap-xxxhdpi/   (192×192 icon) → ADD YOUR ICON
    │   ├── src/main/assets/
    │   │   └── index.html        (Your game - auto-embedded)
    │   ├── src/main/AndroidManifest.xml
    │   ├── build.gradle          (App config - Java 17, API 34)
    │   └── proguard-rules.pro    (Code minification)
    │
    ├── gradle/wrapper/gradle-wrapper.properties  (Gradle 8.1.1)
    ├── build.gradle              (Project config)
    ├── settings.gradle           (Module config)
    ├── gradle.properties         (JVM config)
    ├── gradlew                   (Build script - Unix)
    ├── gradlew.bat               (Build script - Windows)
    │
    ├── README.md                 (Detailed setup)
    ├── QUICKSTART.md            (Quick reference)
    ├── ICON_SETUP.md            (Icon instructions)
    ├── generate_icons.sh        (Auto-resize icons Linux/Mac)
    ├── generate_icons.bat       (Auto-resize icons Windows)
    ├── setup-security.sh        (Security hardening)
    ├── keystore.properties.example
    └── .gitignore               (Security rules)
```

---

## 🎯 What Each File Does

| File | Purpose | Edit? |
|------|---------|-------|
| `MainActivity.java` | WebView activity that displays your game | Usually not |
| `activity_main.xml` | Screen layout (just a WebView) | Usually not |
| `app/build.gradle` | Build config, versions, signing | Yes (for version updates) |
| `AndroidManifest.xml` | App permissions, name, orientation | As needed |
| `index.html` | Your game (copied to APK) | Yes (when updating game) |
| `gradle/wrapper/gradle-wrapper.properties` | Gradle 8.1.1 config | Don't change |
| `keystore.jks` | Signing key (create yourself) | Keep safe! |

---

## 🔄 Build Commands

```bash
cd android/

# Test build (debug APK)
./gradlew assembleDebug
# → app/build/outputs/apk/debug/app-debug.apk (≈5 MB)

# Release for Google Play (AAB - RECOMMENDED)
./gradlew bundleRelease
# → app/build/outputs/bundle/release/app-release.aab (≈3 MB)

# Clean rebuild
./gradlew clean bundleRelease

# Check build
./gradlew tasks
```

---

## ⚙️ Configuration

**Java & Gradle:**
- Java 17 ✅
- Gradle 8.1.1 ✅
- Set in: `gradle.properties` & `app/build.gradle`

**Android:**
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Package: `com.pollitoproductions.zenpoparcade`
- Set in: `AndroidManifest.xml` & `app/build.gradle`

**App Version:**
```gradle
// In app/build.gradle
versionCode 1       // Increment for each Play Store release
versionName "1.0.0" // User-visible version
```

---

## 🎨 Add Your Icon

Your icon (the vibrant circular design) needs to be PNG files at 5 different sizes:

**Fastest way:**
```bash
cd android/
./generate_icons.sh /path/to/icon.png
```

**Manual way:**
Save PNG files at these exact locations:
- `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48×48)
- `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72×72)
- `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96×96)
- `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144×144)
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192×192)

→ See `android/ICON_SETUP.md` for detailed instructions & tools

---

## 🔐 Signing Setup

**Create keystore once (per project):**
```bash
keytool -genkey -v -keystore keystore.jks \
  -keyalg RSA -keysize 2048 -validity 10000 -alias android

# Answer prompts (remember your passwords!)
# Move keystore.jks to android/ folder
```

**Configure in `app/build.gradle`:**
```gradle
signingConfigs {
    release {
        storeFile file("keystore.jks")
        storePassword System.getenv("KEYSTORE_PASSWORD")
        keyAlias System.getenv("KEY_ALIAS")
        keyPassword System.getenv("KEY_PASSWORD")
    }
}
```

**Or create `android/keystore.properties`:**
```properties
storeFile=keystore.jks
storePassword=YOUR_PASSWORD
keyAlias=android
keyPassword=YOUR_PASSWORD
```

⚠️ **BACKUP YOUR KEYSTORE!** You'll need it for app updates.

---

## 📦 Upload to Google Play

1. Build: `./gradlew bundleRelease`
2. Go to [Google Play Console](https://play.google.com/console)
3. Create new app
4. Upload `app/build/outputs/bundle/release/app-release.aab`
5. Add:
   - App name: "Zen Pop Arcade"
   - Description
   - Screenshots (1080×1920)
   - Icon (512×512 PNG)
   - Content rating
6. Submit for review

---

## ✅ Verification Checklist

- [ ] Java 17 installed (`java -version`)
- [ ] Icons in all 5 mipmap-* folders
- [ ] Icons are PNG files (not JPEG)
- [ ] Icons named exactly `ic_launcher.png`
- [ ] `index.html` exists in `app/src/main/assets/`
- [ ] `keystore.jks` created and in `android/` folder
- [ ] Build completes: `./gradlew bundleRelease`
- [ ] AAB file generated: `app/build/outputs/bundle/release/app-release.aab`

---

## 🆘 Quick Troubleshooting

| Problem | Solution |
|---------|----------|
| `java: command not found` | Install Java 17 or set `$JAVA_HOME` |
| `Gradle downloads fail` | Check internet / set proxy in gradle.properties |
| `icon not showing` | Ensure PNG files in ALL mipmap-* dirs, exact name `ic_launcher.png` |
| `APK crashes on launch` | Check `index.html` exists in assets/, test in emulator first |
| `symbol not found buildConfig` | Run `./gradlew clean bundleRelease` |

---

## 📖 Full Documentation

- **Setup Guide:** [README.md](android/README.md) (10 min read)
- **Quick Ref:** [QUICKSTART.md](android/QUICKSTART.md) (5 min read)
- **Icons:** [ICON_SETUP.md](android/ICON_SETUP.md) (detailed)
- **Project Summary:** [../ANDROID_PROJECT_SUMMARY.md](ANDROID_PROJECT_SUMMARY.md)
- **Build Guide:** [../ANDROID_BUILD.md](ANDROID_BUILD.md)

---

## 🎯 Next 5 Minutes

```bash
# 1. Add your icons
cd android/
./generate_icons.sh ~/Downloads/zen-pop-icon.png

# 2. Create signing key (answer prompts)
keytool -genkey -v -keystore keystore.jks -keyalg RSA \
  -keysize 2048 -validity 10000 -alias android

# 3. Build
./gradlew bundleRelease

# 4. Check output
ls -lh app/build/outputs/bundle/release/

# 5. You're done! Upload to Google Play Console
```

---

## 🎓 Technologies Used

- **Language:** Java 17
- **Build System:** Gradle 8.1.1
- **Android:** API 34 (Android 14)
- **Package Format:** AAB (App Bundle)
- **Minification:** ProGuard enabled
- **WebView:** For HTML game rendering

---

## 📊 Expected Sizes

| Build Type | Size | Location |
|-----------|------|----------|
| Debug APK | 5-8 MB | `app/build/outputs/apk/debug/` |
| Release AAB | 2-4 MB | `app/build/outputs/bundle/release/` |
| Release APK | 4-6 MB | `app/build/outputs/apk/release/` |

AAB files are measured by device - actual download is 1-3 MB per device.

---

## 💡 Pro Tips

✨ **Faster builds:**
```bash
export ORG_GRADLE_PROJECT_gradle_parallel=true
export ORG_GRADLE_PROJECT_gradle_workers_max=8
```

🚀 **Skip tests (if any):**
```bash
./gradlew bundleRelease -x test
```

🔍 **Debug build:**
```bash
./gradlew bundleDebug
```

📱 **Install on device:**
```bash
./gradlew installDebug
```

---

## 🎉 You're All Set!

Your Android project is complete and ready to build. Just add icons and create a signing key, then you can build and upload to Google Play.

**Questions?** Check the documentation files in the `android/` folder.

---

*Project: Zen Pop Arcade*  
*Created: 2026-04-07*  
*Status: ✅ Production Ready*
