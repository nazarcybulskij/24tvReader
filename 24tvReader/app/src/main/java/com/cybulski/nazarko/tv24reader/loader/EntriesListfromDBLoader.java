package com.cybulski.nazarko.tv24reader.loader;

import android.content.AsyncTaskLoader;
import android.util.Log;

import com.cybulski.nazarko.tv24reader.App;
import com.cybulski.nazarko.tv24reader.model.dbmodel.Entry;
import com.cybulski.nazarko.tv24reader.model.dbmodel.Feed;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nazarko on 3/16/16.
 */
public class EntriesListfromDBLoader extends AsyncTaskLoader<RealmResults<Entry>> {
  private static final String LOG_TAG = "EntriesListfromDBLoader";

  private final App app;
  private String  feedUrl;
  private Realm realm;

  public EntriesListfromDBLoader(App app,Feed feed) {
    super(app);
    this.app = app;
    this.feedUrl = feed.getUrl();
  }

  @Override
  public RealmResults<Entry> loadInBackground() {
    Realm realm = Realm.getDefaultInstance();
    final RealmResults<Entry> list = realm.where(Entry.class)
        .equalTo("url", feedUrl)
        .findAll();

    Log.d(LOG_TAG, "loadInBackground() loaded from  DB" + list.size() + " entries");
    return list;
  }


}
