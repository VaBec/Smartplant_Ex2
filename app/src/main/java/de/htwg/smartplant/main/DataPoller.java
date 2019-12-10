package de.htwg.smartplant.main;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.htwg.smartplant.main.fragments.AnalyseFragment;
import de.htwg.smartplant.main.fragments.PlantsFragment;
import de.htwg.smartplant.main.recycler.PlantManageAdapter;
import de.htwg.smartplant.main.recycler.PlantsAdapter;
import de.htwg.smartplant.plantdetail.PlantDetailObjectModel;
import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.BASE_URL;

public class DataPoller {

    private final String userName;
    private final Activity activity;
    private PlantManageAdapter plantManageAdapter;
    private PlantsAdapter plantsAdapter;

    private List<PlantDetailObjectModel> plants;

    public DataPoller(PlantsFragment plantsFragment, AnalyseFragment analyseFragment, String userName, Activity activity) {
        this.plantsAdapter = plantsFragment.getPlantsAdapter();
        this.plantManageAdapter = analyseFragment.getPlantManageAdapter();
        this.userName = userName;
        this.activity = activity;
    }

    public void pollData() {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(250);
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(() -> requestPlants());
                } catch(Exception e) { }
            }
        }).start();
    }

    private void requestPlants() {
        Map<String,String> params = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        params.put("userName", userName);
        headers.put("accept:", "application/json");
        Map.Entry<String,String> header = headers.entrySet().iterator().next();
        RequestParams reqParams = new RequestParams(params);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(1, 5000);
        client.addHeader(header.getKey(), header.getValue());

        String url = BASE_URL + "plantsfromuser";

        client.get(activity, url, reqParams, new RequestHandler(new HttpNotifier() {
            @Override
            public void showRetry() {

            }

            @Override
            public void showFailure(JSONObject response) {
                int db = 3;
            }

            @Override
            public void showSuccess(JSONObject response) {
                JSONArray array = null;
                try {
                    array = (JSONArray) response.get("payload");
                    plants = createPlants(array);

                    List<PlantDetailObjectModel> currentPlants = plantManageAdapter.getPlants();

                    if(dataChanged(plants, currentPlants)) {
                        plantManageAdapter.updateData(plants);
                        plantManageAdapter.notifyDataSetChanged();

                        plantsAdapter.updateData(plants);
                        plantsAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void showStart() {
                int db = 3;
            }
        }));
    }

    private boolean dataChanged(List<PlantDetailObjectModel> plants, List<PlantDetailObjectModel> currentPlants) {
        if(plants.size() != currentPlants.size()) {
            return true;
        }

        for(int i=0 ; i<plants.size() ; i++) {
            if(plants.get(i).getWaterValue() != currentPlants.get(i).getWaterValue()) {
                return true;
            }
        }

        return false;
    }

    private List<PlantDetailObjectModel> createPlants(JSONArray array) {
        List<PlantDetailObjectModel> result = new ArrayList<>();
        String errorMessage = "";

        for(int i=0 ; i<array.length() ; i++) {
            try {
                PlantDetailObjectModel model = new PlantDetailObjectModel();

                model.setId(
                        array.getJSONObject(i).getString("id")
                );

                model.setPlantType(
                        Integer.valueOf(array.getJSONObject(i).getString("plantType"))
                );

                model.setMac(
                        array.getJSONObject(i).getString("macAddress")
                );

                model.setWaterValue(
                        Integer.valueOf(array.getJSONObject(i).getString("watervalue"))
                );

                model.setTimeStamp(
                        array.getJSONObject(i).getString("timeStamp")
                );

                model.setId(
                        array.getJSONObject(i).getString("id")
                );

                result.add(model);
            } catch (Exception e) {
                errorMessage += e.getMessage() + "\n";
            }
        }

        return result;
    }
}
