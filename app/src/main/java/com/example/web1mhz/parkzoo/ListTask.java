package com.example.web1mhz.parkzoo;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by web1mhz on 2017-06-05.
 */

public class ListTask extends AsyncTask<Void, Void, String> {


        private NoticeAdapter adapter;
        private List<Notice> noticeList;
        String target;

        public ListTask(NoticeAdapter adapter, List<Notice> noticeList) {

            this.adapter = adapter;
             this.noticeList = noticeList;
         }

        @Override
        protected void onPreExecute() {

            target = "http://web1mhz.cafe24.com/NoticeList.php";
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


                while ((temp = bufferedReader.readLine()) != null) {

                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");//notic.php파일 수행후 결과 받아오고

                int count = 0;
                String noticeContent, noticeName, noticeDate;

                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");

                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);

                    noticeList.add(notice);

                    count++;
                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
 }//task
