package com.tajimz.volleyquick;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyQuick {
    public interface ObjListener {
        void onSuccess(JSONObject jsonObject);
        void onFailed(VolleyError volleyError);
    }

    public interface ArrayListener {
        void onSuccess(JSONArray jsonArray);
        void onFailed(VolleyError volleyError);
    }
    public static void requestObj(Context context,int loading, int method,  String url, JSONObject jsonObject, ObjListener objListener ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        if (loading != 0) startLoading(context, loading);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                objListener.onSuccess(jsonObject);
                endLoading();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                objListener.onFailed(volleyError);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void requestArray(Context context,int loading, int method,  String url,JSONArray jsonArray, ArrayListener arrayListener ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        if (loading != 0) startLoading(context, loading);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                arrayListener.onSuccess(jsonArray);
                endLoading();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                arrayListener.onFailed(volleyError);
                endLoading();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void putInJsonObj(JSONObject jsonObject, String key, String value){
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getFromJsonObj(JSONObject jsonObject, String key){
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static AlertDialog alertDialog;
    private static void startLoading(Context context, int view){
        if (alertDialog != null) endLoading();
        alertDialog = new AlertDialog.Builder(context)
                .setView(LayoutInflater.from(context).inflate(view, null))
                .setCancelable(false)
                .create();

        if (alertDialog.getWindow() != null) {
            // Make background transparent
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Force wrap_content for width and height
            alertDialog.getWindow().setLayout(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        alertDialog.show();


    }

    private static void endLoading(){
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }

}
