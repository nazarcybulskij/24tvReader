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

  private static final String TAG_ITEM = "item";
  private static final String TAG_TITLE = "title";
  private static final String TAG_PUBDATE = "pubDate";;
  private static final String TAG_LINK = "link";
  private static final String TAG_DESCRIPTION = "description";
  private static final String ATTR_SRC = "src";
  private static final String TAG_IMG = "img";

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
    if (element.select(TAG_DESCRIPTION).first()!=null){
      Element element1 = element.select(TAG_DESCRIPTION).first();
      Document doc = Jsoup.parse(element1.text(), "", Parser.htmlParser());
      entry.setUrl(doc.select(TAG_IMG).attr(ATTR_SRC));
    }
    return  entry;
  }
}
