package com.cybulski.nazarko.tv24reader;

import android.app.Application;


import com.cybulski.nazarko.tv24reader.model.dbmodel.Feed;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by nazarko on 3/16/16.
 */
public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
    Realm.setDefaultConfiguration(realmConfiguration);

  }
}
