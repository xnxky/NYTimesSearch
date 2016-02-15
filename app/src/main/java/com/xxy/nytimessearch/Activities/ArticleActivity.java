package com.xxy.nytimessearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.R;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.wvArticle)
  WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    Article article = Parcels.unwrap(
        getIntent().getParcelableExtra("article"));
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    webView.loadUrl(article.getWebUrl());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_article, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_share:
        actionShare();
        return true;
      case android.R.id.home:
        this.finish();
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void actionShare() {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    WebView webView = (WebView) findViewById(R.id.wvArticle);
    shareIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
    startActivity(shareIntent);
  }
}
