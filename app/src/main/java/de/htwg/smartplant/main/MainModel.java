package de.htwg.smartplant.main;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.htwg.smartplant.Utils;
import de.htwg.smartplant.rest.HttpManager;
import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;

import static de.htwg.smartplant.rest.HttpManager.RequestType.DELETE;

public class MainModel implements HttpNotifier {

    private final int SLEEP_TIME = 250;
    private List<Plant> oldPlants = new ArrayList<>();

    private final User user;
    private final MainPresenter.IMainActivity mainActivity;
    private final MainPresenter mainPresenter;
    private Thread pollingThread;

    public MainModel(MainPresenter mainPresenter, User user, MainPresenter.IMainActivity mainActivity) {
        this.mainPresenter = mainPresenter;
        this.mainActivity = mainActivity;
        this.user = user;
    }

    public void sendDeletePlantRequest(String plantId) {
        try {
            JSONObject deleteUserJson = new JSONObject();

            deleteUserJson.put("id", plantId);
            deleteUserJson.put("username", user.getUserName());
            deleteUserJson.put("password", user.getPassWord());

            HttpManager.sendHtppRequest(DELETE, deleteUserJson, HttpManager.RequestUrl.DELETEPLANT.create(), mainPresenter, this.mainActivity.getContext());
        } catch(Exception e) {
            mainPresenter.showException(e);
        }
    }

    public void startPollingTask() {
        this.pollingThread = new Thread(() -> {
            while (true) {
                try {
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(() -> requestPlants());
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        pollingThread.start();
    }

    public void stopPollingTask() {
        if(this.pollingThread != null && !this.pollingThread.isInterrupted()) {
            this.pollingThread.interrupt();
        }
    }

    private void requestPlants() {
        HttpManager.requestPlants(this.user.getUserName(), this.mainActivity.getContext(), this);
    }

    @Override public void showRetry() { }

    @Override
    public void showFailure(String errorResponse) {
        this.mainActivity.showToast(errorResponse);
    }

    @Override
    public void showSuccess(JSONObject response) {
        try {
            JSONArray plants = (JSONArray) response.get("payload");
            List<Plant> newPlants = Plant.createPlantListFromJSON(plants);

            if(newPlants.size() != oldPlants.size()) {
                mainActivity.setPlants(newPlants);
                oldPlants = newPlants;
            }
        } catch(Exception e){
            this.mainActivity.showToast(e.getMessage());
        }
    }

    @Override public void showStart(){}
}
