package de.htwg.smartplant.rest;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RequestHandler extends JsonHttpResponseHandler {

    public static final String BASE_URL = "http://smartplant.azurewebsites.net/";
    private final HttpNotifier notifier;

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
