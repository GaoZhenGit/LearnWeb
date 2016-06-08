package com.gz.learnweb.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * volley网络
 * Created by host on 2015/8/14.
 */
public class VolleyUtils {
    private static RequestQueue mQueue;
    private static boolean isInit = false;
    private static Context context;

    public static RequestQueue getmQueue() throws Exception {
        if (isInit) {
            return mQueue;
        } else {
            throw new Exception("volley has not init");
        }
    }

    public static void init(Context context) {
        mQueue = Volley.newRequestQueue(context);
        VolleyUtils.context = context;
        isInit = true;
    }

    public static void post(String httpurl, final Map<String, String> params, final NetworkListener networkListener) {
        StringRequest stringRequest = new StringRequest8(Request.Method.POST, VolleyUtils.context, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.i("volley", "response -> " + response);
                        networkListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.i("volley error", error.getMessage() + "");
                networkListener.onFail(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                return params;
            }
        };
        mQueue.add(stringRequest);
    }

    public static void login(String httpurl, final Map<String, String> params, final NetworkListener networkListener) {
        StringRequest stringRequest = new CookieStoreRequest(Request.Method.POST, VolleyUtils.context, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.i("volley", "response -> " + response);
                        networkListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.i("volley error", error.getMessage() + "");
                networkListener.onFail(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                return params;
            }
        };
        mQueue.add(stringRequest);
    }

    public interface NetworkListener {
        void onSuccess(String response);

        void onFail(String error);
    }
}
