package de.htwg.smartplant.main.datapoller;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.htwg.smartplant.jsonmodels.Plant;
import de.htwg.smartplant.main.MainActivity;
import de.htwg.smartplant.main.fragments.HandlePlantsFragment;
import de.htwg.smartplant.main.fragments.YourPlantsFragment;
import de.htwg.smartplant.main.recycler.ManagePlantsAdapter;
import de.htwg.smartplant.main.recycler.YourPlantsAdapter;
import de.htwg.smartplant.rest.RequestHandler;

import static de.htwg.smartplant.rest.RequestHandler.RequestUrl.PLANTSFROMUSER;

public class DataPoller {

    private final int SLEEPTIME = 250;

    private final String USERNAME;
    private final MainActivity MAIN_ACTIVITY;

    private ManagePlantsAdapter managePlantsAdapter;
    private YourPlantsAdapter yourPlantsAdapter;

    private Thread pollingThread;
    private List<Plant> plants;

    public DataPoller(YourPlantsFragment yourPlantsFragment, HandlePlantsFragment handlePlantsFragment, String userName, MainActivity mainActivity) {
        this.yourPlantsAdapter = yourPlantsFragment.getYourPlantsAdapter();
        this.managePlantsAdapter = handlePlantsFragment.getPlantManageAdapter();
        this.USERNAME = userName;
        this.MAIN_ACTIVITY = mainActivity;
    }

    public void stopPolling() {
        if(pollingThread != null) {
            pollingThread.interrupt();
        }
    }

    public void startPolling() {
        this.pollingThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(SLEEPTIME);
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(() -> requestPlants());
                } catch (InterruptedException e) {
                    Log.e("DataPollerError", e.getMessage());
                    return;
                }
            }
        });

        pollingThread.start();
    }

    private void requestPlants() {
        Map<String,String> params = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        params.put("userName", USERNAME);
        headers.put("accept:", "application/json");
        Map.Entry<String,String> header = headers.entrySet().iterator().next();
        RequestParams reqParams = new RequestParams(params);

        AsyncHttpClient client = RequestHandler.createClient();
        client.addHeader(header.getKey(), header.getValue());

        client.get(MAIN_ACTIVITY, PLANTSFROMUSER.create(), reqParams, new RequestHandler(
                new DataPollerRequestHandler(this.managePlantsAdapter, this.yourPlantsAdapter)));
    }
}
