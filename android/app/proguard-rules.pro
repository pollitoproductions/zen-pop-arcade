# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# signingConfig { ... } block in the build gradle file.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class com.pollitoproductions.zenpoparcade.** {
    public *;
}

# Keep JavaScript interface methods for WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# AdMob
-keep class com.google.android.gms.ads.** { *; }

# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

# WebView
-keepclassmembers class * {
    public void onPageFinished(android.webkit.WebView, java.lang.String);
    public boolean shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String);
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Android system classes
-keep class android.** { *; }
-keep interface android.** { *; }

# AppCompat
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
