package com.xxy.nytimessearch.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.R;
import com.xxy.nytimessearch.Util.DynamicHeightImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public class ArticleRecylerViewAdapter
    extends RecyclerView.Adapter<ArticleRecylerViewAdapter.ViewHolder> {

  public static OnItemClickListener listener;
  Context mContext;
  private List<Article> mArticles;

  public ArticleRecylerViewAdapter(List<Article> articles) {
    mArticles = articles;
  }

  public void setOnItemClickListener(
      OnItemClickListener listener) {
    this.listener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View articleView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_article_layout, parent, false);
    mContext = parent.getContext();
    return new ViewHolder(articleView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.ivThumbNail.setImageDrawable(null);
    Article article = mArticles.get(position);
    holder.tvTitle.setText(article.getHeadline());
    String thumbNail = article.getThumbNail();

    if (!TextUtils.isEmpty(thumbNail)) {
      holder.ivThumbNail.setVisibility(View.VISIBLE);
      holder.pbLoading.setVisibility(View.VISIBLE);
      Picasso.with(mContext)
          .load(thumbNail)
          .placeholder(R.drawable.placeholder)
          .error(R.drawable.placeholder_error)
          .into(holder);
      holder.pbLoading.setVisibility(View.GONE);
    } else {
      holder.ivThumbNail.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return mArticles.size();
  }

  public interface OnItemClickListener {
    void onItemClick(View itemView, int position);
  }

  public static class ViewHolder
      extends RecyclerView.ViewHolder implements Target {
    @Bind(R.id.ivImage) DynamicHeightImageView ivThumbNail;
    @Bind(R.id.pbLoading) ProgressBar pbLoading;
    @Bind(R.id.tvTitle) TextView tvTitle;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View viewItem) {
              if (listener != null) {
                listener.onItemClick(viewItem, getLayoutPosition());
              }
            }
          }
      );
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
      ivThumbNail.setHeightRatio(ratio);
      ivThumbNail.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
  }

}

