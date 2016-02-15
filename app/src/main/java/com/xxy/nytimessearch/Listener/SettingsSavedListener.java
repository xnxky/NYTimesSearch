package com.xxy.nytimessearch.Listener;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.common.collect.ImmutableList;
import com.xxy.nytimessearch.Object.Settings;
import com.xxy.nytimessearch.R;

import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiangyang_xiao on 2/13/16.
 */

public class SettingsSavedListener {
  @Bind(R.id.dpBegin)
  DatePicker dpBeginDate;
  @Bind(R.id.dpEnd)
  DatePicker dpEndDate;
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

  private boolean isDateValid(
      int[] begin, int[] end
  ) {
    //year
    if (begin[0] > end[0]) return false;
    if (begin[0] < end[0]) return true;
    //month
    if (begin[1] > end[1]) return false;
    if (begin[1] < end[1]) return true;
    //day
    return begin[2] <= end[2];
  }

  @OnClick(R.id.btnSave)
  public void saveSettings(View view) {
    if (!isDateValid(
        new int[]{dpBeginDate.getYear(), dpBeginDate.getMonth(), dpBeginDate.getDayOfMonth()},
        new int[]{dpEndDate.getYear(), dpEndDate.getMonth(), dpEndDate.getDayOfMonth()})) {
      Toast.makeText(
          dialog.getContext(),
          "Begin Date is later than End Date", Toast.LENGTH_SHORT).show();
      return;
    }

    if (!isDateValid(
        new int[]{dpBeginDate.getYear(), dpBeginDate.getMonth(), dpBeginDate.getDayOfMonth()},
        Settings.getDate(new LocalDate().toString(Settings.DATE_FORMAT)))) {
      Toast.makeText(
          dialog.getContext(),
          "Begin Date is bigger than today's Date", Toast.LENGTH_SHORT).show();
      return;
    }

    settings.setStartDate(
        Settings.getDateString(
            dpBeginDate.getYear(),
            dpBeginDate.getMonth(),
            dpBeginDate.getDayOfMonth()
        )
    );

    settings.setEndDate(
        Settings.getDateString(
            dpEndDate.getYear(),
            dpEndDate.getMonth(),
            dpEndDate.getDayOfMonth()
        )
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
    initializeView();
  }

  public void setDialog(Dialog dialog) {
    this.dialog = dialog;
  }

  public void initializeView() {
    int[] beginDate = Settings.getDate(
        settings.getStartDate()
    );
    dpBeginDate.init(
        beginDate[0],
        beginDate[1],
        beginDate[2],
        null
    );
    int[] endDate = Settings.getDate(
        settings.getEndDate()
    );
    dpEndDate.init(
        endDate[0],
        endDate[1],
        endDate[2],
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
