package com.cybulski.nazarko.tv24reader.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cybulski.nazarko.tv24reader.R;

import butterknife.Bind;

public class WebViewActivity extends AbstractBaseActivity {

  public  final static String  EXTRA_URL = "extra:url";
  @Bind(R.id.webView)
  WebView webview;

  String  url;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    webview.setWebViewClient(new WebViewClient());
    webview.getSettings().setJavaScriptEnabled(true);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String value = extras.getString(EXTRA_URL);
      webview.loadUrl(value);
    }

  }

}
