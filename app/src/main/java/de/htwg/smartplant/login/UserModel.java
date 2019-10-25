package de.htwg.smartplant.login;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.BASE_URL;

public class UserModel {

    public enum RequestType {
        LOGIN,
        REGISTER,
        NONE;

        private String retryMessage;
        private String errorMessage;

        static {
            LOGIN.retryMessage = "Can't login - trying again.";
            REGISTER.retryMessage = "Can't register - trying again.";
            NONE.retryMessage = "NONE RETRY";

            LOGIN.errorMessage = createConnectErrorMessage(UserModel.LOGIN_ENDPOINT);
            REGISTER.errorMessage = createConnectErrorMessage(UserModel.REGISTER_ENDPOINT);
            NONE.errorMessage = "NONE ERROR";
        }

        private static String createConnectErrorMessage(String endpoint) {
            return "Can't connect to '" + RequestHandler.BASE_URL + endpoint + "'.";
        }

        public String getRetryMessage() {
            return retryMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static String LOGIN_ENDPOINT = "login";
    public static String REGISTER_ENDPOINT = "register";

    private final AsyncHttpClient client = new AsyncHttpClient();
    private final int TIMEOUT = 5000;
    private final int MAX_RETRIES = 1;

    private final String password;
    private final String name;
    private final LoginPresenter loginPresenter;

    private RequestType requestType = RequestType.NONE;

    public UserModel(String name, String password, LoginPresenter loginPresenter) {
        this.name = name;
        this.password = password;
        this.loginPresenter = loginPresenter;
    }

    public void sendLoginRequest() {
        requestType = RequestType.LOGIN;

        RequestParams params = new RequestParams();
        params.add("username", name);
        params.add("password", password);

        client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
        client.get(BASE_URL + LOGIN_ENDPOINT, params , new RequestHandler(loginPresenter));
    }

    public void sendRegisterRequest() {
        requestType = RequestType.REGISTER;

        RequestParams params = new RequestParams();
        params.add("username", name);
        params.add("password", password);

        client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
        client.get(BASE_URL + REGISTER_ENDPOINT, params , new RequestHandler(loginPresenter));
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
