package de.htwg.smartplant.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import de.htwg.smartplant.caching.CachedRequest;
import de.htwg.smartplant.caching.RequestCacheQueue;
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
    private RequestCacheQueue requestCache;

    public PlantModel(MainPresenter presenter, String username, Context context) {
        this.presenter = presenter;
        this.user = username;
        this.context = context;
        requestCache = new RequestCacheQueue();
    }

    public void getUserPlants(String user){
        try {
            Map<String,String> params = new HashMap<>();
            Map<String,String> headers = new HashMap<>();
            params.put("userName", user);
            headers.put("accept:", "application/json");
            Map.Entry<String,String> header = headers.entrySet().iterator().next();
            RequestParams reqParams = new RequestParams(params);

            client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
            client.addHeader(header.getKey(), header.getValue());

            String url = BASE_URL + PLANTS_ENDPOINT;

            if(isOnline()) {
                client.get(context, url, reqParams, new RequestHandler(presenter));
            } else {
                requestCache.addRequest(new CachedRequest(params, url, headers));

                Thread thread = new Thread(){
                    public void run(){
                        boolean done = false;
                        while(!done) {
                            if(isOnline()) {
                                //send Requests
                                for (CachedRequest req : requestCache.getRequests()) {
                                    client.get(context, req.getUrl(), new RequestParams(req.getRequestParams()), new RequestHandler(presenter));
                                }
                                currentThread().interrupt();
                            } else {
                                //Wait 10 Seconds
                                try {
                                    currentThread().wait(15000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };
                if (!thread.isAlive()) {thread.start();}
            }
        }
        catch(Exception e) {
            presenter.showException(e);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
