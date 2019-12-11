package de.htwg.smartplant.main.recycler.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.Utils;
import de.htwg.smartplant.rest.jsonmodels.Plant;

public class YourPlantsAdapter extends RecyclerView.Adapter<YourPlantsAdapter.PlantsViewHolder> {

    private final Activity activity;
    private String userName;
    private String password;

    private List<Plant> plantDetailObjectModels;

    public void updateData(List<Plant> plants) {
        this.plantDetailObjectModels = plants;
    }

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView imageView;
        public ProgressBar waterValue;
        public TextView macLabel;
        public LinearLayout plantContainer;
        public String userName;

        public PlantsViewHolder(View v, String name) {
            super(v);
            view = v ;
            userName = name;
            imageView = view.findViewById(R.id.plant_row_image);
            waterValue = view.findViewById(R.id.plant_row_watervalue);
            plantContainer = view.findViewById(R.id.plant_container);
            macLabel = view.findViewById(R.id.macLabel);
        }
    }

    public YourPlantsAdapter(List<Plant> plantDetailObjectModels, Activity activity,
                             String userName, String password) {
        this.plantDetailObjectModels = plantDetailObjectModels;
        this.activity = activity;
        this.userName = userName;
        this.password = password;
    }

    public void removeFromList(int index) {
        this.plantDetailObjectModels.remove(index);
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.plant_row, parent, false);
        return new PlantsViewHolder(v, this.userName);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder plantsViewHolder, int i) {
        int image = Utils.getImageOfPlant(plantDetailObjectModels.get(i).getPlantType());
        plantsViewHolder.imageView.setImageResource(image);
        plantsViewHolder.macLabel.setText("MAC: " + plantDetailObjectModels.get(i).getMac());
        styleProgressBar(plantDetailObjectModels.get(i).getWaterValue(), plantsViewHolder.waterValue);
    }

    @Override
    public int getItemCount() {
        return plantDetailObjectModels.size();
    }

    private void styleProgressBar(Integer waterValue, ProgressBar waterValueProgressBar) {
        String colorString;
        if(waterValue >= 682) {
            colorString = "#2389da";    // Water blue
        } else if(waterValue >= 341) {
            colorString = "#ffae42";    // Warning orange
        } else {
            colorString = "#ff5042";   // Error red
        }

        waterValueProgressBar.getProgressDrawable().setColorFilter(
                Color.parseColor(colorString), android.graphics.PorterDuff.Mode.SRC_IN);

        waterValueProgressBar.setProgress(waterValue);
    }
}
