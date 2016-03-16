package com.cybulski.nazarko.tv24reader.activity;

import android.app.Activity;

import com.cybulski.nazarko.tv24reader.App;

import butterknife.ButterKnife;

/**
 * Created by nazarko on 3/16/16.
 */
public class AbstractBaseActivity extends Activity {

  @Override
  public void setContentView(final int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  public App getApp() {
    return (App) getApplicationContext();
  }
}