package de.htwg.smartplant.main.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.htwg.smartplant.main.PlantModel;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsViewHolder> {

    private List<PlantModel> plants;

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public PlantsViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public PlantsAdapter(List<PlantModel> dataSet) {
        this.plants = dataSet;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder plantsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
