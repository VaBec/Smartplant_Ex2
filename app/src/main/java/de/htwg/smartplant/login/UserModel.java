package de.htwg.smartplant.login;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import de.htwg.smartplant.login.LoginPresenter;
import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.BASE_URL;

public class UserModel {

    public static String LOGIN_ENDPOINT = "login";

    private final String password;
    private final String name;
    private final LoginPresenter loginPresenter;

    public UserModel(String name, String password, LoginPresenter loginPresenter) {
        this.name = name;
        this.password = password;
        this.loginPresenter = loginPresenter;
    }

    public void sendLoginRequest() {
        RequestParams params = new RequestParams();
        params.add("username", name);
        params.add("password", password);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL + LOGIN_ENDPOINT, params , new RequestHandler(loginPresenter));
    }
}
