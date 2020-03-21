package com.example.dissertation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

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
        System.out.println("From newInstance in Restaurant " + param1);
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
            System.out.println("This is name in Restaurant" + name);
        }
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        LinearLayout layout = view.findViewById(R.id.layoutRestaurant);
        for (int i = 1; i <= 4; i++) {
            Button button = new Button(getActivity());
            button.setText(name);
            button.setPadding(5, 0, 5, 0);
            button.setTextSize(14f);
            layout.addView(button);
        }
        return view;
    }
}