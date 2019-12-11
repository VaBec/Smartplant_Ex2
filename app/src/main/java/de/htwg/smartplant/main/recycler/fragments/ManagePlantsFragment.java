package de.htwg.smartplant.main.recycler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.main.recycler.adapters.ManagePlantsAdapter;

public class ManagePlantsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ManagePlantsAdapter plantsManagedAdapter;

    public ManagePlantsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_analyse, container, false);
        setUpRecycler(view);
        return view;
    }

    private void setUpRecycler(View view){
        recyclerView = view.findViewById(R.id.analyse_recycler_plants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addPlantsData(List<Plant> plants, String userName, String password, YourPlantsFragment yourPlantsFragment) {
        plantsManagedAdapter = new ManagePlantsAdapter(plants, getActivity(), userName, password, yourPlantsFragment.getYourPlantsAdapter());
        recyclerView.setAdapter(plantsManagedAdapter);
        plantsManagedAdapter.notifyDataSetChanged();
    }
}
