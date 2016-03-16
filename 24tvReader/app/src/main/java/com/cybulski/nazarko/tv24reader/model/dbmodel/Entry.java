package com.cybulski.nazarko.tv24reader.model.dbmodel;

import io.realm.RealmObject;

/**
 * Created by nazarko on 3/16/16.
 */
public class Entry extends RealmObject {
  String  title;
  String  date;
  String  url;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
