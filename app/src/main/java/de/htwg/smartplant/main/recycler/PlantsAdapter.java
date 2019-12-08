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
import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.main.MainActivity;
import de.htwg.smartplant.plantdetail.PlantDetailObjectModel;
import de.htwg.smartplant.plantdetail.PlantDetailView;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantsViewHolder> {

    private final Activity activity;

    private List<PlantDetailObjectModel> plantDetailObjectModels;

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView imageView;
        public ProgressBar waterValue;
        public LinearLayout plantContainer;
        public String userName;

        public PlantsViewHolder(View v, String name) {
            super(v);
            view = v ;
            userName = name;
            imageView = view.findViewById(R.id.plant_row_image);
            waterValue = view.findViewById(R.id.plant_row_watervalue);
            plantContainer = view.findViewById(R.id.plant_container);
        }
    }

    public PlantsAdapter(List<PlantDetailObjectModel> plantDetailObjectModels, Activity activity) {
        this.plantDetailObjectModels = plantDetailObjectModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.plant_row, parent, false);
        return new PlantsViewHolder(v, MainActivity.user);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder plantsViewHolder, int i) {
        int image = getImageOfPlant(plantDetailObjectModels.get(i).getPlantType());
        plantsViewHolder.imageView.setImageResource(image);
        styleProgressBar(plantDetailObjectModels.get(i).getWaterValue(), plantsViewHolder.waterValue);

        plantsViewHolder.plantContainer.setOnClickListener(v -> {
            activity.finish();
            Intent plantDetailView = new Intent(activity, PlantDetailView.class);

            plantDetailObjectModels.get(i).setUser(plantsViewHolder.userName);
            plantDetailView.putExtra("plant", plantDetailObjectModels.get(i));

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
        return plantDetailObjectModels.size();
    }
}
