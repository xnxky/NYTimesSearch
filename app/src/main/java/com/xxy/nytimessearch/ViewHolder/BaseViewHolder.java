package com.xxy.nytimessearch.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xxy.nytimessearch.Listener.OnItemClickListener;
import com.xxy.nytimessearch.Object.Article;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

  OnItemClickListener mListener;

  public BaseViewHolder(View itemView, OnItemClickListener listener) {
    super(itemView);
    mListener = listener;
    itemView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (mListener != null) {
              mListener.onItemClick(v, getLayoutPosition());
            }
          }
        }
    );
  }

  public abstract void bindView(Article article);
}
