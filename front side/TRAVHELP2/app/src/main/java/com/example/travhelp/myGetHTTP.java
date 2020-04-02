package com.example.travhelp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class myGetHTTP extends AsyncTask<String, Void, JSONObject> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public AsyncResponse delegate = null;

    public interface AsyncResponse {
        void processFinish(JSONObject output);
    }

    @Override
    protected JSONObject doInBackground(String... params){
        String stringUrl = params[0];
        String result;
        String inputLine;
        JSONObject response = new JSONObject();
        delegate.getClass();

        try {
            try
            {
                response.put("key", "value");
            }
            catch(JSONException e)
            {
                System.out.println(e);
            }
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            System.out.println(stringBuilder);
            result = stringBuilder.toString();
            try{
                response = new JSONObject(result);
            }
            catch (JSONException error)
            {
                error.printStackTrace();
            }
        }
        catch(IOException e){
            e.printStackTrace();
            try{
                response.put("error", "error");
            }catch (JSONException error){
                error.printStackTrace();
            }
        }
        System.out.println(response);
        return response;
    }
    protected void onPostExecute(JSONObject response){
        System.out.println("IN POST EXECUTE : " + response);
        delegate.processFinish(response);
    }
}