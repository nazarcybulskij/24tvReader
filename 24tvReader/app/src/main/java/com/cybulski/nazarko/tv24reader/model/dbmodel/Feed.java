package com.cybulski.nazarko.tv24reader.model.dbmodel;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by nazarko on 3/16/16.
 */
public class Feed extends RealmObject {
  String url;
  RealmList<Entry> entries;
}
