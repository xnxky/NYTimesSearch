package com.xxy.nytimessearch.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxy.nytimessearch.Listener.OnItemClickListener;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.Object.ArticleWithThumbnail;
import com.xxy.nytimessearch.Object.ArticleWithoutThumbnail;
import com.xxy.nytimessearch.R;
import com.xxy.nytimessearch.ViewHolder.BaseViewHolder;
import com.xxy.nytimessearch.ViewHolder.ImageTextViewHolder;
import com.xxy.nytimessearch.ViewHolder.TextViewHolder;

import java.util.List;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public class ArticleRecylerViewAdapter
    extends RecyclerView.Adapter<BaseViewHolder> {

  private List<Article> mArticles;
  private OnItemClickListener mListener;
  private final static int IMAGE_ARTICLE = 0, TEXT_ARTICLE = 1;

  public ArticleRecylerViewAdapter(
      List<Article> articles, OnItemClickListener listener) {
    mArticles = articles;
    mListener = listener;
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    switch (viewType) {
      case IMAGE_ARTICLE:
        View imageArticleView = inflater.inflate(R.layout.item_image_text_layout, parent, false);
        return new ImageTextViewHolder(imageArticleView, mListener, parent.getContext());
      default:
        View textArticleView = inflater.inflate(R.layout.item_text_layout, parent, false);
        return new TextViewHolder(textArticleView, mListener);
    }
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    Article article = mArticles.get(position);
    holder.bindView(article);
  }

  @Override
  public int getItemCount() {
    return mArticles.size();
  }

  @Override
  public int getItemViewType(int position) {
    if(mArticles.get(position) instanceof ArticleWithThumbnail) {
      return IMAGE_ARTICLE;
    } else if (mArticles.get(position) instanceof ArticleWithoutThumbnail) {
      return TEXT_ARTICLE;
    }
    return -1;
  }
}

