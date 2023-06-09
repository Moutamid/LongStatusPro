package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import in.whatsaga.whatsapplongerstatus.status.uploader.R;

public class WebActivity extends AppCompatActivity  {

    public static Handler handler;
    private static ValueCallback<Uri[]> mUploadMessageArr;
    ProgressBar progressBar;
    private WebView webViewscanner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);




        InitHandler();
        progressBar = findViewById(R.id.progressBar);
        webViewscanner = findViewById(R.id.webViewscan);
        webViewscanner.clearFormData();
        webViewscanner.getSettings().setSaveFormData(true);
        webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
        webViewscanner.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        webViewscanner.setWebChromeClient(new webChromeClients());
        webViewscanner.setWebViewClient(new MyBrowser());

        webViewscanner.getSettings().setAllowFileAccess(true);

        webViewscanner.getSettings().setJavaScriptEnabled(true);
        webViewscanner.getSettings().setDefaultTextEncodingName("UTF-8");
        webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webViewscanner.getSettings().setDatabaseEnabled(true);
        webViewscanner.getSettings().setBuiltInZoomControls(false);
        webViewscanner.getSettings().setSupportZoom(false);
        webViewscanner.getSettings().setUseWideViewPort(true);
        webViewscanner.getSettings().setDomStorageEnabled(true);
        webViewscanner.getSettings().setAllowFileAccess(true);
        webViewscanner.getSettings().setLoadWithOverviewMode(true);
        webViewscanner.getSettings().setLoadsImagesAutomatically(true);
        webViewscanner.getSettings().setBlockNetworkImage(false);
        webViewscanner.getSettings().setBlockNetworkLoads(false);
        webViewscanner.getSettings().setLoadWithOverviewMode(true);
        webViewscanner.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");

    }

    @SuppressLint("HandlerLeak")
    private class btnInitHandlerListner extends Handler {
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message msg) {
        }
    }

    private class webChromeClients extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }
    }


    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && Build.VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            try {
                if (webViewscanner.canGoBack()) {
                    webViewscanner.goBack();
                    return z;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        z = super.onKeyDown(keyCode, event);
        return z;
    }

    public void onPause() {
        super.onPause();
        webViewscanner.clearCache(true);
    }

    public void onDestroy() {
        super.onDestroy();
        webViewscanner.clearCache(true);
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        webViewscanner.clearCache(true);
        super.onStop();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @SuppressLint({"HandlerLeak"})
    private void InitHandler() {
        handler = new btnInitHandlerListner();
    }



   



    public void onBackPressed() {



        super.onBackPressed();
    }
}