package com.xxy.nytimessearch.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xxy.nytimessearch.Adapter.ArticleRecylerViewAdapter;
import com.xxy.nytimessearch.DialogFragment.SettingsDialogFragment;
import com.xxy.nytimessearch.Listener.EndlessRecyclerViewScrollListener;
import com.xxy.nytimessearch.Listener.OnItemClickListener;
import com.xxy.nytimessearch.Listener.SettingsSavedListener;
import com.xxy.nytimessearch.Model.NYTimeArticle;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.Object.Settings;
import com.xxy.nytimessearch.R;

import org.parceler.Parcels;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class SearchActivity extends AppCompatActivity {

  private static final String url =
      "http://api.nytimes.com/svc/search/v2/articlesearch.json";
  private static final AsyncHttpClient client = new AsyncHttpClient();
  @Bind(R.id.rvResults)
  RecyclerView rvResults;
  ArrayList<Article> mArticles;
  ArticleRecylerViewAdapter rcAdapter;
  StaggeredGridLayoutManager articleLayoutManager;
  private Settings settings;
  private RequestParams params;
  private Gson gson = new GsonBuilder().create();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    //TODO: find why this can not bind
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    mArticles = new ArrayList<>();
    settings = new Settings();
    setupViews();
  }

  public void setupViews() {
    rvResults.setHasFixedSize(true);
    rcAdapter = new ArticleRecylerViewAdapter(mArticles,
        new OnItemClickListener() {
          @Override
          public void onItemClick(View itemView, int position) {
            Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
            Article article = mArticles.get(position);
            intent.putExtra("article", Parcels.wrap(article));
            startActivity(intent);
          }
        }
    );
    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rcAdapter);
    alphaAdapter.setDuration(1000);
    alphaAdapter.setInterpolator(new OvershootInterpolator());
    alphaAdapter.setFirstOnly(false);
    rvResults.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
    articleLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    rvResults.setLayoutManager(articleLayoutManager);
    /*
    //TODO: get a better decoration
    RecyclerView.ItemDecoration articleDecoration = new
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
    rvResults.addItemDecoration(articleDecoration);
    */

    rvResults.addOnScrollListener(
        new EndlessRecyclerViewScrollListener(articleLayoutManager) {
          @Override
          public void onLoadMore(int page, int totalItemsCount) {
            loadMoreArticlesFromApi(page);
          }
        }
    );

    /*
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
    */
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_search, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    final SearchView searchView =
        (SearchView) MenuItemCompat.getActionView(searchItem);
    searchView.setOnQueryTextListener(
        new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            searchArticle(query);
            searchView.clearFocus();
            return true;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            return false;
          }
        }
    );

    if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
      try {
        Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
        m.setAccessible(true);
        m.invoke(menu, true);
      } catch (Exception e) {
      }
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
      case R.id.action_settings:
        modifySettings();
        return true;
      default:
        return super.onOptionsItemSelected(item);

    }
  }

  public void modifySettings() {
    SettingsSavedListener listener =
        new SettingsSavedListener(settings, getApplicationContext());
    SettingsDialogFragment dialogFragment =
        SettingsDialogFragment.newInstance(listener);
    FragmentManager fm = getSupportFragmentManager();
    dialogFragment.show(fm, "Settings");
  }

  public void searchArticle(String query) {
    for (int idx = mArticles.size() - 1; idx >= 0; idx--) {
      mArticles.remove(idx);
      rcAdapter.notifyItemRemoved(idx);
    }
    params = new RequestParams();
    params.put("api-key", "ba4c5e5aa246172d86da625fe8e35a88:18:74365063");
    params.put("q", query);
    params.put("begin_date", settings.getStartDate());
    params.put("end_date", settings.getEndDate());
    params.put("sort", settings.getSortOrder());
    if (!settings.getNewsDesk().isEmpty()) {
      params.put("fq", getNewsDeskPara(settings.getNewsDesk()));
    }
    loadMoreArticlesFromApi(0);
  }

  public void loadMoreArticlesFromApi(int page) {
    params.put("page", page);
    if (!isNetworkAvailable()) {
      Toast.makeText(this, "network is not available", Toast.LENGTH_SHORT).show();
    }
    if (!isOnline()) {
      Toast.makeText(this, "internet is not connected", Toast.LENGTH_SHORT).show();
      return;
    }
/*
    client.get(url, params, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        JSONArray articleJsonResult;
        try {
          articleJsonResult = response.getJSONObject("response").getJSONArray("docs");
          int curSize = mArticles.size();
          List<Article> newArticles = Article.fromJsonArray(articleJsonResult);
          for (int idx = 0; idx < newArticles.size(); idx++) {
            mArticles.add(curSize + idx, newArticles.get(idx));
            rcAdapter.notifyItemInserted(curSize + idx);

          }
        } catch (JSONException e) {
          Toast.makeText(
              getApplicationContext(),
              "response format is wrong",
              Toast.LENGTH_SHORT).show();
          Log.e("json parsing exception", e.toString());
        }
      }
      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Toast.makeText(
            getApplicationContext(),
            "fetch articles failed; try again later",
            Toast.LENGTH_SHORT).show();
        Log.e("fetch articles failed",
            String.format("error code %s and response %s", statusCode, responseString),
            throwable);
      }
    });
  */
    client.get(url, params, new TextHttpResponseHandler() {
      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Toast.makeText(
            getApplicationContext(),
            "fetch articles failed; try again later",
            Toast.LENGTH_SHORT).show();
        Log.e("fetch articles failed",
            String.format("error code %s and response %s", statusCode, responseString),
            throwable);
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, String responseString) {
        //articleJsonResult = response.getJSONObject("response").getJSONArray("docs");
        int curSize = mArticles.size();
        NYTimeArticle nyTimeArticle = gson.fromJson(responseString, NYTimeArticle.class);
        List<Article> newArticles = Article.fromNYTimeArticle(nyTimeArticle);
        for (int idx = 0; idx < newArticles.size(); idx++) {
          mArticles.add(curSize + idx, newArticles.get(idx));
          rcAdapter.notifyItemInserted(curSize + idx);

        }
      }
    });
  }

  private String getNewsDeskPara(Set<String> newsDesk) {
    StringBuilder sb = new StringBuilder();
    sb.append("news_desk:(");
    for (String news : newsDesk) {
      sb.append(String.format("\"%s\"", news));
      sb.append(" ");
    }
    sb.replace(sb.length() - 1, sb.length() - 1, ")");
    return sb.toString();
  }

  private Boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
  }

  public boolean isOnline() {
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return false;
  }
}
