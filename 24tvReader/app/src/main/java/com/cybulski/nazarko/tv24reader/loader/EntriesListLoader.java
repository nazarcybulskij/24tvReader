package com.cybulski.nazarko.tv24reader.loader;

import android.content.AsyncTaskLoader;
import android.util.Log;

import com.cybulski.nazarko.tv24reader.App;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Entry;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Feed;
import com.cybulski.nazarko.tv24reader.parser.XML24TVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nazarko on 3/16/16.
 */
public class EntriesListLoader  extends AsyncTaskLoader<List<Entry>> {

  private static final String LOG_TAG = "EntriesListLoader";

  private final  App            app;
  private        Feed           feed;
  private        OkHttpClient   client ;

  public EntriesListLoader(App app,Feed feed) {
    super(app);
    this.app = app;
    this.feed = feed;
    client = new OkHttpClient();
  }

  @Override
  public List<Entry> loadInBackground() {
    final ArrayList<Entry> list = new ArrayList<>();

    try {
       list.addAll(new XML24TVParser().parse(loadNewsXml(feed.getUrl())));

    } catch (IOException e) {
      this.cancelLoad();
      e.printStackTrace();
    }


    Log.d(LOG_TAG, "loadInBackground() loaded " + list.size() + " entries");
    return list;
  }


   private String loadNewsXml(String url) throws IOException {
     Request request = new Request.Builder()
         .url(url)
         .build();
     Response response = client.newCall(request).execute();
     return response.body().string();
   }


  }
