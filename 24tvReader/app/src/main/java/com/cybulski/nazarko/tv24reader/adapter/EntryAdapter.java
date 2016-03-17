package com.cybulski.nazarko.tv24reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cybulski.nazarko.tv24reader.R;
import com.cybulski.nazarko.tv24reader.activity.EntryListActivity;
import com.cybulski.nazarko.tv24reader.model.dbmodel.Entry;
import com.cybulski.nazarko.tv24reader.model.dbmodel.Feed;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by nazarko on 3/16/16.
 */
public class EntryAdapter extends RealmBaseAdapter<Feed> {
  private LayoutInflater inflater;
  private static final DateFormat[] PUBDATE_DATE_FORMATS_OLD = {
      new SimpleDateFormat("ddd, dd MMM yyyy HH:mm:ss z", Locale.US),

  };
  private static final DateFormat[] PUBDATE_DATE_FORMATS_NEW = {
      new SimpleDateFormat("dd MMM , HH:mm", Locale.UK),

  };


  public EntryAdapter(Context context, RealmResults<Feed> realmResults, boolean automaticUpdate) {
    super(context, realmResults, automaticUpdate);
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return getItem(0).getEntries().size();
  }

  @Override
  public Feed getItem(int i) {
    return super.getItem(0);
  }

  @Override
  public long getItemId(int i) {
    return super.getItemId(i);
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_entry_list, parent, false);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    Feed feed = getItem(0);
    Entry  item = feed.getEntries().get(position);
    holder.titleTextView.setText(item.getTitle());
    holder.dateTextView.setText(PUBDATE_DATE_FORMATS_NEW[0].format(parsePubdateDate(item.getDate())));
    Glide.with(context).load(item.getUrl()).into(holder.mainImgView);
    return  convertView;
  }


  private Date parsePubdateDate(String dateStr) {
    Date result = null;
    try {
      result = PUBDATE_DATE_FORMATS_OLD[0].parse(dateStr);
      return  result;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new Date();

  }

  public static class ViewHolder {
    @Bind(R.id.title)
    public TextView titleTextView;
    @Bind(R.id.date)
    public TextView dateTextView;
    @Bind(R.id.main_icon)
    public ImageView mainImgView;

    public ViewHolder(View view) {
      ButterKnife.bind(this,view);
    }
  }


}
