package com.baloonink.crashthebones;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class PrivacyActivity extends AppCompatActivity {

    private WebView webView;

    private String param = "";

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.activity_privacy);
        
        Intent intent = getIntent();
        boolean privacy = intent.getBooleanExtra("privacy", false);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new ProWebViewClient());

        if (privacy) {
            webView.loadUrl("file:///android_asset/privacy.html");
        } else {
            if (isNetworkConnected()) {
                webView.loadUrl(param);
            } else {
                webView.loadUrl("file:///android_asset/error.html");
            }
        }
    }

    public boolean isNetworkConnected() {
        if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap twice Back for Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private class ProWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return handleUri(view, url);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return handleUri(view, request.getUrl().toString());
        }

        private boolean handleUri(WebView view, final String url) {
            if (url.contains("reload://")) {
                Intent intent = new Intent(getApplicationContext(), PrivacyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                if (getApplicationContext() instanceof Activity) {
                    ((Activity) getApplicationContext()).finish();
                }
                Runtime.getRuntime().exit(0);
                return true;
            } else {
                return false;
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
            super.onReceivedError(view, req, err);
            webView.loadUrl("file:///android_asset/error.html");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            webView.loadUrl("file:///android_asset/error.html");
        }
    }
}