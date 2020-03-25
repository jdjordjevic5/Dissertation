package com.example.dissertation;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestaurantGetRequest {
    private RestaurantListener listener;

    public RestaurantGetRequest(RestaurantListener listener) {
        this.listener = listener;
    }

    public RestaurantGetRequest() {
    }

    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData().execute(createUrl());
    }


    private String createUrl() throws UnsupportedEncodingException {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.005020,23.745547&radius=300&type=restaurant&key=AIzaSyB2Kyy8TS2MGcHCYv68hK4-NPfASWe6LV0";
    }


    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a new InputStreamReader
                InputStream streamReader = myUrl.openConnection().getInputStream();
                //Create a new buffered reader and string buffer
                BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
                StringBuffer stringBuffer = new StringBuffer();
                //Check if the line we are reading is not null
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                return stringBuffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            try {
                parseJSon(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;
        List<RestaurantNote> restaurants = new ArrayList<RestaurantNote>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRestaurants = jsonData.getJSONArray("results");
        for (int i = 0; i < jsonRestaurants.length(); i++) {
            JSONObject jsonRoute = jsonRestaurants.getJSONObject(i);

            if (jsonRoute.has("opening_hours")) {
                RestaurantNote restaurant = new RestaurantNote(jsonRoute.getString("name"));
                restaurants.add(restaurant);
            }
            listener.onRestaurantSuccess(restaurants);
        }
    }
}