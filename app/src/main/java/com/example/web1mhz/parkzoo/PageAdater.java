package com.example.web1mhz.parkzoo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by web1mhz on 2017-06-05.
 */

public class PageAdater extends PagerAdapter {

   // private int[] imgNabi= {R.drawable.nebalnavi, R.drawable.daewangnabi, R.drawable.horangnabi};
   // String[] temp = {"http://web1mhz.cafe24.com/1.png", "http://web1mhz.cafe24.com/2.png", "http://web1mhz.cafe24.com/3.png"};

    private List<Bee> bee3;
    private LayoutInflater inflater;
    private Context context;


    public PageAdater(List<Bee> bee3, Context context) {
        this.bee3 = bee3;
        this.context = context;

    }

    @Override
    public int getCount() {
       return bee3.size()/10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);


        ImageView imgPage = (ImageView) v.findViewById(R.id.imgPage);
        TextView txtPage = (TextView) v.findViewById(R.id.txtPage);


 //     imgPage.setImageResource(imgNabi[position]);
// //   txtPage.setText(sb[position]);
        Picasso.with(context).load(bee3.get(position).getImg_url()).into(imgPage);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         container.invalidate();
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
