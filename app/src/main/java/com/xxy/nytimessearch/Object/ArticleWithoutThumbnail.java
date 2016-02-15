package com.xxy.nytimessearch.Object;

import org.parceler.Parcel;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */

@Parcel
public class ArticleWithoutThumbnail extends Article {

  public ArticleWithoutThumbnail() {
  }

  public ArticleWithoutThumbnail(String webUrl, String headLine) {
    super(webUrl, headLine);
  }
}
