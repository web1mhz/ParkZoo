package com.example.web1mhz.parkzoo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 2017-05-30.
 */

public class MammalAdapter extends RecyclerView.Adapter<MammalAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String,String>> mammalList; //공지사항 정보 담겨있음

    public MammalAdapter(Context context, ArrayList<HashMap<String, String>> mammalList) {
        this.context = context;
        this.mammalList = mammalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mammal_detail,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        HashMap<String,String> mammalItem = mammalList.get(position);

        holder.kor_name.setText(mammalItem.get("kor_name")); //제목
        holder.eng_name.setText(mammalItem.get("eng_name")); //작성자
        holder.tv_content.setText(mammalItem.get("status")); //내용 일부
        holder.tv_date.setText(mammalItem.get("date"));
        holder.tv_writer.setText(mammalItem.get("source"));
       //holder.img_url.setText(mammalItem.get("img_url"));
        Log.e("[kor_name]", mammalItem.get("kor_name"));

       // holder.tv_date.setText(mammalItem.get("regist_day")); //작성일
        Picasso.with(context).load(mammalItem.get("img_url")).into(holder.img_url);


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent i = new Intent(context, NoticeActivity.class);
               // i.putExtra("writer", noticeList.get(pos).writer);
               // i.putExtra("title", noticeList.get(pos).title);
               // i.putExtra("content", noticeList.get(pos).content);
              //  i.putExtra("regist_day", noticeList.get(pos).date);
              //  i.putExtra("r_count", noticeList.get(pos).count);
              //  i.putExtra("cnum", noticeList.get(pos).cnum);
              //  i.putExtra("img1", noticeList.get(pos).img1);
              //  i.putExtra("img2", noticeList.get(pos).img2);
              //  i.putExtra("img3", noticeList.get(pos).img3);
              // i.putExtra("id", noticeList.get(pos).id);
              // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return this.mammalList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView kor_name;
        TextView eng_name;
        TextView tv_content;
        TextView tv_date;
        ImageView img_url;
        TextView tv_writer;
        CardView cv;

        public ViewHolder(View v) {
            super(v);

            kor_name = (TextView) v.findViewById(R.id.kor_name);//국명
            eng_name = (TextView) v.findViewById(R.id.eng_name);//학명
            tv_content = (TextView) v.findViewById(R.id.tv_content);//status
            tv_writer = (TextView) v.findViewById(R.id.tv_writer);//출처
            tv_date = (TextView) v.findViewById(R.id.tv_date);//날짜
            img_url = (ImageView) v.findViewById(R.id.iv_image);//이미지URL
            cv = (CardView) v.findViewById(R.id.cv);
        }
    }
}
