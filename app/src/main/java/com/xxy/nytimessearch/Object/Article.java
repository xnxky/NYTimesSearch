package com.xxy.nytimessearch.Object;

import com.xxy.nytimessearch.Model.Doc;
import com.xxy.nytimessearch.Model.Multimedium;
import com.xxy.nytimessearch.Model.NYTimeArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyang_xiao on 2/10/16.
 */
public class Article {

  String webUrl;
  String headline;

  public Article() {
  }

  public Article(String webUrl, String headline) {
    this.webUrl = webUrl;
    this.headline = headline;
  }

  public static Article getInstance(JSONObject jsonObject) {
    try {
      String webUrl = jsonObject.getString("web_url");
      String headline = jsonObject.getJSONObject("headline").getString("main");

      JSONArray multimedia = jsonObject.getJSONArray("multimedia");
      if (multimedia.length() > 0) {
        JSONObject multimediaJson = multimedia.getJSONObject(0);
        String thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
        return new ArticleWithThumbnail(webUrl, headline, thumbNail);
      } else {
        return new ArticleWithoutThumbnail(webUrl, headline);
      }
    } catch (JSONException e) {
    }
    return null;
  }

  public static Article getInstance(Doc doc) {
    String webUrl = doc.getWebUrl();
    String headline = doc.getHeadline().getMain();

    List<Multimedium> multimediaList = doc.getMultimedia();
    if (multimediaList.size() > 0) {
      Multimedium multimedium = multimediaList.get(0);
      String thumbNail = "http://www.nytimes.com/" + multimedium.getUrl();
      return new ArticleWithThumbnail(webUrl, headline, thumbNail);
    } else {
      return new ArticleWithoutThumbnail(webUrl, headline);
    }
  }

  public static ArrayList<Article> fromNYTimeArticle(
      NYTimeArticle nyTimeArticle) {

    ArrayList<Article> results = new ArrayList<>();

    List<Doc> docs = nyTimeArticle.getResponse().getDocs();
    for (Doc doc : docs) {
      results.add(Article.getInstance(doc));
    }
    return results;
  }

  public static ArrayList<Article> fromJsonArray(JSONArray jsonArray) {
    ArrayList<Article> results = new ArrayList<>();

    for (int x = 0; x < jsonArray.length(); x++) {
      try {
        Article newArticle = Article.getInstance(jsonArray.getJSONObject(x));
        if (newArticle != null) {
          results.add(newArticle);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return results;
  }

  public String getHeadline() {
    return headline;
  }

  public String getWebUrl() {
    return webUrl;
  }
}
