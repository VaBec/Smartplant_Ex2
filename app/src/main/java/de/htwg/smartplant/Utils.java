package de.htwg.smartplant;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Utils {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG)
                .show();
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
}
