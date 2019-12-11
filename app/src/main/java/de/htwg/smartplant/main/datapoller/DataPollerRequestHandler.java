package de.htwg.smartplant.main.datapoller;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.htwg.smartplant.main.MainView;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.main.recycler.adapters.ManagePlantsAdapter;
import de.htwg.smartplant.main.recycler.adapters.YourPlantsAdapter;
import de.htwg.smartplant.rest.HttpNotifier;

public class DataPollerRequestHandler implements HttpNotifier {

    private final MainView view;
    private List<Plant> oldPlants = new ArrayList<>();

    public DataPollerRequestHandler(ManagePlantsAdapter managePlantsAdapter, YourPlantsAdapter yourPlantsAdapter, MainView view) {
        this.view = view;
    }
    
    @Override public void showRetry() { }

    @Override
    public void showFailure(String errorResponse) {
        Log.e("DataPollerHandlerError", errorResponse);
    }

    @Override
    public void showSuccess(JSONObject response) {
        try {
            JSONArray plants = (JSONArray) response.get("payload");
            List<Plant> newPlants = Plant.createPlantListFromJSON(plants);

            if(newPlants.size() != oldPlants.size()) {
                view.setPlants(newPlants);
                oldPlants = newPlants;
            }
        } catch(Exception e){
            //showException(e);
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
