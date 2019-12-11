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
import de.htwg.smartplant.main.MainPresenter;
import de.htwg.smartplant.main.MainView;
import de.htwg.smartplant.main.recycler.adapters.YourPlantsAdapter;
import de.htwg.smartplant.rest.jsonmodels.Plant;

public class YourPlantsFragment extends Fragment {

    private RecyclerView recyclerView;
    YourPlantsAdapter yourPlantsAdapter;
    private MainPresenter.IMainActivity mainView;

    public YourPlantsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants, container, false);
        setUpRecycler(view);
        return view;
    }

    private void setUpRecycler(View view){
        recyclerView = view.findViewById(R.id.recycler_plants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void addPlantsData(List<Plant> plants, String userName) {
        yourPlantsAdapter = new YourPlantsAdapter(plants, userName);
        recyclerView.setAdapter(yourPlantsAdapter);
        yourPlantsAdapter.notifyDataSetChanged();
    }

    public YourPlantsAdapter getYourPlantsAdapter() {
        return yourPlantsAdapter;
    }

    public void setMainView(MainPresenter.IMainActivity mainView) {
        this.mainView = mainView;
    }
}
