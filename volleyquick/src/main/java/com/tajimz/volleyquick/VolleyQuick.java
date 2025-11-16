package com.tajimz.volleyquick;


import android.content.Context;

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
    private interface ObjListener {
        void onSuccess(JSONObject jsonObject);
        void onFailed(VolleyError volleyError);
    }

    private interface ArrayListener {
        void onSuccess(JSONArray jsonArray);
        void onFailed(VolleyError volleyError);
    }
    public static void requestObj(Context context, String url, JSONObject jsonObject, ObjListener objListener ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                objListener.onSuccess(jsonObject);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                objListener.onFailed(volleyError);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public static void requestArray(Context context, String url,JSONArray jsonArray, ArrayListener arrayListener ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                arrayListener.onSuccess(jsonArray);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                arrayListener.onFailed(volleyError);
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

}
