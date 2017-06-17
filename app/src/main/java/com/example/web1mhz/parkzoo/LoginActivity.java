package com.example.web1mhz.parkzoo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView txtRegister = (TextView) findViewById(R.id.txtRegister);//회원가입버튼

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent= new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerintent);
            }
        });

        final EditText txtID = (EditText) findViewById(R.id.txtID);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);//로그인 버튼



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = txtID.getText().toString();
                String userPassword = txtPassword.getText().toString();

                Response.Listener<String> responseLister = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage( txtID.getText().toString() + "환영합니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(LoginActivity.this, TabMainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage( "로그인에 실패했습니다.계정을 확인하시거나 회원가입하세요")
                                        .setNegativeButton("다시확인", null)
                                        .create();
                                dialog.show();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };//Response 기능

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }

    }
}
