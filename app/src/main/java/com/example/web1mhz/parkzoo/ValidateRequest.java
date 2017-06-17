package com.example.web1mhz.parkzoo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by web1mhz on 2017-05-20.
 */

public class ValidateRequest extends StringRequest{

    final static private String URL ="http://web1mhz.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String txtID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID", txtID);

    }

    @Override
    public Map<String, String> getParams(){

        return parameters;
    }


}
