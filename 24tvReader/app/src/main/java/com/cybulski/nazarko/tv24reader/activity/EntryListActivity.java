package com.cybulski.nazarko.tv24reader.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cybulski.nazarko.tv24reader.R;
import com.cybulski.nazarko.tv24reader.adapter.EntryAdapter;
import com.cybulski.nazarko.tv24reader.loader.CancelLoaderListener;
import com.cybulski.nazarko.tv24reader.loader.EntriesListLoader;
import com.cybulski.nazarko.tv24reader.model.dbmodel.Entry;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Feed;

import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EntryListActivity extends AbstractBaseActivity implements LoaderManager.LoaderCallbacks<List<Objects>>,AdapterView.OnItemClickListener{

  private static final String LOG_TAG = "EntryListActivity";

  //
  private static final int ENTRY_LIST_LOADER_ID = 1;
  private static final int ENTRY_DB_LIST_LOADER_ID = 2;
  EntryAdapter     adapter;

  //UI
  @Bind(R.id.entry_list)
  ListView                listView;
  @Bind(R.id.refresh)
  MaterialRefreshLayout   materialRefreshLayout;







  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_entry_list);
    initDB();
    getLoaderManager().initLoader(ENTRY_LIST_LOADER_ID, null, this).forceLoad();
    materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
      @Override
      public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
        getLoaderManager().restartLoader(ENTRY_LIST_LOADER_ID, null, EntryListActivity.this).forceLoad();
        materialRefreshLayout.finishRefreshLoadMore();
      }

      @Override
      public void onfinish() {
        //Toast.makeText(EntryListActivity.this, "finish", Toast.LENGTH_LONG).show();
      }

    });

    Realm realm = Realm .getDefaultInstance();
    com.cybulski.nazarko.tv24reader.model.dbmodel.Feed feed=realm.where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class)
        .findFirst();
//    RealmResults<com.cybulski.nazarko.tv24reader.model.dbmodel.Feed> enties = realm
//        .where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class)
//        .equalTo("url", feed.getUrl())
//        .findAll();


    RealmResults<com.cybulski.nazarko.tv24reader.model.dbmodel.Entry> enties = realm.where(com.cybulski.nazarko.tv24reader.model.dbmodel.Entry.class)
        .equalTo("feed.url", feed.getUrl())
        .findAll();

    enties.sort("date",Sort.DESCENDING);







    adapter = new EntryAdapter(this, enties, true);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(this);
    realm.close();
  }

  private  void  initDB(){
    Realm realm = Realm.getDefaultInstance();
    com.cybulski.nazarko.tv24reader.model.dbmodel.Feed feed = realm
        .where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class)
        .equalTo("url","http://24tv.ua/rss/all.xml")
        .findFirst();
    realm.beginTransaction();
    if (feed==null){
      feed = new com.cybulski.nazarko.tv24reader.model.dbmodel.Feed();
      feed.setUrl("http://24tv.ua/rss/all.xml");
      realm.copyToRealmOrUpdate(feed);
    }
    realm.commitTransaction();
    realm.close();
  }


  @Override
  public Loader onCreateLoader(int id, Bundle args) {
    Log.d(LOG_TAG, "onCreateLoader() " + id);
    if (id == ENTRY_LIST_LOADER_ID) {
      return new EntriesListLoader(getApp(),
          new Feed(Realm.getDefaultInstance().where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class).findFirst()),
          new CancelLoaderListener() {
            @Override
            public void onCancelLoadNetwork(String url) {
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(EntryListActivity.this, "error", Toast.LENGTH_LONG).show();
                  materialRefreshLayout.finishRefresh();
                }
              });
            }
          });
    }
//    if (id == ENTRY_DB_LIST_LOADER_ID) {
//      return new EntriesListfromDBLoader(getApp(),Realm.getDefaultInstance().where(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed.class).findFirst());
//    }
    return null;
  }

  @Override
  public void onLoadFinished(Loader<List<Objects>> loader, List<Objects> data) {
    int id = loader.getId();
    if (id == ENTRY_LIST_LOADER_ID) {
      materialRefreshLayout.finishRefresh();
    }
  }



  @Override
  public void onLoaderReset(Loader<List<Objects>> loader) {

  }


  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent  webIntent= new Intent(this,WebViewActivity.class);
    Entry entry = adapter.getItem(position);

    startWebViewActivity(webIntent,entry);
  }

  public  void startWebViewActivity(Intent intent,Entry entry){
    intent.putExtra(WebViewActivity.EXTRA_URL,entry.getLink());
    startActivity(intent);
  }
}
