package com.example.web1mhz.parkzoo;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeeListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ID_EXTRA = "beeItem";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BeeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeeListFragment newInstance(String param1, String param2) {
        BeeListFragment fragment = new BeeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


//        private ArrayAdapter yearAdapter;
//        private Spinner yearSpinner;
//        private ArrayAdapter areaAdapter;
//        private Spinner areaSpinner;
//        private String courseUniversity="";

        private EditText searchText;
        private List<Bee> beeList;
        private BeeListAdapter adapter;
        private ListView beeListView;


    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);


//        final RadioGroup courseUniversityGroup = (RadioGroup) getView().findViewById(R.id.courseGroup);
//        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
//        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
//
//        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//
//                RadioButton courseButtoon = (RadioButton) getView().findViewById(checkedId);
//                courseUniversity = courseButtoon.getText().toString();
//
//                yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
//                yearSpinner.setAdapter(yearAdapter);
//
//                if(courseUniversity.equals("육상생태계")){
//                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.landTaxon, android.R.layout.simple_spinner_dropdown_item);
//                    areaSpinner.setAdapter(areaAdapter);
//
//                }
//                else if(courseUniversity.equals("해양생태계")){
//                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.seaTaxon, android.R.layout.simple_spinner_dropdown_item);
//                    areaSpinner.setAdapter(areaAdapter);
//                }
//            }
//        });

        searchText = (EditText) getView().findViewById(R.id.searchText);
        beeListView= (ListView) getView().findViewById(R.id.beeListView);
        beeList = new ArrayList<Bee>();
        adapter = new BeeListAdapter(getContext().getApplicationContext(), beeList);
        beeListView.setAdapter(adapter);


        Button searchButton= (Button) getView().findViewById(R.id.beeListButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BeeListTask().execute();
            }
        });

        beeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //리스트에 있는 하나의 아이템을 지정해서
                Bee bee = beeList.get(position);
                //Toast.makeText(getContext().getApplicationContext(), bee.getImg_url(), Toast.LENGTH_SHORT).show();

                //상세내용화면으로 "Bee" 태그로 bee아이템 하나늘 전달함.
                Intent in= new Intent(getContext().getApplicationContext(), BeeListDetail.class);
                in.putExtra("Bee", bee);
                startActivity(in);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beelist, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BeeListTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {

            try {

                if(searchText.getText().toString().equals("")){

                    Toast.makeText(getContext().getApplicationContext(), "국명이나 학명을 입력하세요", Toast.LENGTH_LONG).show();


                } else {

                    target = "http://web1mhz.cafe24.com/searchlist.php?beeID=" + URLEncoder.encode(searchText.getText().toString().trim(), "UTF-8")
                            + "&kor_name=" + URLEncoder.encode(searchText.getText().toString().trim(), "UTF-8")
                            + "&eng_name=" + URLEncoder.encode(searchText.getText().toString().trim(), "UTF-8");

                    searchText.setText("");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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

//                AlertDialog dialog;
//                AlertDialog.Builder builder = new AlertDialog.Builder(BeeListFragment.this.getContext());
//                dialog = builder.setMessage(s)
//                        .setPositiveButton("확인", null)
//                        .create();
//               dialog.show();
                beeList.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("searched");
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
                     AlertDialog.Builder builder = new AlertDialog.Builder(BeeListFragment.this.getContext());
                      dialog = builder.setMessage(s)
                             .setPositiveButton("조회된 자료가 없습니다.", null)
                            .create();
                     dialog.show();

                }
                //변경된 내용을 리스트에 알려줌
                adapter.notifyDataSetChanged();

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
