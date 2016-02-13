package com.xxy.nytimessearch.Listener;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.common.collect.ImmutableList;
import com.xxy.nytimessearch.Object.Settings;
import com.xxy.nytimessearch.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiangyang_xiao on 2/13/16.
 */

public class SettingsSavedListener {
  @Bind(R.id.dpBegin)
  DatePicker dpBeginDate;
  @Bind(R.id.spSortOrder)
  Spinner spSortOrder;
  @Bind(R.id.cbArts)
  CheckBox cbArts;
  @Bind(R.id.cbFashion)
  CheckBox cbFashion;
  @Bind(R.id.cbSports)
  CheckBox cbSports;

  private ArrayAdapter<String> spinnerAdapter;
  private Settings settings;
  private Dialog dialog;

  public SettingsSavedListener(Settings settings, Context context) {
    this.settings = settings;
    spinnerAdapter = new ArrayAdapter<String>(
        context,
        android.R.layout.simple_spinner_item,
        Settings.sortOrderValues);
  }

  @OnClick(R.id.btnSave)
  public void saveSettings(View view) {
    settings.setDate(
        dpBeginDate.getYear(),
        dpBeginDate.getMonth(),
        dpBeginDate.getDayOfMonth()
    );
    settings.setSortOrder(
        Settings.SortOrder.valueOf(
            (String) spSortOrder.getSelectedItem()
        )
    );

    settings.getNewsDesk().clear();
    for (CheckBox ctView :
        ImmutableList.of(cbArts, cbFashion, cbSports)) {
      if (ctView.isChecked()) {
        settings.getNewsDesk().add(ctView.getText().toString());
      }
    }
    dialog.dismiss();
  }

  public void setView(View view) {
    ButterKnife.bind(this, view);
    initializedView();
  }

  public void setDialog(Dialog dialog) {
    this.dialog = dialog;
  }

  public void initializedView() {
    int[] date = settings.getDate();
    dpBeginDate.init(
        date[0],
        date[1],
        date[2],
        null
    );
    spSortOrder.setAdapter(spinnerAdapter);
    spSortOrder.setSelection(
        spinnerAdapter.getPosition(settings.getSortOrder().name()));
    for (CheckBox ctView : ImmutableList.of(cbArts, cbFashion, cbSports)) {
      String newsDeskText = ctView.getText().toString();
      ctView.setChecked(settings.getNewsDesk().contains(newsDeskText));
    }
  }
}
