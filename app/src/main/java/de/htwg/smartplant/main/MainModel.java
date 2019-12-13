package de.htwg.smartplant.main;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.util.List;

import de.htwg.smartplant.Storage;
import de.htwg.smartplant.rest.HttpManager;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;

import static de.htwg.smartplant.rest.HttpManager.RequestType.DELETE;

public class MainModel {

    private final int SLEEP_TIME = 5000;

    private final User user;
    private final MainPresenter.IMainActivity mainActivity;
    private final MainPresenter mainPresenter;
    private final boolean isOnline;
    private Thread pollingThread;

    public MainModel(MainPresenter mainPresenter, User user, MainPresenter.IMainActivity mainActivity, boolean isOnline) {
        this.mainPresenter = mainPresenter;
        this.mainActivity = mainActivity;
        this.user = user;
        this.isOnline = isOnline;
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
                    if (isOnline) {
                        refresh.post(this::requestPlants);
                        Thread.sleep(SLEEP_TIME);
                    } else {
                        // Fire dummy HTTP-Response, since user is offline.
                        Thread.sleep(SLEEP_TIME);
                        List<Plant> plants = Storage.getPlantsFromStorage(this.user.getUserName(), this.mainActivity.getContext());
                        if (plants.size() > 0) {
                            this.mainPresenter.updatePlants(plants);
                        } else {
                            this.mainPresenter.showToast("Du hast noch keine Pflanzen!");
                        }

                        break;
                    }
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
        HttpManager.requestPlants(this.user.getUserName(), this.mainActivity.getContext(), mainPresenter);
    }
}
