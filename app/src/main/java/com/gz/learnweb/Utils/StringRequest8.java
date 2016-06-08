package com.gz.learnweb.Utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.gz.learnweb.Constant;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gz on 15/9/1.
 */
public class StringRequest8 extends StringRequest {
    private Context context;

    public StringRequest8(int method, Context context, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.context = context;
    }

    public StringRequest8(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String str = null;
        try {
            str = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<>();
        String cookieFromResponse;
        SpUtils spUtils = new SpUtils(context);
        cookieFromResponse = spUtils.getValue(Constant.DataKey.SESS, null);
        try {
            if (cookieFromResponse != null && cookieFromResponse.length() != 0) {
                cookieFromResponse.replace("\u003d", "=");
                LogUtil.i("replace string", cookieFromResponse);
                header.put("Cookie", cookieFromResponse);
                LogUtil.i("send header", header.toString());
            }
            return header;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
