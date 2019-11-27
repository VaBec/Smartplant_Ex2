package de.htwg.smartplant.main;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.BASE_URL;

public class PlantModel {

    private Long id;
    private float waterValue;
    private String name;

    private String retryMessage;
    private String errorMessage;

    private MainPresenter presenter;
    private String user;

    public static String PLANTS_ENDPOINT = "plantsfromuser";

    private final AsyncHttpClient client = new AsyncHttpClient();
    private final int TIMEOUT = 5000;
    private final int MAX_RETRIES = 1;

    private final Context context;
    public PlantModel(MainPresenter presenter, String username, Context context) {
        this.presenter = presenter;
        this.user = username;
        this.context = context;
    }

    public void getUserPlants(String user){
        try {
            Map<String,String> params = new HashMap<>();
            params.put("userName", user);
            RequestParams reqParams = new RequestParams(params);

            client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
            client.addHeader("accept:", "application/json");

            String url = BASE_URL + PLANTS_ENDPOINT;

            client.get(context, url, reqParams, new RequestHandler(presenter));
        }
        catch(Exception e) {
            presenter.showException(e);
        }
    }
}
