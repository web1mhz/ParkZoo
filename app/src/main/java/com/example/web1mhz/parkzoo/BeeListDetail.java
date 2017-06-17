package com.example.web1mhz.parkzoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BeeListDetail extends AppCompatActivity {


    private Button btnMap;
    private Intent in;
    private TextView kor_name, eng_name, status, date, source;
    private ImageView img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bee_list_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //BeeListFragment에서 putExtra() 값을 전달받는 곳
        in = getIntent();
        final Bee bee = (Bee) in.getSerializableExtra("Bee");

        this.kor_name= (TextView) findViewById(R.id.kor_name);
        this.eng_name= (TextView) findViewById(R.id.eng_name);
        this.status= (TextView) findViewById(R.id.status);
        this.date= (TextView) findViewById(R.id.date);
        this.source= (TextView) findViewById(R.id.source);

        this.img_url= (ImageView) findViewById(R.id.img_url);
        kor_name.setText(bee.getKor_name().toString().trim());
        eng_name.setText(bee.getEng_name().toString().trim());
        status.setText(bee.getStatus().toString().trim());
        date.setText(bee.getDate());
        source.setText(bee.getSource());
        Picasso.with(this).load(bee.getImg_url()).into(img_url);

        btnMap = (Button) findViewById(R.id.btnMap);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(BeeListDetail.this, MapsActivity.class);
                startActivity(in);
            }
        });

    }


}
