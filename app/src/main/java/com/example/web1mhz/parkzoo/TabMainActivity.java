package com.example.web1mhz.parkzoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class TabMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        toolbar = (Toolbar) findViewById(R.id.tabToolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.tabViewPager);
        viewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MammalFragment(), "생물종목록");
        viewPagerAdapter.addFragment(new BeeListFragment(), "생물종검색");
        viewPagerAdapter.addFragment(new TopPaidFragment(), "동영상보기");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

       // tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return super.onCreateOptionsMenu(menu);

    }

    private long lastTimeBackPressed;
    @Override
    public void onBackPressed(){

        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        } else{

            AlertDialog.Builder builder = new AlertDialog.Builder(TabMainActivity.this);
            builder.setMessage("정말로 종료하시겠습니까?");
            builder.setTitle("종료 알림창")
                    .setCancelable(false)
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("종료 알림창");
            alert.show();

        }
        //Toast.makeText(this, "뒤로 버튼을 한 번 더 눌려 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
