package com.cybulski.nazarko.tv24reader.model.dbmodel;

import io.realm.RealmObject;

/**
 * Created by nazarko on 3/16/16.
 */
public class Entry extends RealmObject {
  String title;
  String date;
  String  url;
}
