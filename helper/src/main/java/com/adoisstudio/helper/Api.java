package com.adoisstudio.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amitkumar on 05/01/18.
 */


public class Api {

    private static final String TAG = "Api";
    private static final int SOCKET_TIMEOUT_MS = 20000;

    public static int GET = 0;
    public static int POST = 1;

    private int method = POST;

    private static int JSON_REQUEST = 1;
    private int requestType = 0;

    private OnSuccessListener onSuccessListener = null;
    private OnErrorListener onErrorListener = null;

    private Context context;
    private String url;
    private JSONObject json;

    public String msg = "";
    public int success = 0;


    public Api(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public static Api newApi(Context context, String url) {
        return new Api(context, url);
    }

    public Api setMethod(int method) {
        return this;
    }

    public Api addJson(JSONObject json) {
        this.json = json;
        this.requestType = JSON_REQUEST;
        return this;
    }

    public Api onSuccess(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
        return this;
    }

    public Api onError(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
        return this;
    }

    public void run(final String code) {

        Log.e("Api Url", code + ": " + url);
        Log.e("Api Url", code + ": " + json.toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(getMethod(), url, json, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Api Response", code + ": " + response.toString());

                if (onSuccessListener != null)
                    onSuccessListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Api Error", code + ": " + error.toString());

                if (onErrorListener != null)
                    onErrorListener.onError();
            }
        });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);
    }

    private int getMethod() {

        if (method == GET)
            return Request.Method.GET;

        return Request.Method.POST;
    }

    public static int getInt(JSONObject json, String param) {
        try {
            return json.getInt(param);
        } catch (JSONException e) {
            H.log(TAG, e.toString());
        }
        return 0;
    }

    public static String getString(JSONObject json, String param) {
        try {
            return json.getString(param);
        } catch (JSONException e) {
            H.log(TAG, e.toString());
        }
        return "";
    }

    public static JSONObject getJsonObject(JSONObject json, String param) {
        try {
            return json.getJSONObject(param);
        } catch (JSONException e) {
            H.log(TAG, e.toString());
        }
        return new JSONObject();
    }

    public static JSONArray getJsonArray(JSONObject json, String param) {
        try {
            return json.getJSONArray(param);
        } catch (JSONException e) {
            H.log(TAG, e.toString());
        }
        return new JSONArray();
    }

    public static JSONArray getJsonArray(String json) {
        JSONArray array;
        try {
            array = new JSONArray(json);
        } catch (JSONException e) {
            H.log(TAG, e.toString());
            array = new JSONArray();
        }
        return array;
    }

    public interface OnSuccessListener {
        void onSuccess(JSONObject json);
    }

    public interface OnErrorListener {
        void onError();
    }

}//class