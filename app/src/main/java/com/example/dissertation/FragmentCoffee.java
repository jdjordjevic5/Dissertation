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
 * Use the {@link FragmentCoffee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCoffee extends Fragment {
    private static final String ARG_PARAM1 = "coffee";

    private String name;

    public FragmentCoffee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Coffee.
     */
    static FragmentCoffee newInstance(String key, String param1) {
        //PACK DATA IN A BUNDLE
        FragmentCoffee fragmentCoffee = new FragmentCoffee();
        Bundle args = new Bundle();
        args.putString(key, param1);
        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        fragmentCoffee.setArguments(args);
        return fragmentCoffee;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coffee, container, false);
        LinearLayout layout = view.findViewById(R.id.layoutCoffee);
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