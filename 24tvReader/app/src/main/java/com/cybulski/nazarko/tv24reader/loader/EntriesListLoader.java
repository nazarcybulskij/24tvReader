package com.cybulski.nazarko.tv24reader.loader;

import android.content.AsyncTaskLoader;
import android.util.Log;

import com.cybulski.nazarko.tv24reader.App;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Entry;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazarko on 3/16/16.
 */
public class EntriesListLoader  extends AsyncTaskLoader<List<Entry>> {

  private static final String LOG_TAG = "EntriesListLoader";

  private final  App    app;
  private        Feed   feed;

  public EntriesListLoader(App app,Feed feed) {
    super(app);
    this.app = app;
    this.feed = feed;
  }

  @Override
  public List<Entry> loadInBackground() {
    final ArrayList<Entry> list = null; //app.getService().getQuestList(app.getUserId());
    Log.d(LOG_TAG, "loadInBackground() loaded " + list.size() + " entries");
    return list;
  }

}
