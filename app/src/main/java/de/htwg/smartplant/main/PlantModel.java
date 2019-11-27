package de.htwg.smartplant.main;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
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

    public void getUserPlants(String user) throws JSONException, UnsupportedEncodingException {
        try {
            JSONObject plantsModel = new JSONObject();
            plantsModel.put("userName", user);

            StringEntity entity = new StringEntity(plantsModel.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
            String url = BASE_URL + PLANTS_ENDPOINT;
            client.get(context, url, entity, "application/json", new RequestHandler(presenter));
        }
        catch(Exception e) {
            presenter.showException(e);
        }
    }
}
