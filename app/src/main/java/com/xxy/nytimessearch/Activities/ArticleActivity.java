package com.xxy.nytimessearch.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.wvArticle) WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    Article article = (Article)getIntent().getSerializableExtra("article");
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    webView.loadUrl(article.getWebUrl());
  }

}
