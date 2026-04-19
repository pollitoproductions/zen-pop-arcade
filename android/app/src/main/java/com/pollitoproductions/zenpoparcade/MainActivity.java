package com.pollitoproductions.zenpoparcade;

import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ZenPopArcade";
    // Test rewarded ad unit ID — replace with real ID for production
    private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";

    private WebView webView;
    private RewardedAd rewardedAd;
    private boolean isLoadingAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();

        // Initialize AdMob SDK
        MobileAds.initialize(this, initializationStatus -> {
            Log.d(TAG, "AdMob SDK initialized");
            loadRewardedAd();
        });

        webView = findViewById(R.id.webview);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        // Configure WebView settings
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        settings.setGeolocationDatabasePath(getFilesDir().getPath());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setUserAgentString("Mozilla/5.0 (Linux; Android " + Build.VERSION.RELEASE +
                "; " + Build.MODEL + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Mobile Safari/537.36");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Inject vibration polyfill since WebView doesn't support navigator.vibrate
                view.evaluateJavascript(
                    "navigator.vibrate = function(pattern) {" +
                    "  if (!pattern) return false;" +
                    "  if (typeof pattern === 'number') { Android.vibrate(pattern); }" +
                    "  else if (Array.isArray(pattern)) { Android.vibratePattern(JSON.stringify(pattern)); }" +
                    "  return true;" +
                    "};", null);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://pollitoproductions.github.io/zen-pop-arcade/");
    }

    // --- JavaScript Interface for WebView to Native communication ---
    public class WebAppInterface {
        @JavascriptInterface
        public void showRewardedAd() {
            runOnUiThread(() -> showAd());
        }

        @JavascriptInterface
        public boolean isAdReady() {
            return rewardedAd != null;
        }

        @JavascriptInterface
        public void vibrate(int milliseconds) {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (v == null || !v.hasVibrator()) return;
            // Clamp to a stronger minimum so short pulses are actually felt
            int duration = Math.max(milliseconds, 30);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(duration, 255));
            } else {
                v.vibrate(duration);
            }
        }

        @JavascriptInterface
        public void vibratePattern(String patternJson) {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (v == null || !v.hasVibrator()) return;
            try {
                String[] parts = patternJson.replace("[", "").replace("]", "").split(",");
                long[] pattern = new long[parts.length + 1];
                pattern[0] = 0; // no initial delay
                for (int i = 0; i < parts.length; i++) {
                    pattern[i + 1] = Long.parseLong(parts[i].trim());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1));
                } else {
                    v.vibrate(pattern, -1);
                }
            } catch (Exception e) {
                Log.e(TAG, "vibratePattern error", e);
            }
        }

        @JavascriptInterface
        public int getAppVersion() {
            // Return the app version code (or version name as int if needed)
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                return pInfo.versionCode;
            } catch (Exception e) {
                return 1; // fallback to 1 if error
            }
        }
    }

    // --- Rewarded Ad Methods ---
    private void loadRewardedAd() {
        if (isLoadingAd || rewardedAd != null) return;
        isLoadingAd = true;

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;
                isLoadingAd = false;
                Log.d(TAG, "Rewarded ad loaded");
                notifyWebView("onAdReady", "true");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                rewardedAd = null;
                isLoadingAd = false;
                Log.e(TAG, "Rewarded ad failed: " + loadAdError.getMessage());
                notifyWebView("onAdReady", "false");
            }
        });
    }

    private void showAd() {
        if (rewardedAd == null) {
            notifyWebView("onAdClosed", "false");
            loadRewardedAd();
            return;
        }

        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed");
                rewardedAd = null;
                notifyWebView("onAdClosed", "true");
                loadRewardedAd();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.e(TAG, "Ad failed to show: " + adError.getMessage());
                rewardedAd = null;
                notifyWebView("onAdClosed", "false");
                loadRewardedAd();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showing");
            }
        });

        rewardedAd.show(this, rewardItem -> {
            Log.d(TAG, "Reward earned: " + rewardItem.getAmount() + " " + rewardItem.getType());
            notifyWebView("onAdRewarded", "true");
        });
    }

    private void notifyWebView(String event, String data) {
        runOnUiThread(() -> {
            if (webView != null) {
                webView.evaluateJavascript(
                    "if(typeof " + event + " === 'function') " + event + "('" + data + "')", null);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
        hideSystemUI();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
