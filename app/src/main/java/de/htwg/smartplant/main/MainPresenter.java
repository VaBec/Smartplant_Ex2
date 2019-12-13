package de.htwg.smartplant.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.htwg.smartplant.Storage;
import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;

public class MainPresenter implements HttpNotifier {

    private final User user;
    private IMainActivity mainActivity;
    private MainModel mainModel;

    private List<Plant> oldPlants = new ArrayList<>();

    public MainPresenter(IMainActivity mainActivity, User user, boolean isOnline) {
        this.mainActivity = mainActivity;
        this.mainModel = new MainModel(this, user, mainActivity, isOnline);
        this.user = user;
    }

    public void showException(Exception e) {
        mainActivity.showToast(e.getMessage());
    }

    public void startPollingTask() {
        this.mainModel.startPollingTask();
    }

    @Override public void showRetry() { }

    @Override
    public void showFailure(String errorMessage) {
        this.mainActivity.showToast(errorMessage);
    }

    @Override
    public void showSuccess(JSONObject response) {
        if(response.toString().contains("already exists")) {
            this.showToast("Gieß-Auftrag existiert bereits!");
        } else if(response.toString().contains("Successfully added task for mac")) {
            this.showToast("Gieß-Auftrag wurde hinterlegt!");
        } else {
            if (response.toString().contains("deleted")) {
                this.mainActivity.showToast("Pflanze erfolgreich gelöscht!");
            } else {
                try {
                    JSONArray plants = (JSONArray) response.get("payload");
                    this.updatePlants(Plant.createPlantListFromJSON(plants));
                } catch (Exception e) {
                    this.mainActivity.showToast(e.getMessage());
                }
            }
        }
    }

    public void updatePlants(List<Plant> newPlants) {
        boolean shouldUpdate = newPlants.size() != oldPlants.size();

        if(!shouldUpdate) {
            for(int i=0 ; i<oldPlants.size() ; i++) {
                if(oldPlants.get(i).getWaterValue() != newPlants.get(i).getWaterValue()) {
                    shouldUpdate = true;
                    break;
                }
            }
        }

        if(shouldUpdate) {
            mainActivity.setPlants(newPlants);
            oldPlants = newPlants;

            Storage.savePlantsToStorage(newPlants, this.mainActivity.getContext(), this.user.getUserName());
        }
    }

    @Override public void showStart() { }

    public void stopPolling() {
        this.mainModel.stopPollingTask();
    }

    public void sendDeletePlantRequest(String id) {
        this.mainModel.sendDeletePlantRequest(id);
    }

    public void sendWateringRequest(String mac) {
        this.mainModel.sendWateringRequest(mac);
    }

    public void showToast(String text) {
        this.mainActivity.showToast(text);
    }

    public interface IMainActivity{
        void setupTabs();
        void showToast(String message);
        void setPlants(List<Plant> plants);
        MainPresenter getMainPresenter();
        Context getContext();
        AppCompatActivity getActivity();
    }
}
