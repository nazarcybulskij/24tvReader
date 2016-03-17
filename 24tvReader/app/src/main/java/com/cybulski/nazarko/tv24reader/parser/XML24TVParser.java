package com.cybulski.nazarko.tv24reader.parser;

import android.util.Log;
import android.widget.Toast;

import com.cybulski.nazarko.tv24reader.activity.EntryListActivity;
import com.cybulski.nazarko.tv24reader.model.networkmodel.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.util.ArrayList;

/**
 * Created by nazarko on 3/17/16.
 */
public class XML24TVParser {

  private static final String AND_SHARP = "&#";
  private static final String HTML_TEXT = "text/html";
  private static final String HTML_TAG_REGEX = "<(.|\n)*?>";

  private static final String TAG_RSS = "rss";
  private static final String TAG_RDF = "rdf";
  private static final String TAG_FEED = "feed";
  private static final String TAG_ENTRY = "entry";
  private static final String TAG_UPDATED = "updated";


  private static final String TAG_DESCRIPTION = "description";
  private static final String TAG_MEDIA_DESCRIPTION = "media:description";
  private static final String TAG_CONTENT = "content";
  private static final String TAG_MEDIA_CONTENT = "media:content";
  private static final String TAG_ENCODED_CONTENT = "encoded";
  private static final String TAG_SUMMARY = "summary";

  private static final String TAG_PUBLISHED = "published";
  private static final String TAG_DATE = "date";
  private static final String TAG_LAST_BUILD_DATE = "lastBuildDate";
  private static final String TAG_ENCLOSURE = "enclosure";
  private static final String TAG_GUID = "guid";
  private static final String TAG_AUTHOR = "author";
  private static final String TAG_CREATOR = "creator";
  private static final String TAG_NAME = "name";

  private static final String TAG_ITEM = "item";
  private static final String TAG_TITLE = "title";
  private static final String TAG_PUBDATE = "pubDate";;
  private static final String TAG_LINK = "link";

  public ArrayList<Entry>  parse(String sourse){
    ArrayList<Entry> list = new ArrayList<>();
    Document doc = Jsoup.parse(sourse, "", Parser.xmlParser());
    for (Element e:doc.select(TAG_ITEM)){
      list.add(parseElement(e));
    }



    return list;
  }

  public Entry parseElement(Element element) {
     Entry entry = new Entry();
     if (element.select(TAG_TITLE).first()!=null){
       entry.setTitle(element.select(TAG_TITLE).text());
     }
    if (element.select(TAG_PUBDATE).first()!=null){
      entry.setDate(element.select(TAG_PUBDATE).text());
    }
    if (element.select(TAG_LINK).first()!=null){
      entry.setLink(element.select(TAG_LINK).text());
    }
    return  entry;
  }
}
