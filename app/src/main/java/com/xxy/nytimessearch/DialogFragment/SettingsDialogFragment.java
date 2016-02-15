package com.xxy.nytimessearch.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.xxy.nytimessearch.Listener.SettingsSavedListener;
import com.xxy.nytimessearch.R;

/**
 * Created by xiangyang_xiao on 2/13/16.
 */
public class SettingsDialogFragment extends DialogFragment {

  private SettingsSavedListener listener;

  public SettingsDialogFragment() {
  }

  public static SettingsDialogFragment newInstance(
      SettingsSavedListener listener
  ) {
    SettingsDialogFragment dialogFragment =
        new SettingsDialogFragment();
    dialogFragment.listener = listener;
    return dialogFragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final View view = LayoutInflater.from(getActivity())
        .inflate(R.layout.content_settings, null);
    Dialog dialog = new AlertDialog
        .Builder(getActivity())
        .setView(view)
        .create();
    listener.setView(view);
    listener.setDialog(dialog);
    return dialog;
  }
}
