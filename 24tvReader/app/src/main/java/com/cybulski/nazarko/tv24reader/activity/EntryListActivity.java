package com.cybulski.nazarko.tv24reader.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cybulski.nazarko.tv24reader.R;
import com.cybulski.nazarko.tv24reader.adapter.EntryAdapter;
import com.cybulski.nazarko.tv24reader.loader.EntriesListLoader;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Feed;

import butterknife.Bind;

public class EntryListActivity extends AbstractBaseActivity implements LoaderManager.LoaderCallbacks<Object> {

  private static final String LOG_TAG = "EntryListActivity";

  //
  private static final int ENTRY_LIST_LOADER_ID = 1;
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
    getLoaderManager().initLoader(ENTRY_LIST_LOADER_ID, null, this).forceLoad();

    materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
      @Override
      public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
        materialRefreshLayout.postDelayed(new Runnable() {
          @Override
          public void run() {
            materialRefreshLayout.finishRefresh();

          }
        }, 3000);
        materialRefreshLayout.finishRefreshLoadMore();

      }

      @Override
      public void onfinish() {
        Toast.makeText(EntryListActivity.this, "finish", Toast.LENGTH_LONG).show();
      }

    });

  }


  @Override
  public Loader onCreateLoader(int id, Bundle args) {
    Log.d(LOG_TAG, "onCreateLoader() " + id);
    if (id == ENTRY_LIST_LOADER_ID) {
      return new EntriesListLoader(getApp(),new Feed());
    }
    return null;
  }

  @Override
  public void onLoadFinished(Loader<Object> loader, Object data) {

  }

  @Override
  public void onLoaderReset(Loader<Object> loader) {

  }
}
