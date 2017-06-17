package com.example.web1mhz.parkzoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends Activity {


    private Spinner parkspinner;
    private  ArrayAdapter adapter;
    private  String userID;
    private  String userPassword;
    private String userGender;
    private  String userPark;
    private String userEmail;

    AlertDialog dialog;

   private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);


        parkspinner = (Spinner) findViewById(R.id.parkSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.park, android.R.layout.simple_spinner_dropdown_item);
        parkspinner.setAdapter(adapter);

        final EditText txtID = (EditText) findViewById(R.id.txtID);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);

        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtPark = (EditText) findViewById(R.id.txtID);

        final RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderRadio);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton genderButton =  (RadioButton) findViewById(checkedId);
                userGender = genderButton.getText().toString();
            }
        });

        //중복체크 버튼 기능수행

        final Button ValidateButton = (Button) findViewById(R.id.btncheckID);

        ValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = txtID.getText().toString();
                //이미 아이디가 존재하여 중복할 때
                if(validate){

                    return;
                }
                //아이디 입력창이 빈칸일때 또는 이미
                if(userID.equals("")){

                   AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                   dialog = bulider.setMessage("아이디는 빈칸이 될 수 없습니다.")
                           .setPositiveButton("확인", null)
                           .create();

                    dialog.show();
                    return;
                }

                //사용할 수 있는 아이디인 경우

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if(success){

                                AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = bulider.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                txtID.setEnabled(false);
                                validate = true;
                                txtID.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                ValidateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));

                            }
                            else{

                                AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = bulider.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch (Exception e ){
                            e.printStackTrace();
                        }
                    }
                };//Response.Listener
                //실제로 중복체크 기능을 할 수 있게
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

            }
        });//중복체크 버튼 기능완료

        //회원가입 버튼 기능

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String userID=txtID.getText().toString();
               String userPassword=txtPassword.getText().toString();
               String userPark=parkspinner.getSelectedItem().toString();
               String userEmail=txtEmail.getText().toString();

                if(!validate){

                    AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = bulider.setMessage("아이디 중복체크 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();

                    return;

                }

                if(userID.equals("")||userPassword.equals("")||userPark.equals("")||userEmail.equals("")||userGender.equals("")){

                    AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = bulider.setMessage("빈 칸 없이 입력하세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;

                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonresponse = new JSONObject(response);
                            boolean success = jsonresponse.getBoolean("success");

                            if(success){

                                AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = bulider.setMessage("회원 등록에 성공했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();

                            }
                            else{

                                AlertDialog.Builder bulider = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = bulider.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch (Exception e   ){
                            e.printStackTrace();
                        }
                    }
                };//Response.Listener

                //실제로 회원가입 기능 php 파일 수행 할수 있게
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword,  userGender, userPark,  userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });//회원가입 버튼 기능완료

    }//0nCreate

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }

    }
}
