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

  private final  App                    app;
  private        Feed                   feed;
  private        OkHttpClient           client ;
  private        CancelLoaderListener   listener;

  public EntriesListLoader(App app,Feed feed,CancelLoaderListener listener) {
    super(app);
    this.app = app;
    this.feed = feed;
    this.client = new OkHttpClient();
    this.listener = listener;
  }

  @Override
  public List<Entry> loadInBackground() {
    final ArrayList<Entry> list = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();

    com.cybulski.nazarko.tv24reader.model.dbmodel.Feed  feed = realm.where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class)
        .equalTo("url", this.feed.getUrl())
        .findFirst();

    try {
       list.addAll(new XML24TVParser().parse(loadNewsXml(feed.getUrl())));
    } catch (IOException e) {
      if (listener!=null){
        listener.onCancelLoadNetwork(feed.getUrl());
      }
      e.printStackTrace();
    }
    realm.beginTransaction();
    for (Entry entry:list){
      try {
        com.cybulski.nazarko.tv24reader.model.dbmodel.Entry entrydb =  new com.cybulski.nazarko.tv24reader.model.dbmodel.Entry();
        entrydb.setTitle(entry.getTitle());
        entrydb.setDate(entry.getDate());
        entrydb.setUrl(entry.getUrl());
        entrydb.setLink(entry.getLink());
        entrydb.setId(entry.getId());
        entrydb.setFeed(feed);
        feed.getEntries().add(realm.copyToRealm(entrydb));
      }catch (RealmPrimaryKeyConstraintException exception){

      }
    }
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
