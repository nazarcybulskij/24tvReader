package com.cybulski.nazarko.tv24reader.model.networkmodel;

/**
 * Created by nazarko on 3/16/16.
 */
public class Feed {
  String url;

  public Feed(com.cybulski.nazarko.tv24reader.model.dbmodel.Feed feed) {
    this.url = feed.getUrl();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
