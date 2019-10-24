package de.htwg.smartplant.data;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import de.htwg.smartplant.presenter.LoginPresenter;
import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.BASE_URL;

public class UserModel {

    public static String LOGIN_ENDPOINT = "getplant";

    private final String password;
    private final String name;
    private final LoginPresenter loginPresenter;

    public UserModel(String name, String password, LoginPresenter loginPresenter) {
        this.name = name;
        this.password = password;
        this.loginPresenter = loginPresenter;
    }

    public void login() {
        RequestParams params = new RequestParams();
        params.add("id", "2");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL + "getplant", params , new RequestHandler(loginPresenter));
    }
}
