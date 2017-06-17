package com.example.web1mhz.parkzoo;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InsectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InsectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InsectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MammalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsectFragment newInstance(String param1, String param2) {
        InsectFragment fragment = new InsectFragment();
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


    //DATA parsing 관련

    private static final String TAG_KORNAME="kor_name";
    private static final String TAG_ENGNAME = "eng_name";
    private static final String TAG_STATUS = "status";
    private static final String TAG_IMGURL = "img_url";
    private static final String TAG_DATE = "date";
    private static final String TAG_SOURCE = "source";


    ArrayList<HashMap<String,String>> insectLIst;

    //UI 관련
    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;



   // private ArrayAdapter parkAdapter;
   // private Spinner parkSpinner;
   // private ArrayAdapter taxonAdapter;
   // private Spinner taxonSpinner;
    //private Button btnMammalList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_insect, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       // parkSpinner = (Spinner) getView().findViewById(R.id.parkSpinner);
       // taxonSpinner = (Spinner) getView().findViewById(R.id.taxonSpinner);
       // parkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.park, android.R.layout.simple_spinner_dropdown_item);
      //  parkSpinner.setAdapter(parkAdapter);
       // taxonAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.taxon, android.R.layout.simple_spinner_dropdown_item);
       // taxonSpinner.setAdapter(taxonAdapter);

        //리스트 불러오기
        insectLIst = new ArrayList<HashMap<String, String>>();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = (RecyclerView) getView().findViewById(R.id.rvInsect);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);



       // btnMammalList = (Button) getView().findViewById(mammalListButton);
        //btnMammalList.setOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View v) {
                new listTask().execute();
        //    }
       // });



    }


    private class listTask extends AsyncTask<Void, Void, String>{

        private String target;

        @Override
        protected void onPreExecute() {

            target = "http://web1mhz.cafe24.com/beelist.php";

        }

        @Override
        protected void onPostExecute(String json) {

             makeList(json);


        }

        private void makeList(String json) {

            insectLIst.clear();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("Bee_info");
                int count =0;

                String kor_name, eng_name, status, img_url,date, source;


                while(count < jsonArray.length()){

                    JSONObject object = jsonArray.getJSONObject(count);

                    kor_name=object.getString("kor_name");
                    eng_name=object.getString("eng_name");
                    status=object.getString("status");
                    img_url=object.getString("img_url");
                    date=object.getString("date");
                    source=object.getString("source");


                    HashMap<String, String> posts = new HashMap<String, String>( );

                    posts.put(TAG_KORNAME, kor_name);
                    posts.put(TAG_ENGNAME, eng_name);
                    posts.put(TAG_STATUS,status );
                    posts.put(TAG_IMGURL, img_url);
                    posts.put(TAG_DATE, date);
                    posts.put(TAG_SOURCE, source);


                    insectLIst.add(posts);

                    count++;
                }

                MammalAdapter adapter = new MammalAdapter(getActivity(), insectLIst);
                Log.e("onCreate[noticeList]", "" + insectLIst.size());
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String json;
                while((json = br.readLine())!=null){

                    sb.append(json + "\n");

                }

                br.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return sb.toString().trim();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;

        }
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


}
