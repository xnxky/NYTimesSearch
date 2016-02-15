package com.xxy.nytimessearch.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.xxy.nytimessearch.Listener.OnItemClickListener;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.Object.ArticleWithoutThumbnail;
import com.xxy.nytimessearch.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public class TextViewHolder extends BaseViewHolder {

  public TextView getTvTitle() {
    return tvTitle;
  }

  @Bind(R.id.tvTitle) TextView tvTitle;

  public TextViewHolder(View itemView, OnItemClickListener listener) {
    super(itemView, listener);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bindView(Article article) {
    if(!(article instanceof ArticleWithoutThumbnail)) {
      throw new RuntimeException("TextViewHolder binding on wrong article type");
    }
    tvTitle.setText(article.getHeadline());
  }
}
