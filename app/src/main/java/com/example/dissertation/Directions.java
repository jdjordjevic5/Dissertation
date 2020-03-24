package com.example.dissertation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Directions extends AppCompatActivity implements RestaurantListener{

    private RequestQueue mQueue_route;
    private RequestQueue mQueue_restaurant;
    private String id_result_origin;
    TextToSpeech t1;

    private double arr_lat;
    private double arr_lng;
    private double dpt_lat;
    private double dpt_lng;

    private double arr_lat2;
    private double arr_lng2;
    private double dpt_lat2;
    private double dpt_lng2;

    private String departure_stop_name;
    private String arrival_stop_name;
    private String departure_stop_name2;
    private String arrival_stop_name2;
    private String arrival_time;
    private String departure_time;
    private String distance;
    private String duration;
    private double userlat;
    private double userlng;
    PageAdapter pageAdapter;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        try {
            new RestaurantGetRequest(this).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        pageAdapter = new PageAdapter(Directions.this.getSupportFragmentManager());
        FragmentDirection fragmentDirection = new FragmentDirection();
        FragmentRestaurant fragmentRestaurant = new FragmentRestaurant();
        FragmentCoffee fragmentCoffee = new FragmentCoffee();
        FragmentCultural fragmentCultural = new FragmentCultural();

        pageAdapter.addDirection(fragmentDirection, "direction", "asdsa");
        pageAdapter.addRestaurant(fragmentRestaurant, "restaurant", "");
        pageAdapter.addCoffee(fragmentCoffee, "coffee", "Hello");
        pageAdapter.addCultural(fragmentCultural, "cultural", "olaa");


        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        String input_destination = getIntent().getStringExtra("destination");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Menu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.directions:
                        break;
                }
                return true;
            }
        });

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(Directions.this);
        if (ActivityCompat.checkSelfPermission(Directions.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Directions.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            client.getLastLocation().addOnSuccessListener(Directions.this, new OnSuccessListener<Location>() {
                public void onSuccess(Location location) {
                    if (location != null) {
                        userlat = location.getLatitude();
                        userlng = location.getLongitude();
                    }
                }
            });

        mQueue_route = Volley.newRequestQueue(Directions.this);
        mQueue_restaurant = Volley.newRequestQueue(Directions.this);

//        jsonParseRestaurant();
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Directions.this);
//        String value = sp.getString("key", "");
//
//        System.out.println("THis is value from restaurant: " + value);

        jsonParseOrigin();
        jsonParseDestination(input_destination);
    }

    //----------------------------------------------------------- PARSING DATA FROM URL REQUEST ----------------------------------------------------------------------//
    private void jsonParseOrigin() {

//        String origin = "Karaiskaki 24";
        String url_origin = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyB2Kyy8TS2MGcHCYv68hK4-NPfASWe6LV0&location=37.985967, 23.721822&radius=1000&name=Karaiskaki 24,Athens";

        JsonObjectRequest request_origin = new JsonObjectRequest(Request.Method.GET, url_origin, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response_origin) {
                        try {
                            JSONArray jsonArray_origin = response_origin.getJSONArray("results");


                            for (int i = 0; i < jsonArray_origin.length(); i++) {
//                                JSONObject result_origin = jsonArray_origin.getJSONObject(i);

//                                id_result_origin = result_origin.getString("place_id");
                                id_result_origin = "EihQbC4gS2FyYWlza2FraSAyNCwgQXRoaW5hIDEwNCAzNywgR3JlZWNlIhoSGAoUChIJg_RXsyi9oRQRBAmADU6bvMcQGA";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        });
        mQueue_route.add(request_origin);
    }


    private String id_result_destination;

    public void jsonParseDestination(String value) {
        String latitude_dest = null;
        String longitude_dest = null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List addressList_destination;
        try {
            addressList_destination = geocoder.getFromLocationName(value, 1);

            if (addressList_destination != null && addressList_destination.size() > 0) {
                Address address_destination = (Address) addressList_destination.get(0);
                latitude_dest = String.valueOf(address_destination.getLatitude());
                longitude_dest = String.valueOf(address_destination.getLongitude());
            }
//            else {
//                start_view.append("No result");
//                for(int i = 0; i < restaurants.size(); i++){
//                    String x = restaurants.get(i);
//                    start_view.append(x);
//
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        final String url_destination = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyB2Kyy8TS2MGcHCYv68hK4-NPfASWe6LV0&location=" + latitude_dest + "," + longitude_dest + "&radius=1000&name=" + value + ",Athens";
        JsonObjectRequest request_destination = new JsonObjectRequest(Request.Method.GET, url_destination, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response_destination) {
                        try {
                            JSONArray jsonArray_destination = response_destination.getJSONArray("results");

                            for (int i = 0; i < jsonArray_destination.length(); i++) {
                                JSONObject result_destination = jsonArray_destination.getJSONObject(i);

                                id_result_destination = result_destination.getString("place_id");

                                String url_route = "https://maps.googleapis.com/maps/api/directions/json?origin=place_id:" + id_result_origin + "&destination=place_id:" + id_result_destination + "&transit_routing_preference=fewer_transfers&mode=transit&key=AIzaSyB2Kyy8TS2MGcHCYv68hK4-NPfASWe6LV0";
                                JsonObjectRequest request_route = new JsonObjectRequest(Request.Method.GET, url_route, (String) null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response_route) {
                                                try {
                                                    // Getting JSON Array route node
                                                    JSONArray routes = response_route.getJSONArray("routes");

                                                    // looping through All routes
                                                    for (int i = 0; i < routes.length(); i++) {
                                                        JSONObject r = routes.getJSONObject(i);

                                                        // Getting JSON Array legs node
                                                        JSONArray legs = r.getJSONArray("legs");

                                                        // looping through All legs
                                                        for (int j = 0; j < legs.length(); j++) {
                                                            JSONObject l = legs.getJSONObject(i);

                                                            // Getting JSON Array steps node
                                                            JSONArray steps = l.getJSONArray("steps");

                                                            int n = steps.length();
                                                            if (n > 3) {
                                                                JSONObject s = steps.getJSONObject(1);
                                                                JSONObject s2 = steps.getJSONObject(3);

                                                                if (s.has("transit_details") && s2.has("transit_details")) {
                                                                    JSONObject transit_details = s.getJSONObject("transit_details");
                                                                    JSONObject transit_details2 = s2.getJSONObject("transit_details");

                                                                    if (transit_details.has("arrival_stop") && transit_details.has("departure_stop") || transit_details2.has("arrival_stop") && transit_details2.has("departure_stop")) {
                                                                        //first step
                                                                        JSONObject departure_stop = transit_details.getJSONObject("departure_stop");
                                                                        JSONObject arrival_stop = transit_details.getJSONObject("arrival_stop");
                                                                        arrival_stop_name = arrival_stop.getString("name");
                                                                        departure_stop_name = departure_stop.getString("name");

                                                                        if (arrival_stop.has("location")) {
                                                                            JSONObject location = arrival_stop.getJSONObject("location");
                                                                            arr_lat = location.getDouble("lat");
                                                                            arr_lng = location.getDouble("lng");
                                                                        }
                                                                        if (departure_stop.has("location")) {
                                                                            JSONObject location = departure_stop.getJSONObject("location");
                                                                            dpt_lat = location.getDouble("lat");
                                                                            dpt_lng = location.getDouble("lng");
                                                                        }

                                                                        //second step
                                                                        JSONObject departure_stop2 = transit_details2.getJSONObject("departure_stop");
                                                                        JSONObject arrival_stop2 = transit_details2.getJSONObject("arrival_stop");
                                                                        arrival_stop_name2 = arrival_stop.getString("name");
                                                                        departure_stop_name2 = departure_stop2.getString("name");

                                                                        if (arrival_stop2.has("location")) {
                                                                            JSONObject location2 = arrival_stop2.getJSONObject("location");
                                                                            arr_lat2 = location2.getDouble("lat");
                                                                            arr_lng2 = location2.getDouble("lng");
                                                                        }
                                                                        if (departure_stop.has("location")) {
                                                                            JSONObject location2 = departure_stop2.getJSONObject("location");
                                                                            dpt_lat2 = location2.getDouble("lat");
                                                                            dpt_lng2 = location2.getDouble("lng");
                                                                        }
//                                                                        start_view.append("Because the trip uses two transportations it is still not available");
//                                                                        start_view.append(" Departure stop : " + departure_stop_name + "\n\n");
//                                                                        start_view.append(" Arrival stop : " + arrival_stop_name + "\n\n");
                                                                    }
                                                                }
                                                            } else {
                                                                JSONObject s = steps.getJSONObject(1);
                                                                if (s.has("distance") && s.has("duration")) {
                                                                    JSONObject dis = s.getJSONObject("distance");
                                                                    JSONObject dur = s.getJSONObject("duration");

                                                                    distance = dis.getString("text");
                                                                    duration = dur.getString("text");

                                                                }
                                                                if (s.has("transit_details")) {
                                                                    JSONObject transit_details = s.getJSONObject("transit_details");

                                                                    if (transit_details.has("arrival_stop") && transit_details.has("departure_stop")) {
                                                                        JSONObject departure_stop = transit_details.getJSONObject("departure_stop");
                                                                        JSONObject arrival_stop = transit_details.getJSONObject("arrival_stop");


                                                                        arrival_stop_name = arrival_stop.getString("name");
                                                                        departure_stop_name = departure_stop.getString("name");

                                                                        if (transit_details.has("arrival_time") && transit_details.has("departure_time")) {
                                                                            JSONObject departuretime = transit_details.getJSONObject("departure_time");
                                                                            JSONObject arrivaltime = transit_details.getJSONObject("arrival_time");

                                                                            arrival_time = arrivaltime.getString("text");
                                                                            departure_time = departuretime.getString("text");

                                                                        }
                                                                        if (arrival_stop.has("location")) {
                                                                            JSONObject location = arrival_stop.getJSONObject("location");
                                                                            arr_lat = location.getDouble("lat");
                                                                            arr_lng = location.getDouble("lng");
                                                                        }
                                                                        if (departure_stop.has("location")) {
                                                                            JSONObject location = departure_stop.getJSONObject("location");
                                                                            dpt_lat = location.getDouble("lat");
                                                                            dpt_lng = location.getDouble("lng");
                                                                        }
//                                                                        start_view.append(" Διακοπή αναχώρησης : " + departure_stop_name + "\n");
//                                                                        start_view.append(" Ωρα αναχώρησης : " + departure_time + "\n\n");
//
//                                                                        start_view.append(" Άφιξη άφιξης : " + arrival_stop_name + "\n");
//                                                                        start_view.append(" Ωρα άφιξης : " + arrival_time + "\n\n");
//
//                                                                        start_view.append(" Απόσταση : " + distance + "\n");
//                                                                        start_view.append(" Διάρκεια : " + duration + "\n\n");
//---------------------------------------------------------------------- TEXT TO SPEECH -----------------------------------------------------------------//
                                                                        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                                                            @Override
                                                                            public void onInit(int status) {
                                                                                if (status != TextToSpeech.ERROR) {
                                                                                    Locale gr = new Locale("el", "GR");
                                                                                    t1.setLanguage(gr);
                                                                                }
                                                                            }
                                                                        });

                                                                        final Handler handler = new Handler();
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                t1.speak("\n" +
                                                                                        "Η στάση αναχώρησης είναι " + departure_stop_name + "\n" +
                                                                                        "Ωρα αναχώρησης\n " + departure_time +
                                                                                        "Η στάση άφιξής σας είναι " + arrival_stop_name +
                                                                                        "Ωρα άφιξης " + arrival_time +
                                                                                        "\n" +
                                                                                        "απόσταση " + distance +
                                                                                        "\n" +
                                                                                        "Διάρκεια ταξιδιού " + duration, TextToSpeech.QUEUE_FLUSH, null);
                                                                            }
                                                                        }, 100);

//------------------------------------------------------------------ TEXT TO SPEECH END ----------------------------------------------------------------//

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (
                                                        JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // As of f605da3 the following should work
                                        NetworkResponse response = error.networkResponse;
                                        if (error instanceof ServerError && response != null) {
                                            try {
                                                String res = new String(response.data,
                                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                                // Now you can use any deserializer to make sense of data
                                                JSONObject obj = new JSONObject(res);
                                            } catch (UnsupportedEncodingException e1) {
                                                // Couldn't properly decode data to string
                                                e1.printStackTrace();
                                            } catch (JSONException e2) {
                                                // returned data is not JSONObject?
                                                e2.printStackTrace();
                                            }
                                        }
                                    }
                                });

                                // Add the request to the RequestQueue.
                                mQueue_route.add(request_route);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        });
        mQueue_route.add(request_destination);
    }

    public void jsonParseRestaurant() {
        String url_restaurant = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.005020,23.745547&radius=300&type=restaurant&key=AIzaSyB2Kyy8TS2MGcHCYv68hK4-NPfASWe6LV0";

        JsonObjectRequest request_restaurant = new JsonObjectRequest(Request.Method.GET, url_restaurant, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response_restaurant) {
                        try {
                            JSONArray restaurantJSONArray = response_restaurant.getJSONArray("results");
                            for (int i = 0; i < restaurantJSONArray.length(); i++) {
                                if (restaurantJSONArray.getJSONObject(i).has("opening_hours")) {
                                    String restaurant_name = restaurantJSONArray.getJSONObject(i).getString("name");
                                    ArrayList<String> restaurantList = new ArrayList<>();
                                    restaurantList.add(restaurant_name);


                                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Directions.this);
                                    SharedPreferences.Editor restaurant = sp.edit();
//                                    for (int j = 0; j < restaurantList.size(); j++) {
//                                        restaurant.putString(String.valueOf(j), restaurantList.get(j));
//                                    }
                                    restaurant.apply();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        });
        mQueue_restaurant.add(request_restaurant);
    }

    @Override
    public void onRestaurantSuccess(List<RestaurantNote> restaurants) {
        for (RestaurantNote route : restaurants) {
            String name = route.getName();
        }
    }

//    private void viewAll() {
//
//        Cursor res = myDb.getAllData();
//        if (res.getCount() == 0) {
//            // show message
//            showMessage("Error", "Nothing found");
//            return;
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()) {
//            buffer.append("Id :" + res.getString(0) + "\n");
//            buffer.append("Name :" + res.getString(1) + "\n");
//            buffer.append("Surname :" + res.getString(2) + "\n");
//            buffer.append("Marks :" + res.getString(3) + "\n\n");
//        }
//
//        // Show all data
//        showMessage("Data", buffer.toString());
//    }
//
//    private void AddData() {
//        myDb.insertData("Jasna","Djordjevic","60");
//
//    }

//    public void showMessage(String title, String Message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(Directions.this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(Message);
//        builder.show();
//    }

//    public boolean isInsideFirstRadius() {
//        double radiusInMeters = 500.0; //1 KM = 1000 Meter
//
//        float[] result = new float[1];
//        double stationPositionlat = arr_lat;
//        double stationPositionlng = arr_lng;
////37.970620, 23.710405
//        double currentPositionlat = 37.970620;
//        double currentPositionlng = 23.710405;
////        double currentPositionlat = userlat;
////        double currentPositionlng = userlng;
//        Location.distanceBetween(currentPositionlat, currentPositionlng, stationPositionlat, stationPositionlng, result);
//
//        if (result[0] > radiusInMeters) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean isInsideSecondRadius() {
//        double radiusInMeters = 1000.0; //1 KM = 1000 Meter
//
//        float[] result = new float[1];
//        double stationPositionlat = arr_lat2;
//        double stationPositionlng = arr_lng2;
//
//        double currentPositionlat = 37.964388;
//        double currentPositionlng = 23.722711;
//        Location.distanceBetween(currentPositionlat, currentPositionlng, stationPositionlat, stationPositionlng, result);
//
//        if (result[0] > radiusInMeters) {
//            return false;
//        } else {
//            return true;
//        }
//    }
}