package de.htwg.smartplant.main.recycler.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import de.htwg.smartplant.R;
import de.htwg.smartplant.Utils;
import de.htwg.smartplant.main.MainPresenter;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.HttpManager;
import de.htwg.smartplant.rest.jsonmodels.User;

import static de.htwg.smartplant.rest.HttpManager.BASE_URL;

public class ManagePlantsAdapter extends RecyclerView.Adapter<ManagePlantsAdapter.PlantManageViewHolder> {

    private final MainPresenter.IMainActivity mainActivity;
    private final YourPlantsAdapter yourPlantsAdapter;
    private final User user;

    private List<Plant> plants;

    public static class PlantManageViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView macLabel;
        private final ImageView plantImage;
        private final Button deleteButton;
        private final Button waterButton;

        public PlantManageViewHolder(View v) {
            super(v);
            view = v ;
            macLabel = view.findViewById(R.id.macLabel);
            plantImage = view.findViewById(R.id.plant_row_image);
            deleteButton = view.findViewById(R.id.deleteButton);
            waterButton = view.findViewById(R.id.waterButton);
        }
    }

    public ManagePlantsAdapter(List<Plant> plants, MainPresenter.IMainActivity mainActivity,
                               User user, YourPlantsAdapter yourPlantsAdapter) {
        this.plants = plants;
        this.mainActivity = mainActivity;
        this.user = user;
        this.yourPlantsAdapter = yourPlantsAdapter;
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
        int image = Utils.getImageOfPlant(plants.get(i).getPlantType());

        plantsViewHolder.macLabel.setText("MAC: " + plants.get(i).getMac());
        plantsViewHolder.plantImage.setImageResource(image);

        plantsViewHolder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(mainActivity.getActivity())
                    .setTitle("Löschen")
                    .setMessage("Wirklich löschen?")
                    .setPositiveButton("Ja", (dialog, which) -> deletePlant(plants.get(i).getId(), i))
                    .setNegativeButton("Nein", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        plantsViewHolder.waterButton.setOnClickListener(v -> {

        });
    }

    private void deletePlant(String id, int deletedIndex) {
        this.mainActivity.getMainPresenter().sendDeletePlantRequest(id);

        plants.remove(deletedIndex);
        notifyItemRemoved(deletedIndex);
        yourPlantsAdapter.notifyItemRemoved(deletedIndex);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }
}
