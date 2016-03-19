package com.cybulski.nazarko.tv24reader.model.dbmodel;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazarko on 3/16/16.
 */
public class Feed extends RealmObject {
  @PrimaryKey
  private String url;
  private RealmList<Entry> entries = new RealmList<>();

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public RealmList<Entry> getEntries() {
    return entries;
  }

  public void setEntries(RealmList<Entry> entries) {
    this.entries = entries;
  }
}
