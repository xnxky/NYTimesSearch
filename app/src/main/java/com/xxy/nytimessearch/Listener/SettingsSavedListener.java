package com.xxy.nytimessearch.Listener;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.ImmutableList;
import com.xxy.nytimessearch.DialogFragment.DatePickerFragment;
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
  @Bind(R.id.tvBegin)
  TextView tvBegin;
  @Bind(R.id.tvEnd)
  TextView tvEnd;
  @Bind(R.id.spSortOrder)
  Spinner spSortOrder;
  @Bind(R.id.cbArts)
  CheckBox cbArts;
  @Bind(R.id.cbFashion)
  CheckBox cbFashion;
  @Bind(R.id.cbSports)
  CheckBox cbSports;

  //private ArrayAdapter<String> spinnerAdapter;
  private Settings settings;
  private Dialog dialog;
  private DatePickerListener datePickerListener;
  private FragmentManager fragmentManager;

  public SettingsSavedListener(Settings settings, FragmentManager fragmentManager) {
    this.settings = settings;
    this.fragmentManager = fragmentManager;
    /*
    spinnerAdapter = new ArrayAdapter<String>(
        context,
        R.layout.item_spinner_view,
        //android.R.layout.simple_spinner_item,
        Settings.sortOrderValues);
        */
  }

  @OnClick(R.id.tvBegin)
  public void setStartDate(View view) {
    if(datePickerListener == null) {
      datePickerListener = new DatePickerListener((TextView)view);
    } else {
      datePickerListener.setView((TextView)view);
    }
    DatePickerFragment fragment = new DatePickerFragment();
    fragment.setListener(datePickerListener);
    fragment.show(fragmentManager, "begin date");
  }

  @OnClick(R.id.tvEnd)
  public void setEndDate(View view) {
    if(datePickerListener == null) {
      datePickerListener = new DatePickerListener((TextView)view);
    } else {
      datePickerListener.setView((TextView)view);
    }
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    datePickerFragment.setListener(datePickerListener);
    datePickerFragment.show(fragmentManager, "end date");

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
        Settings.getDate(tvBegin.getText().toString()),
        Settings.getDate(tvEnd.getText().toString()))) {
      Toast.makeText(
          dialog.getContext(),
          "Begin Date is later than End Date", Toast.LENGTH_SHORT).show();
      return;
    }

    if (!isDateValid(
        Settings.getDate(tvBegin.getText().toString()),
        Settings.getDate(new LocalDate().toString(Settings.DATE_FORMAT)))) {
      Toast.makeText(
          dialog.getContext(),
          "Begin Date is bigger than today's Date", Toast.LENGTH_SHORT).show();
      return;
    }

    settings.setStartDate(tvBegin.getText().toString());
    settings.setEndDate(tvEnd.getText().toString());

    settings.setSortOrder(spSortOrder.getSelectedItem().toString());

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
    tvBegin.setText(settings.getStartDate());
    tvEnd.setText(settings.getEndDate());
    /*
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
    */
    //spSortOrder.setAdapter(spinnerAdapter);
    int index = 0;
    if(settings.getSortOrder() != null)  {
      SpinnerAdapter adapter = spSortOrder.getAdapter();
      for (int i = 0; i < adapter.getCount(); i++) {
        if (adapter.getItem(i).equals(settings.getSortOrder())) {
          index = i;
          break;
        }
      }
    }
    spSortOrder.setSelection(index);
    for (CheckBox ctView : ImmutableList.of(cbArts, cbFashion, cbSports)) {
      String newsDeskText = ctView.getText().toString();
      ctView.setChecked(settings.getNewsDesk().contains(newsDeskText));
    }
  }

}
