package com.example.travhelp;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class myPostHTTP extends AsyncTask<Object, Void, String>{

    public AsyncResponse delegate = null;
    public String server_response;
    public interface AsyncResponse {
        void processFinish(String output);
    }
    @Override
    protected String doInBackground(Object... params) {
        String stringUrl = params[0].toString();
        JSONObject data = (JSONObject)params[1];// data to post
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(stringUrl);

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        try {
            nameValuePair.add(new BasicNameValuePair("id_nurse", data.getString("id_nurse")));
            nameValuePair.add(new BasicNameValuePair("id_patient", data.getString("id_patient")));
            nameValuePair.add(new BasicNameValuePair("name", data.getString("name")));
            nameValuePair.add(new BasicNameValuePair("address", data.getString("address")));
            nameValuePair.add(new BasicNameValuePair("number", data.getString("number")));
            nameValuePair.add(new BasicNameValuePair("notes", data.getString("notes")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("Http Post Response:", response.toString());
            server_response = response.toString();
            //delegate.getClass();
            return(server_response);
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return("") ;
    }
    protected void onPostExecute(String response){
        System.out.println("IN POST EXECUTE : " + response);
        //delegate.processFinish(response);
    }
}