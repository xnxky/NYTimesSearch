package com.xxy.nytimessearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by xiangyang_xiao on 2/12/16.
 */
public class SettingsActivity extends AppCompatActivity {
  @Bind(R.id.dpBegin) DatePicker dpBeginDate;
  @Bind(R.id.spSortOrder) Spinner spSortOrder;
  @Bind(R.id.cbArts) CheckBox cbArts;
  @Bind(R.id.cbFashion) CheckBox cbFashion;
  @Bind(R.id.cbSports) CheckBox cbSports;

  private ArrayAdapter<String> adapter;
  private Settings settings;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            Settings.sortOrderValues);
    setContentView(R.layout.content_settings);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    settings = (Settings)intent.getSerializableExtra("settings");
    int[] date = settings.getDate();
    dpBeginDate.init(
        date[0],
        date[1],
        date[2],
        null
    );
    spSortOrder.setAdapter(adapter);
    spSortOrder.setSelection(
        adapter.getPosition(settings.getSortOrder().name()));
    for(CheckBox ctView : ImmutableList.of(cbArts, cbFashion, cbSports)) {
      String newsDeskText = ctView.getText().toString();
      ctView.setChecked(settings.getNewsDesk().contains(newsDeskText));
    }
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
    for(CheckBox ctView : ImmutableList.of(
        cbArts, cbFashion, cbSports
    )) {
      if(ctView.isChecked()) {
        settings.getNewsDesk().add(ctView.getText().toString());
      }
    }

    Intent newSettingsIntent = new Intent();
    newSettingsIntent.putExtra("settings", settings);
    setResult(RESULT_OK, newSettingsIntent);
    finish();
  }
}

