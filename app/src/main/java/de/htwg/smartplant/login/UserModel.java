package de.htwg.smartplant.login;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
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

    private final Long id = 0L;
    private final String password;
    private final String name;
    private final Context context;
    private final LoginPresenter loginPresenter;

    private RequestType requestType = RequestType.NONE;

    public UserModel(String name, String password, LoginPresenter loginPresenter, Context context) {
        this.name = name;
        this.password = password;
        this.loginPresenter = loginPresenter;
        this.context = context;
    }

    public void sendLoginRequest() {
        try {
            requestType = RequestType.LOGIN;

            JSONObject userModel = new JSONObject();

            userModel.put("username", name);
            userModel.put("password", password);

            StringEntity entity = new StringEntity(userModel.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);

            String url = BASE_URL + LOGIN_ENDPOINT;
            client.post(context, url, entity,"application/json", new RequestHandler(loginPresenter));

        } catch(Exception e) {
            loginPresenter.showException(e);
        }
    }

    public void sendRegisterRequest() {
        try {
            requestType = RequestType.REGISTER;

            JSONObject userModel = new JSONObject();

            userModel.put("username", name);
            userModel.put("password", password);

            StringEntity entity = new StringEntity(userModel.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);

            String url = BASE_URL + REGISTER_ENDPOINT;
            client.put(context, url, entity,"application/json", new RequestHandler(loginPresenter));

        } catch(Exception e) {
            loginPresenter.showException(e);
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }
    public String getName(){return name;}
    public Long getID() {return this.id;}
}
