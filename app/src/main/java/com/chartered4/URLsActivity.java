package com.chartered4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.chartered4.databinding.ActivityUrlsBinding;
import com.chartered4.retrofit.ApiService;
import com.chartered4.utils.AppConstants;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class URLsActivity extends AppCompatActivity {

    ActivityUrlsBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUrlsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        if (getIntent().hasExtra(AppConstants.IntentConstants.TERMS)) {
            binding.txtTitle.setText(getString(R.string.terms_conditions));
        }

        if (AppUtils.isNetworkConnected(URLsActivity.this)) {
            setUpWebViewDefaults();
        } else {
            AppDialogs.showAlertDialog(getString(R.string.internet_msg), URLsActivity.this);
        }
    }

    public void setUpWebViewDefaults() {

        binding.progressBar.setVisibility(View.VISIBLE);

        WebSettings settings = binding.webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        binding.webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        /*webView.setWebChromeClient(new WebChromeClient() {
        });*/

        binding.webView.setWebChromeClient(new MyChrome());

        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.e("url", url);
                // hide element by class name
//                webView.loadUrl("javascript:(function() { document.getElementsByClassName('heading-two')[0].style.display='none'; })()");
//                webView.loadUrl("javascript:(function() { document.getElementsByClassName('header')[0].style.display='none'; })()");
//                webView.loadUrl("javascript:(function() { document.getElementsByClassName('page-banner-sm bg-dull')[0].style.display='none'; })()");
//                webView.loadUrl("javascript:(function() { document.getElementsByClassName('footer footer-two')[0].style.display='none'; })()");
//                webView.loadUrl("javascript:(function() { document.getElementById('toTop')[0].style.display='none'; })()");

                // hide element by id
//                webView.loadUrl("javascript:(function() { document.getElementById('toTop').style.display='none';})()");

//                webView.loadUrl("javascript:(function() { " +
//                        "document.querySelector('[role=\"small\"]').remove();})()");

                binding.progressBar.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        if (getIntent().hasExtra(AppConstants.IntentConstants.TERMS)) {
            binding.webView.loadUrl(ApiService.TermsAndConditions);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

}
