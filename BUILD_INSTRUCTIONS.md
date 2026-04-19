# Zen Pop Arcade - Android Build Instructions

## 📦 Download Your Project

Your complete Android project is ready to download as:

- **`zen-pop-arcade-android.zip`** (58 KB) ← **Recommended** (no build cache)
- **`zen-pop-arcade-android.tar.gz`** (111 KB) (includes build cache)

---

## 🔧 Building the App (On Your Local Machine)

### Prerequisites

Before building, ensure you have installed:

1. **Java Development Kit (JDK) 17+**
   - Download: https://adoptium.net/ or https://www.oracle.com/java/
   - Verify: `java -version`

2. **Android SDK**
   - Download Android Studio: https://developer.android.com/studio
   - Install Android SDK API 34 (target)
   - Install Android SDK API 24+ (minimum)

3. **Gradle 8.1.1** (included in project via wrapper)

### Step 1: Extract the Project

```bash
# If you downloaded .zip:
unzip zen-pop-arcade-android.zip
cd android

# If you downloaded .tar.gz:
tar -xzf zen-pop-arcade-android.tar.gz
cd android
```

### Step 2: Create Keystore (For Release Build)

```bash
keytool -genkey -v -keystore app/keystore.jks \
  -keyalg RSA -keysize 2048 -validity 10000 -alias android
```

When prompted:
- **Keystore password:** (remember this!)
- **Key password:** (can be same as keystore password)
- **First and Last Name:** Zen Pop Arcade
- **Organization:** Pollito Productions
- **Anything else:** Press Enter to accept defaults

### Step 3: Set Passwords (For Gradle)

Create a `.env` file or set environment variables:

```bash
export KEYSTORE_PASSWORD=your_password_here
export KEY_ALIAS=android
export KEY_PASSWORD=your_password_here
```

Or create `keystore.properties` in the `android/` directory:

```properties
storeFile=app/keystore.jks
storePassword=your_password_here
keyAlias=android
keyPassword=your_password_here
```

### Step 4: Build the Release AAB

**On Linux/macOS:**
```bash
cd android/
./gradlew bundleRelease
```

**On Windows:**
```cmd
cd android
gradlew.bat bundleRelease
```

### Step 5: Find Your APK/AAB

The build output will be at:

```
android/app/build/outputs/bundle/release/app-release.aab
```

This is your Android App Bundle - ready to upload to Google Play Console!

---

## 📱 Alternative: Build Debug APK for Testing

If you want to test the app on a device first:

```bash
./gradlew assembleDebug
# Output: android/app/build/outputs/apk/debug/app-debug.apk
```

Install on device:
```bash
./gradlew installDebug
```

---

## 🎯 Version Information

✅ **Pre-configured:**
- Gradle: 8.1.1
- Java: 17
- Android Target: API 34
- Min Android: API 24
- App Version: 1.0.0 (versionCode: 1)
- Package: `com.pollitoproductions.zenpoparcade`

✅ **Included:**
- WebView activity for HTML game
- All app icons (5 sizes, vibrant circular design)
- Full-screen game configuration
- Code minification (ProGuard)

---

## 📤 Upload to Google Play Console

1. Go to: https://play.google.com/console
2. Create New App
3. Upload your `app-release.aab` file
4. Set app icon (must be 512×512)
5. Add app description, screenshots, pricing
6. Submit for review

---

## 🆘 Troubleshooting

### "Java not found"
- Install JDK 17+ and ensure it's in your PATH
- Verify: `java -version`

### "Android SDK not found"
- Install Android Studio
- Set `ANDROID_HOME` environment variable:
  - **Linux/macOS:** `export ANDROID_HOME=$HOME/Android/Sdk`
  - **Windows:** Navigate to: `C:\Users\YourName\AppData\Local\Android\Sdk`

###  "Gradle build fails"
- Ensure you're using Java 17+
- Try: `./gradlew clean` then build again
- Check you have API 34 SDK installed in Android Studio

### "Keystore file not found"
- Ensure `app/keystore.jks` exists (created in Step 2)
- Or create new keystore: `keytool -genkey...` (see Step 2)

### "Wrong Android targets"
- Open Android Studio
- Go to SDK Manager
- Install both API 34 (target) and API 24+ (minimum)

---

## 📁 Project Structure

```
android/
├── app/
│   ├── src/main/
│   │   ├── java/MainActivity.java       (WebView activity)
│   │   ├── assets/index.html            (Your game)
│   │   ├── res/mipmap-*/                (App icons, already included)
│   │   └── AndroidManifest.xml
│   ├── build.gradle                     (App config, version 1.0.0)
│   └── keystore.jks                     (To be created by you)
│
├── gradle/wrapper/                      (Gradle 8.1.1)
├── build.gradle                         (Project config)
├── settings.gradle                      (Module config)
├── gradlew & gradlew.bat                (Build scripts)
└── README.md, QUICKSTART.md             (Documentation)
```

---

## ✅ Checklist Before Upload

- [ ] Java 17+ installed
- [ ] Android SDK API 34 installed
- [ ] Keystore created (app/keystore.jks)
- [ ] Build completes: `./gradlew bundleRelease`
- [ ] AAB file generated: `app/build/outputs/bundle/release/app-release.aab`
- [ ] App icon 512×512 PNG prepared
- [ ] App description written
- [ ] Screenshots at least 2 (1080×1920 recommended)
- [ ] Privacy policy URL (if needed)
- [ ] All age ratings selected

---

## 🚀 Ready to Ship!

Your Android project is complete with:
- ✅ Game HTML embedded
- ✅ Icons at all densities
- ✅ Proper version (1.0.0)
- ✅ WebView configuration
- ✅ Signing ready
- ✅ Code minification enabled

Just build locally and upload to Google Play!

---

**Date Created:** April 7, 2026  
**Project:** Zen Pop Arcade  
**Status:** Ready for release
