package com.xxy.nytimessearch.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DynamicHeightImageView extends ImageView {

  private double mHeightRatio;

  public DynamicHeightImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DynamicHeightImageView(Context context) {
    super(context);
  }

  public double getHeightRatio() {
    return mHeightRatio;
  }

  public void setHeightRatio(double ratio) {
    if (ratio != mHeightRatio) {
      mHeightRatio = ratio;
      requestLayout();
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mHeightRatio > 0.0) {
      // set the image views size
      int width = MeasureSpec.getSize(widthMeasureSpec);
      int height = (int) (width * mHeightRatio);
      setMeasuredDimension(width, height);
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}
