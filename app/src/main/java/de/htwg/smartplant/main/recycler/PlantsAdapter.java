package de.htwg.smartplant.main.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import de.htwg.smartplant.R;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsViewHolder> {

    private List<String> plants = Arrays.asList();

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public PlantsViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public PlantsAdapter(List<String> dataSet) {
        this.plants = dataSet;
    }

    public void updateData(List<String> data) {
        this.plants = data;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_row, parent, false);

        PlantsViewHolder vh = new PlantsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder plantsViewHolder, int i) {
        plantsViewHolder.textView.setText(plants.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }
}
