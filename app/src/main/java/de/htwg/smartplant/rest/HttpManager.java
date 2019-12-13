package de.htwg.smartplant.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class HttpManager extends JsonHttpResponseHandler {

    public enum RequestType {
        GET, POST, PUT, DELETE
    }

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

    public static boolean sendHtppRequest(RequestType requestType, JSONObject json, String url, HttpNotifier notifier, Context context) throws UnsupportedEncodingException {
        if(isOnline(context)) {
            createClient();

            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            switch (requestType) {
                case GET:
                    client.get(context, url, entity, "application/json", new HttpManager(notifier));
                    break;
                case POST:
                    client.post(context, url, entity, "application/json", new HttpManager(notifier));
                    break;
                case PUT:
                    client.put(context, url, entity, "application/json", new HttpManager(notifier));
                    break;
                case DELETE:
                    client.delete(context, url, entity, "application/json", new HttpManager(notifier));
                    break;
            }

            return true;
        }

        return false;
    }

    public static void requestPlants(String userName, Context context, HttpNotifier notifier) {
        Map<String,String> params = new HashMap<>();
        params.put("userName", userName);
        RequestParams reqParams = new RequestParams(params);

        HttpManager.createClient().
                get(context, HttpManager.RequestUrl.PLANTSFROMUSER.create(), reqParams, new HttpManager(notifier));
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public HttpManager(HttpNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        notifier.showSuccess(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        try {
            if (errorResponse == null) {
                errorResponse = new JSONObject();
                errorResponse.put("payload", "Der Server ist derzeit Offline.");
            } else {
                notifier.showFailure((String) errorResponse.get("payload"));
            }
        }catch(Exception ex) {
            notifier.showFailure(ex.getMessage());
        }
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
