package de.htwg.smartplant.main.recycler;

import android.app.Activity;
import android.content.Intent;
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
import de.htwg.smartplant.login.LoginView;
import de.htwg.smartplant.main.MainActivity;
import de.htwg.smartplant.plantdetail.PlantDetailView;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsViewHolder> {

    private final Activity activity;
    private List<Integer> waterValues;
    private List<Integer> plantTypes;
    private List<String> plantIds;

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public TextView textView;
        public View view;
        public ImageView imageView;
        public ProgressBar waterValue;
        public LinearLayout plantContainer;

        public PlantsViewHolder(View v) {
            super(v);
            view = v ;
            //textView = view.findViewById(R.id.plant_row_text);
            imageView = view.findViewById(R.id.plant_row_image);
            waterValue = view.findViewById(R.id.plant_row_watervalue);
            plantContainer = view.findViewById(R.id.plant_container);
        }
    }

    public PlantsAdapter(List<String> plantIds, List<Integer> waterValues, List<Integer> plantTypes, Activity activity) {
        this.plantIds = plantIds;
        this.waterValues = waterValues;
        this.plantTypes = plantTypes;
        this.activity = activity;
    }

    public void updateData(List<String> plantIds, List<Integer> waterValues, List<Integer> plantTypes) {
        this.waterValues = waterValues;
        this.plantIds = plantIds;
        this.plantTypes = plantTypes;
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
        //plantsViewHolder.textView.setText(plantIds.get(i));
        plantsViewHolder.imageView.setImageResource(getImageOfPlant(plantTypes.get(i)));
        styleProgressBar(waterValues.get(i), plantsViewHolder.waterValue);

        plantsViewHolder.plantContainer.setOnClickListener(v -> {
            activity.finish();
            Intent plantDetailView = new Intent(activity, PlantDetailView.class);
            activity.startActivity(plantDetailView);
        });
    }

    private int getImageOfPlant(Integer plantType) {
        switch(plantType) {
            case 0: return R.drawable.strawberry;
            case 1: return R.drawable.raspberry;
            case 2: return R.drawable.cactus;
            case 3: return R.drawable.potatoe;
            case 4: return R.drawable.tomato;
            case 5: return R.drawable.onion;
            case 6: return R.drawable.coal;
            case 7: return R.drawable.cucumber;
            case 8: return R.drawable.grape;
            case 9: return R.drawable.carrot;
            default : return R.drawable.plant;
        }
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

    @Override
    public int getItemCount() {
        return plantIds.size();
    }
}
