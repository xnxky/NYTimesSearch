package com.xxy.nytimessearch.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xxy.nytimessearch.Object.Article;
import com.xxy.nytimessearch.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 2/10/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

  public ArticleArrayAdapter(Context context, List<Article> articles) {
    super(context, android.R.layout.simple_list_item_1, articles);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    Article article = getItem(position);
    if(convertView == null) {
      convertView = LayoutInflater
          .from(getContext())
          .inflate(R.layout.item_article_layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder)convertView.getTag();
    }

    viewHolder.imageView.setImageResource(0);
    viewHolder.tvTitle.setText(article.getHeadline());

    String thumbNail = article.getThumbNail();

    if(!TextUtils.isEmpty(thumbNail)) {
      Picasso
          .with(getContext())
          .load(thumbNail)
          .into(viewHolder.imageView);
    }

    return convertView;

  }

  class ViewHolder {
    @Bind(R.id.ivImage) ImageView imageView;
    @Bind(R.id.tvTitle) TextView tvTitle;
    public ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }

}
