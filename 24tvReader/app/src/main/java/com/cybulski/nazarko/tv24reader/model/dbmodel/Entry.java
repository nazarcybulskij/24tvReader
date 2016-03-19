package com.cybulski.nazarko.tv24reader.model.dbmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazarko on 3/16/16.
 */
public class Entry extends RealmObject {


  @PrimaryKey
  private String  id;
  private String  link;
  private String  title;
  private String  date;
  private String  url;
  private Feed    feed;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }
}
