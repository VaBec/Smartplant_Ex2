package de.htwg.smartplant.main;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.htwg.smartplant.main.recycler.fragments.ManagePlantsFragment;
import de.htwg.smartplant.main.recycler.fragments.YourPlantsFragment;
import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;

public class MainPresenter implements HttpNotifier {

    private IMainActivity mainActivity;
    private Context context;
    private User user;
    private MainModel mainModel;


    public MainPresenter(IMainActivity mainActivity, Context context, User user) {
        this.user = user;
        this.mainActivity = mainActivity;
        this.context = context;
        this.mainModel = new MainModel(this, user, mainActivity);
    }

    public void showException(Exception e) {
        mainActivity.showToast(e.getMessage());
    }

    public void startPollingTask(YourPlantsFragment yourPlantsFragment, ManagePlantsFragment managePlantsFragment, IMainActivity mainActivity) {
        this.mainModel.startPollingTask();
    }

    @Override public void showRetry() { }

    @Override
    public void showFailure(String errorMessage) {
        this.mainActivity.showToast(errorMessage);
    }

    @Override
    public void showSuccess(JSONObject response) {
        String text;
        try {
            text = (String) response.get("payload");
        } catch (JSONException e) {
            text = e.getMessage();
        }

        this.mainActivity.showToast(text);
    }

    @Override public void showStart() { }

    public void stopPolling() {
        this.mainModel.stopPollingTask();
    }

    public interface IMainActivity{
        void setupTabs();
        void showToast(String message);
        void setPlants(List<Plant> plants);
        Context getContext();
    }
}
