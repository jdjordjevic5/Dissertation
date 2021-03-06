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
 * Use the {@link FragmentCultural#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCultural extends Fragment {
    private static final String ARG_PARAM1 = "cultural";

    private String name;

    public FragmentCultural() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Cultural.
     */
    // TODO: Rename and change types and number of parameters
    static FragmentCultural newInstance(String key, String param1) {
        System.out.println("From newInstance in Cultural " + param1);
        //PACK DATA IN A BUNDLE
        FragmentCultural fragmentCultural = new FragmentCultural();
        Bundle args = new Bundle();
        args.putString(key, param1);
        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        fragmentCultural.setArguments(args);
        return fragmentCultural;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            System.out.println("This is name in Cultural" + name);
        }
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cultural, container, false);
        LinearLayout layout = view.findViewById(R.id.layoutCultural);
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