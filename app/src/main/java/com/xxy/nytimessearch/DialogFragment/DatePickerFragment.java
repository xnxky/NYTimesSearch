package com.xxy.nytimessearch.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.xxy.nytimessearch.Listener.DatePickerListener;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */

public class DatePickerFragment extends DialogFragment {

  private DatePickerListener mListener;

  public void setListener(
      DatePickerListener listener
  ) {
    mListener = listener;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Create a new instance of TimePickerDialog and return it
    int[] date = mListener.getDate();
    return new DatePickerDialog(
        getActivity(),
        mListener,
        date[0],
        date[1],
        date[2]);
  }
}
