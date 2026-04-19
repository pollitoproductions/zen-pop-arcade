# Icon Setup Instructions

## Overview

The Zen Pop Arcade app requires launcher icons at multiple resolutions for different device densities.

## Directory Structure

```
app/src/main/res/
├── mipmap-mdpi/      (48×48 - Mobile DPI)
├── mipmap-hdpi/      (72×72 - High DPI)
├── mipmap-xhdpi/     (96×96 - Extra High DPI)
├── mipmap-xxhdpi/    (144×144 - Extra Extra High DPI)
├── mipmap-xxxhdpi/   (192×192 - Extra Extra Extra High DPI)
└── mipmap-anydpi-v33/ (adaptive icons - optional for Android 13+)
```

## Requirements

- **Format:** PNG (transparent background recommended)
- **Size:** Full icon fill (no padding/white space around the icon)
- **Content:** Icon should be centered at 192×192px
- **Safe Zone:** Keep important content within 161×161px circle at 192×192px

## Icon Sizes

| Density | Size | DPI | Typical Devices |
|---------|------|-----|-----------------|
| ldpi | 36×36 | 120 | Old devices (rare) |
| mdpi | 48×48 | 160 | Development reference |
| hdpi | 72×72 | 240 | Low-end phones |
| xhdpi | 96×96 | 320 | Mid-range phones |
| xxhdpi | 144×144 | 480 | High-end phones |
| xxxhdpi | 192×192 | 640 | Extra large/tablets |

## Generation Methods

### Method 1: Automated Script (Linux/macOS)

```bash
cd android
./generate_icons.sh /path/to/your/icon.png
```

### Method 2: Automated Script (Windows)

```cmd
cd android
generate_icons.bat C:\path\to\your\icon.png
```

### Method 3: Android Asset Studio (Online)

1. Visit [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/)
2. Select "Icon Generator" → "Launcher Icons"
3. Upload your icon
4. Configure:
   - Trim unused space: OFF (your icon should fill the entire space)
   - Padding: 0%
   - Final sizes: Select all
5. Download the ZIP
6. Extract to `android/app/src/main/res/`

### Method 4: ImageMagick (Manual)

```bash
# Linux/macOS
convert input.png -resize 48x48! app/src/main/res/mipmap-mdpi/ic_launcher.png
convert input.png -resize 72x72! app/src/main/res/mipmap-hdpi/ic_launcher.png
convert input.png -resize 96x96! app/src/main/res/mipmap-xhdpi/ic_launcher.png
convert input.png -resize 144x144! app/src/main/res/mipmap-xxhdpi/ic_launcher.png
convert input.png -resize 192x192! app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
```

### Method 5: GraphicsMagick (Alternative)

```bash
gm convert input.png -resize 48x48! app/src/main/res/mipmap-mdpi/ic_launcher.png
# ... repeat for other sizes
```

### Method 6: GIMP (GUI)

1. Open your icon in GIMP
2. Set canvas to target size (e.g., 192×192)
3. Ensure icon fills the entire canvas
4. Scale and export for each density:
   - Image → Scale Image → (target size)
   - File → Export As → `ic_launcher.png`

## Adaptive Icons (Android 8.0+)

For modern Android versions, create adaptive icons:

**Directory:** `app/src/main/res/mipmap-anydpi-v33/`

**File:** `ic_launcher.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:color="#1a1a1a"/>
    <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
</adaptive-icon>
```

Also create rounded icon variants in respective mipmap folders.

## Verification

After placing icons:

1. **Check files exist:**
   ```bash
   ls -la app/src/main/res/mipmap-*/ic_launcher.png
   ```

2. **Build and test:**
   ```bash
   ./gradlew clean bundleDebug
   ```

3. **Visual inspection:**
   - Build APK and install on emulator/device
   - Launch app and check home screen icon
   - Try different device sizes

## Troubleshooting

### Icon appears blurry
- Ensure PNG files aren't re-encoded as JPG
- Use PNG-8 or PNG-32 format
- Zoom in and check edge clarity

### Icon appears too small
- Icon has too much padding
- Regenerate without padding
- Ensure icon fills the entire canvas

### Icon colors look wrong
- Check PNG encoding
- Consider color space (sRGB recommended)
- Test on multiple devices

### Only one icon showing
- Ensure all density folders have `ic_launcher.png`
- Check file names exactly match (case-sensitive on Linux)

## Best Practices

✓ **Do:**
- Create icons at highest resolution (xxxhdpi/192×192) first
- Scale down for lower densities
- Use solid colors or complex patterns filling entire space
- Test on multiple device screen sizes
- Keep icon simple and recognizable at small sizes
- Use safe zone (161×161px circle) for critical content

✗ **Don't:**
- Leave padding/white space around icon
- Use transparency as a design element (solid background recommended)
- Make icon too complex (hard to see at small sizes)
- Use low-quality images
- Change icon mid-release (consistency matters for recognition)

## Icon Design Tips

For the provided circular gradient icon with paint brush:
1. Ensure the entire 192×192px canvas is used
2. The circular gradient should touch/fill all edges
3. Paint brush should be appropriately sized within the design
4. Colors should remain vibrant when scaled down

## Testing

### Emulator Testing
```bash
./gradlew installDebug
# Check launcher - look for your icon
```

### Device Testing
```bash
./gradlew bundleDebug
# Transfer APK to device and install
```

### Icon Sizes Verification
Use Android Studio's Resource Manager:
1. Right-click `res/` folder
2. Select "Open Resource Manager"
3. Verify icons display in all densities

---

For more details, see [Android Developer - App Icons](https://developer.android.com/guide/topics/ui/app-icons)
