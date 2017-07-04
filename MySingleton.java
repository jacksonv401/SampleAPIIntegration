package com.noegenesys.sampleapiintegration;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jackson on 3/7/17.
 */

public class MySingleton {
    private static MySingleton singleton = null;
    private RequestQueue requestQueue = null;
    private static Context mContext = null;


    private MySingleton(Context context){
        mContext = context;
        requestQueue = getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(singleton == null){
            singleton = new MySingleton(context);
        }
        return singleton;
    }
    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
