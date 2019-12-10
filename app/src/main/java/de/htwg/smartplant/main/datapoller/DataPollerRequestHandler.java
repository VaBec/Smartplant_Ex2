package de.htwg.smartplant.main.datapoller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.htwg.smartplant.jsonmodels.Plant;
import de.htwg.smartplant.main.recycler.ManagePlantsAdapter;
import de.htwg.smartplant.main.recycler.YourPlantsAdapter;
import de.htwg.smartplant.rest.HttpNotifier;

public class DataPollerRequestHandler implements HttpNotifier {

    private final ManagePlantsAdapter MANAGE_PLANTS_ADAPTER;
    private final YourPlantsAdapter YOUR_PLANTS_ADAPTER;

    public DataPollerRequestHandler(ManagePlantsAdapter managePlantsAdapter, YourPlantsAdapter yourPlantsAdapter) {
        this.MANAGE_PLANTS_ADAPTER = managePlantsAdapter;
        this.YOUR_PLANTS_ADAPTER = yourPlantsAdapter;
    }
    
    @Override public void showRetry() { }

    @Override
    public void showFailure(JSONObject response) {
        Log.e("DataPollerHandlerError", response.toString());
    }

    @Override
    public void showSuccess(JSONObject response) {
        JSONArray array = null;
        try {
            array = (JSONArray) response.get("payload");
            List<Plant> newPlants = Plant.createPlantListFromJSON(array);
            List<Plant> oldPlants = MANAGE_PLANTS_ADAPTER.getPlants();

            if(dataChanged(newPlants, oldPlants)) {
                MANAGE_PLANTS_ADAPTER.updateData(newPlants);
                MANAGE_PLANTS_ADAPTER.notifyDataSetChanged();

                YOUR_PLANTS_ADAPTER.updateData(newPlants);
                YOUR_PLANTS_ADAPTER.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            Log.e("DataPollerHandlerError", response.toString());
        }
    }

    @Override
    public void showStart() {
        int db = 3;
    }

    private boolean dataChanged(List<Plant> plants, List<Plant> currentPlants) {
        if(plants.size() != currentPlants.size()) {
            return true;
        }

        for(int i=0 ; i<plants.size() ; i++) {
            if(plants.get(i).getWaterValue() != currentPlants.get(i).getWaterValue()) {
                return true;
            }
        }

        return false;
    }
}
