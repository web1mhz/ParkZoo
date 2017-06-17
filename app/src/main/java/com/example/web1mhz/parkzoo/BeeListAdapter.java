package com.example.web1mhz.parkzoo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by web1mhz on 2017-05-20.
 */

public class BeeListAdapter extends BaseAdapter {

    private Context context;
    private List<Bee> beeList;


    public BeeListAdapter(Context context, List<Bee> beeList) {
        this.context = context;
        this.beeList = beeList;
    }

    @Override
    public int getCount() {
        return beeList.size();
    }

    @Override
    public Object getItem(int position) {
        return beeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.bee_list_item, null);

        TextView kor_name = (TextView) v.findViewById(R.id.kor_name);
        TextView eng_name = (TextView) v.findViewById(R.id.eng_name);
        ImageView img_url = (ImageView) v.findViewById(R.id.img_url);

        kor_name.setText(beeList.get(position).getKor_name());
        eng_name.setText(beeList.get(position).getEng_name());
        String status = beeList.get(position).getStatus();
        String source =  beeList.get(position).getSource();
        String date =  beeList.get(position).getDate();


            Picasso.with(context)
                   .load(beeList.get(position).getImg_url())
                   .resize(150, 150)
                   .centerCrop()
                   .into(img_url);

        v.setTag(beeList.get(position).getBeeID());
        return v;
    }



}
