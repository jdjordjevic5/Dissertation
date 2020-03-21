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
 * Use the {@link FragmentDirection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDirection extends Fragment {
    private static final String ARG_PARAM1 = "direction";

    private String name;

    public FragmentDirection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Direction.
     */
    static FragmentDirection newInstance(String key, String param1) {
        //PACK DATA IN A BUNDLE
        FragmentDirection fragmentDirection = new FragmentDirection();
        Bundle args = new Bundle();
        args.putString(key, param1);
        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        fragmentDirection.setArguments(args);
        return fragmentDirection;
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
        View view = inflater.inflate(R.layout.fragment_direction, container, false);
        LinearLayout layout = view.findViewById(R.id.layoutDirection);
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