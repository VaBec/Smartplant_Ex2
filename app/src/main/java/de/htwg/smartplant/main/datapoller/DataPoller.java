package de.htwg.smartplant.main.datapoller;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import de.htwg.smartplant.Utils;
import de.htwg.smartplant.main.MainView;
import de.htwg.smartplant.main.recycler.fragments.ManagePlantsFragment;
import de.htwg.smartplant.main.recycler.fragments.YourPlantsFragment;
import de.htwg.smartplant.main.recycler.adapters.ManagePlantsAdapter;
import de.htwg.smartplant.main.recycler.adapters.YourPlantsAdapter;

public class DataPoller {

    private final int SLEEPTIME = 5000;

    private final String userName;
    private final MainView mainAMainView;
    private final DataPollerRequestHandler dataPollerRequestHandler;

    private ManagePlantsAdapter managePlantsAdapter;
    private YourPlantsAdapter yourPlantsAdapter;

    private Thread pollingThread;

    public DataPoller(YourPlantsFragment yourPlantsFragment, ManagePlantsFragment managePlantsFragment, String userName, MainView mainView) {
        this.yourPlantsAdapter = yourPlantsFragment.getYourPlantsAdapter();
        this.managePlantsAdapter = managePlantsFragment.getPlantManageAdapter();
        this.userName = userName;
        this.mainAMainView = mainView;
        this.dataPollerRequestHandler = new DataPollerRequestHandler(this.managePlantsAdapter, this.yourPlantsAdapter, mainAMainView);
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
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(() -> requestPlants());
                    Thread.sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                    Log.e("DataPollerError", e.getMessage());
                    return;
                }
            }
        });

        pollingThread.start();
    }

    private void requestPlants() {
        Utils.requestPlants(userName, mainAMainView.getApplicationContext(), dataPollerRequestHandler);
    }
}
