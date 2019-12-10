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
import de.htwg.smartplant.main.recycler.PlantManageAdapter;
import de.htwg.smartplant.main.recycler.PlantsAdapter;
import de.htwg.smartplant.plantdetail.PlantDetailObjectModel;

public class AnalyseFragment extends Fragment {
    private Activity activity;
    private View view;
    private RecyclerView recyclerView;
    private PlantManageAdapter plantsAdapter;

    public AnalyseFragment() {
        // Required empty public constructor
    }

    public AnalyseFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_analyse, container, false);
        setUpRecycler(view);
        return view;
    }

    private void setUpRecycler(View view){
        recyclerView = view.findViewById(R.id.analyse_recycler_plants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addPlantsData(List<PlantDetailObjectModel> plants) {
        plantsAdapter = new PlantManageAdapter(plants, this.activity);
        recyclerView.setAdapter(plantsAdapter);
        plantsAdapter.notifyDataSetChanged();
    }

}
