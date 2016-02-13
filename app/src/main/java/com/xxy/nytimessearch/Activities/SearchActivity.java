package com.xxy.nytimessearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xxy.nytimessearch.Adapter.ArticleArrayAdapter;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.Object.Settings;
import com.xxy.nytimessearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

  @Bind(R.id.etQuery) EditText etQuery;
  @Bind(R.id.gvResults) GridView gvResults;

  ArrayList<Article> articles;
  ArticleArrayAdapter adapter;

  private Settings settings;
  private final int REQUEST_CODE = 20;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    //TODO: find why this can not bind
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    articles = new ArrayList<>();
    settings = Settings.defaultSettings;
    setupViews();
  }

  public void setupViews() {
    adapter = new ArticleArrayAdapter(this, articles);
    gvResults.setAdapter(adapter);
    gvResults.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
            Article article = articles.get(position);
            intent.putExtra("article", article);
            startActivity(intent);
          }
        }
    );
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    System.out.println("test");
    if(requestCode==REQUEST_CODE && resultCode==RESULT_OK) {
      Settings newSettings = (Settings)data.getSerializableExtra("settings");
      settings.update(newSettings);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_search, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch(item.getItemId()) {
      case R.id.action_settings:
        modifySettings();
        return true;
      default:
        return super.onOptionsItemSelected(item);

    }
  }

  public void modifySettings() {
    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
    intent.putExtra("settings", settings);
    startActivityForResult(intent, REQUEST_CODE);
  }

  @OnClick(R.id.btnSearch)
  public void onArticleSearch(View view) {
    String query = etQuery.getText().toString();
    AsyncHttpClient client = new AsyncHttpClient();
    String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    RequestParams params = new RequestParams();
    params.put("api-key", "ba4c5e5aa246172d86da625fe8e35a88:18:74365063");
    params.put("page", 0);
    params.put("q", query);
    client.get(url, params, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        JSONArray articleJsonResult = null;
        try {
          articleJsonResult = response.getJSONObject("response").getJSONArray("docs");
          adapter.addAll(Article.fromJsonArray(articleJsonResult));
        } catch (JSONException e) {

        }
      }
    });
  }
}
