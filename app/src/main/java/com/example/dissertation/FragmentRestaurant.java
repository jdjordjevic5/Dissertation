package com.example.dissertation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRestaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRestaurant extends Fragment {
    private static final String ARG_PARAM1 = "restaurant";
    private String name;

    public FragmentRestaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Restaurant.
     */
    public static FragmentRestaurant newInstance(String key, String param1) {
        //PACK DATA IN A BUNDLE
        FragmentRestaurant fragmentRestaurant = new FragmentRestaurant();
        Bundle args = new Bundle();
        args.putString(key, param1);
        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        fragmentRestaurant.setArguments(args);
        return fragmentRestaurant;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        doSomething();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String value = sp.getString("key", "");
//        int size = prefs.getInt("arrayName_size", 0);
        ArrayList<String> array = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            String value = prefs.getString("arrayName", "");
        array.add("fsada");
        array.add("aaaaa");
        array.add("vvvv");
//        }


        System.out.println("ARRAY " + value);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_items, array);
        ListView listView = view.findViewById(R.id.restaurantList);
        listView.setAdapter(arrayAdapter);

        return view;
    }


    private void doSomething() {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.005020,23.745547&radius=300&type=restaurant&key=AIzaSyB2Kyy8TS2MGcHCYv68hK4-NPfASWe6LV0";

        JSONObject jsonData;
        try {
            jsonData = new JSONObject(url);
            JSONArray jsonRestaurants = jsonData.getJSONArray("results");
            for (int i = 0; i < jsonRestaurants.length(); i++) {
                JSONObject jsonRoute = jsonRestaurants.getJSONObject(i);

                if (jsonRoute.has("opening_hours")) {
//                String name_ = jsonRoute.getString("name");
                    ArrayList<String> array = new ArrayList<>();
                    array.add("heeey");
                    array.add("how ");
                    array.add("are you?");

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor restaurant = sp.edit();
//                    for(int j = 0; j < array.size(); j++) {
//                        String value = array.get(j);
                    restaurant.putString("key", "heeeeeey");
                    restaurant.apply();
//                    }
//                editor.putInt("arrayName_size", array.size());
//                for (int j = 0; j < array.size(); j++) {
//                    editor.putString("arrayName", "hheey");
//                }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}