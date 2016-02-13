package com.xxy.nytimessearch.Object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xiangyang_xiao on 2/10/16.
 */
public class Article implements Serializable{
  public String getHeadline() {
    return headline;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public String getThumbNail() {
    return thumbNail;
  }

  String webUrl;
  String headline;
  String thumbNail;

  public Article(JSONObject jsonObject) {
    try {
      webUrl = jsonObject.getString("web_url");
      headline = jsonObject.getJSONObject("headline").getString("main");

      JSONArray multimedia = jsonObject.getJSONArray("multimedia");
      if(multimedia.length() > 0) {
        JSONObject multimediaJson = multimedia.getJSONObject(0);
        thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
      } else {
        thumbNail = "";
      }
    } catch(JSONException e) {

    }
  }

  public static ArrayList<Article> fromJsonArray(JSONArray jsonArray) {
    ArrayList<Article> results = new ArrayList<>();

    for(int x=0; x<jsonArray.length(); x++) {
      try {
        results.add(new Article(jsonArray.getJSONObject(x)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return results;
  }
}
