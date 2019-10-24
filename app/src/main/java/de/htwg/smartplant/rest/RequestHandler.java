package de.htwg.smartplant.rest;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import de.htwg.smartplant.interfaces.HttpNotifier;

public class RequestHandler extends AsyncHttpResponseHandler {

    public static final String BASE_URL = "https://localhost:5000/";
    private final HttpNotifier notifier;

    public RequestHandler(HttpNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        notifier.showSuccess();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        notifier.showFailure();
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
