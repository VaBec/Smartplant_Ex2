package de.htwg.smartplant.main.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import de.htwg.smartplant.R;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsViewHolder> {

    private List<String> plants = Arrays.asList();

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public View view;
        public ImageView imageView;
        public PlantsViewHolder(View v) {
            super(v);
            //textView = v;
            //view = v;
            view = v ;
            textView=view.findViewById(R.id.plant_row_text);
            imageView = view.findViewById(R.id.plant_row_image);
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
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.plant_row, parent, false);
        PlantsViewHolder vh = new PlantsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder plantsViewHolder, int i) {
        plantsViewHolder.textView.setText(plants.get(i));
        plantsViewHolder.imageView.setImageResource(R.drawable.plant);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }
}
