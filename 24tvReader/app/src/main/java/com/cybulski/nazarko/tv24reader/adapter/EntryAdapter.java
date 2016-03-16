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

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by nazarko on 3/16/16.
 */
public class EntryAdapter extends RealmBaseAdapter<Entry> {
  private LayoutInflater inflater;

  public EntryAdapter(Context context, RealmResults<Entry> realmResults, boolean automaticUpdate) {
    super(context, realmResults, automaticUpdate);
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_entry_list, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    Entry  item = getItem(position);
    holder.titleTextView.setText(item.getTitle());
    holder.dateTextView.setText(item.getDate());
    Glide.with(context).load(item.getUrl()).into(holder.mainImgView);


    return  convertView;
  }

  public static class ViewHolder {
    @Bind(R.id.title)
    public TextView titleTextView;
    @Bind(R.id.date)
    public TextView dateTextView;
    @Bind(R.id.main_icon)
    public ImageView mainImgView;

    public ViewHolder(View view) {
      ButterKnife.bind(view);
    }
  }


}
