package com.example.minitown.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.minitown.R;

public class LoginAdressActivity extends AppCompatActivity {

    private WebView adressWebview;
    private TextView tv_address;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_adress);
        tv_address = (TextView) findViewById(R.id.tv_address);

        init_webView();

        handler = new Handler();


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init_webView() {
        adressWebview = (WebView) findViewById(R.id.adress_webview) ;
        adressWebview.getSettings().setJavaScriptEnabled(true);
        adressWebview.loadUrl("https://kimbuckgur.github.io/Kakao-Postcode/");
        adressWebview.setWebChromeClient(new WebChromeClient());
        adressWebview.addJavascriptInterface(new AndroidBridge(), "Android");
        adressWebview.setWebViewClient(new WebViewClientClass());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && adressWebview.canGoBack()) {
            adressWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL", url);
            view.loadUrl(url);
            return true;
        }
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tv_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    Intent intent = new Intent(LoginAdressActivity.this, RegisterActivity.class);
                    intent.setAction("sendAction_address");
                    intent.putExtra("address", String.valueOf(tv_address));
                    startActivity(intent);
                    init_webView();
                }
            });
        }
    }
}