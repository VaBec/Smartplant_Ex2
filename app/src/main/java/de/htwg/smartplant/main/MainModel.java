package de.htwg.smartplant.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONObject;

import de.htwg.smartplant.Utils;
import de.htwg.smartplant.rest.HttpManager;
import de.htwg.smartplant.rest.jsonmodels.User;

import static de.htwg.smartplant.rest.HttpManager.RequestType.DELETE;

public class MainModel {

    private final User user;
    private final Context context;
    private final MainPresenter mainPresenter;

    public MainModel(MainPresenter mainPresenter, User user, Context context) {
        this.mainPresenter = mainPresenter;
        this.context = context;
        this.user = user;
    }

    public void sendDeletePlantRequest(String plantId) {
        try {
            JSONObject deleteUserJson = new JSONObject();

            deleteUserJson.put("id", plantId);
            deleteUserJson.put("username", user.getUserName());
            deleteUserJson.put("password", user.getPassWord());

            HttpManager.sendHtppRequest(DELETE, deleteUserJson, HttpManager.RequestUrl.LOGIN.create(), mainPresenter, context);
        } catch(Exception e) {
            mainPresenter.showException(e);
        }
    }

    public void getUserPlants(User user){
        Utils.requestPlants(user.getUserName(), context, mainPresenter);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
