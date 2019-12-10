package de.htwg.smartplant.rest;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RequestHandler extends JsonHttpResponseHandler {

    public enum RequestUrl {
        LOGIN, REGISTER, PLANTSFROMUSER, DELETEPLANT;

        private RequestUrl requestUrl;

        static {
            LOGIN.requestUrl = LOGIN;
            REGISTER.requestUrl = REGISTER;
            PLANTSFROMUSER.requestUrl = PLANTSFROMUSER;
            DELETEPLANT.requestUrl = DELETEPLANT;
        }

        public String create() {
            return BASE_URL + requestUrl.name().toLowerCase();
        }
    }

    public static final String BASE_URL = "http://smartplant.azurewebsites.net/";
    public static final int TIMEOUT = 5000;
    public static final int RETRIES = 1;

    private final HttpNotifier notifier;

    private static AsyncHttpClient client;

    public static AsyncHttpClient createClient() {
        if(client == null) {
            client = new AsyncHttpClient();
            client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT);
        }

        return client;
    }

    public RequestHandler(HttpNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        notifier.showSuccess(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        notifier.showFailure(errorResponse);
    }

    @Override
    public void onStart() {
        notifier.showStart();
    }

    @Override
    public void onRetry(int retryNo) {
        notifier.showRetry();
    }
}
