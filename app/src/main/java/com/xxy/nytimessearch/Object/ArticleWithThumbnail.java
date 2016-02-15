package com.xxy.nytimessearch.Object;

import org.parceler.Parcel;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
@Parcel
public class ArticleWithThumbnail extends Article {
  String thumbNail;

  public ArticleWithThumbnail() {

  }

  public ArticleWithThumbnail(String webUrl, String headLine, String thumbNail) {
    super(webUrl, headLine);
    this.thumbNail = thumbNail;
  }

  public String getThumbNail() {
    return thumbNail;
  }
}
