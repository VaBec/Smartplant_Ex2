package de.htwg.smartplant.main.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.main.MainPresenter;
import de.htwg.smartplant.main.recycler.PlantsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlantsFragment extends Fragment implements MainPresenter.IPlantsView {

private View view;
private Activity activity;
private RecyclerView recyclerView;
PlantsAdapter plantsAdapter;

private List<Object> data;
    public PlantsFragment() {
        // Required empty public constructor
    }

    public PlantsFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plants, container, false);
        setUpRecycler(view);
        return view;
    }

    private void setUpRecycler(View view){
        recyclerView = view.findViewById(R.id.recycler_plants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addPlantsData(List<String> plants, List<Integer> waterValues, List<Integer> plantTypes) {
        plantsAdapter = new PlantsAdapter(plants, waterValues, plantTypes, this.activity);
        recyclerView.setAdapter(plantsAdapter);
        plantsAdapter.notifyDataSetChanged();
    }
}
