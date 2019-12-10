package de.htwg.smartplant.main.recycler;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.plantdetail.PlantDetailObjectModel;

public class PlantManageAdapter extends RecyclerView.Adapter<PlantManageAdapter.PlantManageViewHolder> {

    private final Activity activity;

    private List<PlantDetailObjectModel> plantDetailObjectModels;

    public static class PlantManageViewHolder extends RecyclerView.ViewHolder {


        private final View view;
        private final LinearLayout plantContainer;
        private final Button button;

        public PlantManageViewHolder(View v) {
            super(v);
            view = v ;
            plantContainer = view.findViewById(R.id.plant_container);
            button = view.findViewById(R.id.testB);
        }
    }

    public PlantManageAdapter(List<PlantDetailObjectModel> plantDetailObjectModels, Activity activity) {
        this.plantDetailObjectModels = plantDetailObjectModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlantManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.plant_manage_row, parent, false);
        return new PlantManageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantManageViewHolder plantsViewHolder, int i) {
        plantsViewHolder.button.setText("?!?!");
        /*
        plantsViewHolder.plantContainer.setOnClickListener(v -> {
            activity.finish();
            Intent plantDetailView = new Intent(activity, PlantDetailView.class);

            plantDetailObjectModels.get(i).setUser(plantsViewHolder.userName);
            plantDetailView.putExtra("plant", plantDetailObjectModels.get(i));

            activity.startActivity(plantDetailView);
        });
        */
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
