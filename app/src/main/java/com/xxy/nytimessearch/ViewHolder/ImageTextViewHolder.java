package com.xxy.nytimessearch.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xxy.nytimessearch.Listener.OnItemClickListener;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.Object.ArticleWithThumbnail;
import com.xxy.nytimessearch.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public class ImageTextViewHolder extends BaseViewHolder {

  @Bind(R.id.ivImage)
  ImageView ivThumbNail;
  @Bind(R.id.pbLoading)
  ProgressBar pbLoading;
  @Bind(R.id.tvTitle)
  TextView tvTitle;

  Context mContext;

  public ImageTextViewHolder(View itemView, OnItemClickListener listener, Context context) {
    super(itemView, listener);
    mContext = context;
    ButterKnife.bind(this, itemView);
  }

  /*
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
      //ivThumbNail.setHeightRatio(ratio);
      //ivThumbNail.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
  */

  @Override
  public void bindView(Article article) {
    if (!(article instanceof ArticleWithThumbnail)) {
      throw new RuntimeException("bind imageTextViewHolder on a wrong object");
    }

    ivThumbNail.setImageDrawable(null);
    tvTitle.setText(article.getHeadline());
    String thumbNail = ((ArticleWithThumbnail) article).getThumbNail();

    ivThumbNail.setVisibility(View.VISIBLE);
    pbLoading.setVisibility(View.VISIBLE);
    Glide.with(mContext)
        .load(thumbNail)
        .listener(new RequestListener<String, GlideDrawable>() {
          @Override
          public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            pbLoading.setVisibility(View.GONE);
            return false;
          }
        })
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder_error)
            //TODO: how to dynamiclly resize the height
        .centerCrop()
        .into(ivThumbNail); //com.squareup.picasso.Target is for picasso
  }
}
