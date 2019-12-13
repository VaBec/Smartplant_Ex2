package de.htwg.smartplant.main.recycler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.main.MainView;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.main.recycler.adapters.ManagePlantsAdapter;
import de.htwg.smartplant.rest.jsonmodels.User;

public class ManagePlantsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ManagePlantsAdapter managePlantsAdapter;
    private MainView mainView;

    public ManagePlantsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plants_manage, container, false);
        setUpRecycler(view);
        return view;
    }

    private void setUpRecycler(View view){
        recyclerView = view.findViewById(R.id.analyse_recycler_plants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private boolean created = false;

    public void addPlantsData(List<Plant> plants, User user, YourPlantsFragment yourPlantsFragment, boolean isOnline) {
        if(!created) {
            managePlantsAdapter = new ManagePlantsAdapter(plants, mainView, user, yourPlantsFragment.getYourPlantsAdapter(), isOnline);
            recyclerView.setAdapter(managePlantsAdapter);
            created = true;
        } else {
            managePlantsAdapter.updatePlants(plants);
            managePlantsAdapter.notifyDataSetChanged();
        }
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}
