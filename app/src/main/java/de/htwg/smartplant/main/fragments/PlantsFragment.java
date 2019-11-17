package de.htwg.smartplant.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htwg.smartplant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlantsFragment extends Fragment {

private View view;

    public PlantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plants, container, false);
        return view;
    }

}
