package de.htwg.smartplant.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htwg.smartplant.R;
import de.htwg.smartplant.main.recycler.PlantsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlantsFragment extends Fragment {

private View view;
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;
private PlantsAdapter plantsAdapter;

    public PlantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plants, container, false);
        recyclerView = (RecyclerView)container.findViewById(R.id.recycler_plants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

}
