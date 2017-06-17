package com.example.web1mhz.parkzoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String[] taxon ={"식물상","포유류", "곤충류"};

    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    //private ListView noticeListview;
    private List<Bee> beeList;
    private static LinearLayout notice;

    private PageAdater pageAdater;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

//
//        noticeListview = (ListView) findViewById(R.id.noticeListView);
//        noticeList = new ArrayList<>();
//        adapter = new NoticeAdapter(MainActivity.this, noticeList);
//        noticeListview.setAdapter(adapter);



//        viewPager = (ViewPager) findViewById(R.id.pageView);
//        beeList = new ArrayList<Bee>();
//        pageAdater = new PageAdater(beeList, MainActivity.this);
//        viewPager.setAdapter(pageAdater);



        notice = (LinearLayout) findViewById(R.id.notice);
        init(notice);

        final Button courseButton= (Button) findViewById(R.id.courseButton);
        final Button statisticButton= (Button) findViewById(R.id.statisticButton);
        final Button scheduleButton= (Button) findViewById(R.id.scheduleButton);


//        courseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                notice.setVisibility(View.GONE);
//                courseButton.setBackgroundColor(getResources().getColor(R.color.darkGreen));
//                statisticButton.setBackgroundColor(getResources().getColor(R.color.forestGreen));
//                scheduleButton.setBackgroundColor(getResources().getColor(R.color.forestGreen));
//                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new BeeListFragment());
//                fragmentTransaction.commit();
//             }
//        });
//
//        statisticButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notice.setVisibility(View.GONE);
//
//                courseButton.setBackgroundColor(getResources().getColor(R.color.forestGreen));
//                statisticButton.setBackgroundColor(getResources().getColor(R.color.darkGreen));
//                scheduleButton.setBackgroundColor(getResources().getColor(R.color.forestGreen));
//
//                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new StatisticFragment());
//                fragmentTransaction.commit();
//            }
//        });
//
//        scheduleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                notice.setVisibility(View.GONE);
//                courseButton.setBackgroundColor(getResources().getColor(R.color.forestGreen));
//                statisticButton.setBackgroundColor(getResources().getColor(R.color.forestGreen));
//                scheduleButton.setBackgroundColor(getResources().getColor(R.color.darkGreen));
//                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new MammalFragment());
//                fragmentTransaction.commit();
//            }
//        });


       // new BackgroundTask().execute();
        new BeeListPage().execute();


    }

    private void init(LinearLayout notice) {

        notice.setVisibility(View.GONE);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new MammalFragment());
        fragmentTransaction.commit();
    }

    private long lastTimeBackPressed;
    @Override
    public void onBackPressed(){

        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        } else{

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_list:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("분류군 선택");
                builder.setItems(taxon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(taxon[which]=="곤충류"){
                            notice.setVisibility(View.GONE);
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction= fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, new InsectFragment());
                            fragmentTransaction.commit();

                        } else if(taxon[which]=="식물상"){


                        } else{

                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;

            case R.id.action_search:
                notice.setVisibility(View.GONE);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new BeeListFragment());
                fragmentTransaction.commit();

                break;
            case R.id.action_statistic:

                notice.setVisibility(View.GONE);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticFragment());
                fragmentTransaction.commit();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private class BeeListPage extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://web1mhz.cafe24.com/beelist.php";
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL uri = new URL(target);//사이트주소 얻어오고
                HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();//사이트에 연결하고

                InputStream inputStream = httpURLConnection.getInputStream();//데이터 열고
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//byte 형태로 읽고
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//문자 형태로 바꾸고

                String temp;//한줄씩 읽을 임시 통을 만들고
                StringBuilder stringBuilder = new StringBuilder(); // 연결된 사이트의 데이터를 담을 문자통을 만들고


                while ((temp = bufferedReader.readLine()) !=null ){

                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try {

                //  AlertDialog dialog;
                //   AlertDialog.Builder builder = new AlertDialog.Builder(BeeListFragment.this.getContext());
                //   dialog = builder.setMessage(s)
                //          .setPositiveButton("확인", null)
                //         .create();
                //  dialog.show();

                beeList.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Bee_info");
                int count =0;
                String kor_name, eng_name, status, img_url, date, source;
                while(count < jsonArray.length()){

                    JSONObject object = jsonArray.getJSONObject(count);

                    kor_name=object.getString("kor_name");
                    eng_name=object.getString("eng_name");
                    status=object.getString("status");
                    img_url=object.getString("img_url");
                    date = object.getString("date");
                    source = object.getString("source");

                    Bee bee = new Bee(kor_name, eng_name, status, img_url,date, source);

                    beeList.add(bee);

                    count++;
                }

                if(count==0){

                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setMessage(s)
                            .setPositiveButton("조회된 자료가 없습니다.", null)
                            .create();
                    dialog.show();

                }
                //CardList view
              pageAdater.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);


        }
    }//task
}
