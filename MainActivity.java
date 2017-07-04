package com.noegenesys.sampleapiintegration;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    Button btnmap ;
    List<UserLists> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (TextView)findViewById(R.id.txtResult);
        btnmap  = (Button)findViewById(R.id.btnMap);

        getAPIResult("https://reqres.in/api/users?page=2");
//            postAPI("https://reqres.in/api/users");
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapDemo.class);
                startActivity(intent);
            }
        });
    }

    private void getAPIResult(String url) {

        StringRequest streingRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray jsonArray = jsonObj.optJSONArray("data");
                            userList = new ArrayList<UserLists>();
                            for(int i = 0;i<jsonArray.length();i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                UserLists objList = new UserLists();
                                objList.setId(obj.getInt("id"));
                                objList.setFirst_name(obj.getString("first_name"));
                                objList.setLast_name(obj.getString("last_name"));
                                objList.setAvatar(obj.getString("avatar"));
                                userList.add(objList);
                            }
                            Log.d("",""+userList);
                            Log.d("",""+userList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtResult.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtResult.setText(error.toString());
                    }
                });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(streingRequest);
    }
    private void postAPI(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        txtResult.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        txtResult.setText(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<String, String>();
                params.put("name","Jackson Varghese T V");
                params.put("job","Android Developer");
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
