package de.htwg.smartplant.start;

import android.content.Context;

import org.json.JSONObject;

import de.htwg.smartplant.rest.jsonmodels.User;
import de.htwg.smartplant.rest.HttpManager;

import static de.htwg.smartplant.rest.HttpManager.RequestType.POST;
import static de.htwg.smartplant.rest.HttpManager.RequestType.PUT;

public class StartModel {

    private final User user;
    private final Context context;
    private final StartPresenter startPresenter;

    private boolean isLogin = false;

    public StartModel(User user, StartPresenter startPresenter, Context context) {
        this.user = user;
        this.startPresenter = startPresenter;
        this.context = context;
    }

    public void sendLoginRequest() {
        isLogin = true;

        try {
            JSONObject userJson = new JSONObject();

            userJson.put("username", user.getUserName());
            userJson.put("password", user.getPassWord());

            HttpManager.sendHtppRequest(POST, userJson, HttpManager.RequestUrl.LOGIN.create(), startPresenter, context);
        } catch(Exception e) {
            startPresenter.showException(e);
        }
    }

    public void sendRegisterRequest() {
        isLogin = false;

        try {
            JSONObject userJson = new JSONObject();

            userJson.put("username", user.getUserName());
            userJson.put("password", user.getPassWord());

            HttpManager.sendHtppRequest(PUT, userJson, HttpManager.RequestUrl.REGISTER.create(), startPresenter, context);
        } catch(Exception e) {
            startPresenter.showException(e);
        }
    }

    public boolean isLogin() {
        return this.isLogin;
    }
}
