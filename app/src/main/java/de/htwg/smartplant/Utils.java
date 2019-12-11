package de.htwg.smartplant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import de.htwg.smartplant.rest.HttpManager;
import de.htwg.smartplant.rest.HttpNotifier;

public class Utils {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG)
                .show();
    }

    public static void requestPlants(String userName, Context context, HttpNotifier notifier) {
        Map<String,String> params = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        params.put("userName", userName);
        headers.put("accept:", "application/json");
        Map.Entry<String,String> header = headers.entrySet().iterator().next();
        RequestParams reqParams = new RequestParams(params);

        HttpManager.createClient().
                get(context, HttpManager.RequestUrl.PLANTSFROMUSER.create(), reqParams, new HttpManager(notifier));
    }

    public static int getImageOfPlant(int plantType) {
        switch(plantType) {
            case 0: return R.drawable.strawberry;
            case 1: return R.drawable.raspberry;
            case 2: return R.drawable.cactus;
            case 3: return R.drawable.potatoe;
            case 4: return R.drawable.tomato;
            case 5: return R.drawable.onion;
            case 6: return R.drawable.coal;
            case 7: return R.drawable.cucumber;
            case 8: return R.drawable.grape;
            case 9: return R.drawable.carrot;
            default : return R.drawable.plant;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
