package com.cybulski.nazarko.tv24reader.model.networkmodel;

/**
 * Created by nazarko on 3/16/16.
 */
public class Entry {



  String  id;
  String  title;
  String  date;
  String  url;
  String  link;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
