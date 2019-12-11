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
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.HttpManager;

import static de.htwg.smartplant.rest.HttpManager.BASE_URL;

public class ManagePlantsAdapter extends RecyclerView.Adapter<ManagePlantsAdapter.PlantManageViewHolder> {

    private final Activity activity;
    private final YourPlantsAdapter yourPlantsAdapter;

    private List<Plant> plantDetailObjectModels;
    private String userName;
    private String password;

    public List<Plant> getPlants() {
        return plantDetailObjectModels;
    }

    public void updateData(List<Plant> plants) {
        this.plantDetailObjectModels = plants;
    }

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

    public ManagePlantsAdapter(List<Plant> plantDetailObjectModels, Activity activity,
                               String userName, String password, YourPlantsAdapter yourPlantsAdapter) {
        this.plantDetailObjectModels = plantDetailObjectModels;
        this.activity = activity;
        this.userName = userName;
        this.password = password;
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
        int image = Utils.getImageOfPlant(plantDetailObjectModels.get(i).getPlantType());

        plantsViewHolder.macLabel.setText("MAC: " + plantDetailObjectModels.get(i).getMac());
        plantsViewHolder.plantImage.setImageResource(image);

        plantsViewHolder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Löschen")
                    .setMessage("Wirklich löschen?")
                    .setPositiveButton("Ja", (dialog, which) -> deletePlant(
                            plantDetailObjectModels.get(i).getId(), userName, password, i
                    ))
                    .setNegativeButton("Nein", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        plantsViewHolder.waterButton.setOnClickListener(v -> {

        });
    }

    private void deletePlant(String id, String userName, String password, int deletedIndex) {
        try {
            JSONObject deletePlantJson = new JSONObject();

            deletePlantJson.put("id", id);
            deletePlantJson.put("username", userName);
            deletePlantJson.put("password", password);

            StringEntity entity = new StringEntity(deletePlantJson.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            AsyncHttpClient client = new AsyncHttpClient();
            client.setMaxRetriesAndTimeout(1, 5000);

            String url = BASE_URL + "deleteplant";
            client.delete(this.activity.getApplicationContext(), url,
                    entity,"application/json", new HttpManager(new HttpNotifier() {
                        @Override
                        public void showRetry() {
                            int db = 3;
                        }

                        @Override
                        public void showFailure(String response) {
                            int db = 3;
                        }

                        @Override
                        public void showSuccess(JSONObject response) {
                            plantDetailObjectModels.remove(deletedIndex);

                            notifyItemRemoved(deletedIndex);
                            yourPlantsAdapter.notifyItemRemoved(deletedIndex);
                        }

                        @Override
                        public void showStart() {
                            int db = 3;
                        }
                    }));
        } catch(Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return plantDetailObjectModels.size();
    }
}