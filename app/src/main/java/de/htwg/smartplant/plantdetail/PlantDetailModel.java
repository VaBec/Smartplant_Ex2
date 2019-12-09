package de.htwg.smartplant.plantdetail;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.BASE_URL;

public class PlantDetailModel {

    private final AsyncHttpClient client = new AsyncHttpClient();

    private final String id;
    private final String userName;
    private final String password;

    private final int TIMEOUT = 5000;
    private final int MAX_RETRIES = 1;

    private final String DELETE_PLANT_ENDPOINT = "deleteplant";
    private final Context context;
    private final PlantDetailPresenter presenter;

    public PlantDetailModel(String id, String userName, String password, Context context, PlantDetailPresenter presenter) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.context = context;
        this.presenter = presenter;
    }

    public void sendDeletePlantRequest() {
        try {
            JSONObject deletePlantJson = new JSONObject();

            deletePlantJson.put("id", id);
            deletePlantJson.put("username", userName);
            deletePlantJson.put("password", password);

            StringEntity entity = new StringEntity(deletePlantJson.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.setMaxRetriesAndTimeout(1, TIMEOUT);

            String url = BASE_URL + DELETE_PLANT_ENDPOINT;
            client.delete(context, url, entity,"application/json", new RequestHandler(presenter));
        } catch(Exception e) {
            presenter.showException(e);
        }
    }
}
