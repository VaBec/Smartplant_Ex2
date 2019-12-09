package de.htwg.smartplant.plantdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.htwg.smartplant.R;
import de.htwg.smartplant.Utils;
import de.htwg.smartplant.main.MainActivity;

public class PlantDetailView extends AppCompatActivity implements PlantDetailPresenter.IDetailView{

    private PlantDetailObjectModel model;
    private PlantDetailPresenter plantDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_detail);

        Intent intent = getIntent();
        this.model = (PlantDetailObjectModel) intent.getExtras().get("plant");
        this.plantDetailPresenter = new PlantDetailPresenter(this, this.getApplicationContext());
        
        setViewData();
    }

    private void setViewData() {
        ProgressBar waterValue = findViewById(R.id.waterValue);
        TextView macText = findViewById(R.id.macAddressText);
        TextView dateText = findViewById(R.id.dateText);
        ImageView plantImage = findViewById(R.id.plantImage);
        Button deleteButton = findViewById(R.id.deleteButton);

        this.styleProgressBar(model.getWaterValue(), waterValue);
        plantImage.setImageResource(getImageOfPlant(model.getPlantType()));
        macText.setText(model.getMac());
        dateText.setText(this.formatTimeStamp(model.getTimeStamp()));

        deleteButton.setOnClickListener(event -> createDeleteDialog());
    }

    private void createDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Löschen")
                .setMessage("Wirklich löschen?")
                .setPositiveButton("Ja", (dialog, which) -> plantDetailPresenter.deletePlant(
                        model.getId(), Utils.user, Utils.password
                ))
                .setNegativeButton("Nein", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private String formatTimeStamp(String timeStamp) {
        String result;

        result = timeStamp.replace("/", ".");
        for(int i=0 ; i<result.length() ; i++) {
            if(result.charAt(i) == ' ') {
                result = result.substring(0, i);
                break;
            }
        }

        String dayText = "";
        String monthText = "";
        String yearText = "";

        int dayIndex = -1;
        int monthIndex = -1;

        for(int i=0 ; i<result.length() ; i++) {
            if(result.charAt(i) == '.') {
                if(monthText.equals("")) {
                    monthText = result.substring(0, i);
                    monthIndex = i;
                } else {
                    dayText = result.substring(monthIndex+1, i);
                    dayIndex = i;
                    break;
                }
            }
        }

        yearText = result.substring(dayIndex+1);

        if(dayText.length() == 1) {
            dayText = "0" + dayText;
        }

        if(monthText.length() == 1) {
            monthText = "0" + monthText;
        }

        return dayText + "." + monthText + "." + yearText;
    }

    private void styleProgressBar(Integer waterValue, ProgressBar waterValueProgressBar) {
        String colorString;
        if(waterValue >= 682) {
            colorString = "#2389da";    // Water blue
        } else if(waterValue >= 341) {
            colorString = "#ffae42";    // Warning orange
        } else {
            colorString = "#ff5042";   // Error red
        }

        waterValueProgressBar.getProgressDrawable().setColorFilter(
                Color.parseColor(colorString), android.graphics.PorterDuff.Mode.SRC_IN);

        waterValueProgressBar.setProgress(waterValue);
    }

    private int getImageOfPlant(Integer plantType) {
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

    @Override
    public void onBackPressed() {
        finish();
        Intent mainView = new Intent(this, MainActivity.class);
        mainView.putExtra("user", Utils.user);
        startActivity(mainView);
    }

    @Override
    public void startDeleting() {

    }

    @Override
    public void showToast(String text, int length) {
        Toast toast = Toast.makeText(getApplicationContext(), text, length);

        toast.show();
    }

    @Override
    public void openMainView() {

    }
}
