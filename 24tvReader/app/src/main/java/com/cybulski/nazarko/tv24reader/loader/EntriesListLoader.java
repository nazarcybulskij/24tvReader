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

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
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

    Realm realm = Realm.getInstance(getContext());
    com.cybulski.nazarko.tv24reader.model.dbmodel.Feed  feed = realm.where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class)
                                                                    .equalTo("url", this.feed.getUrl())
                                                                    .findFirst();
    Log.d(LOG_TAG, feed.getUrl() + " entries" + feed.getEntries().size());
    realm.beginTransaction();
    if (feed.getEntries()==null){
      feed.setEntries(new RealmList<com.cybulski.nazarko.tv24reader.model.dbmodel.Entry>());
    }

    for (Entry entry:list){
      try {
        com.cybulski.nazarko.tv24reader.model.dbmodel.Entry entrydb = realm.createObject(com.cybulski.nazarko.tv24reader.model.dbmodel.Entry.class);
        entrydb.setTitle(entry.getTitle());
        entrydb.setDate(entry.getDate());
        entrydb.setUrl(entry.getUrl());
        feed.getEntries().add(entrydb);
      }catch (RealmPrimaryKeyConstraintException exception){

      }
    }
    realm.copyToRealmOrUpdate(feed);
    realm.commitTransaction();
    realm.close();
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
