package de.htwg.smartplant.plantdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import de.htwg.smartplant.R;
import de.htwg.smartplant.main.MainActivity;

public class PlantDetailView extends AppCompatActivity {
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_detail);
        Intent intent = getIntent();
        this.user = intent.getStringExtra("user");
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent mainView = new Intent(this, MainActivity.class);
        mainView.putExtra("user", user);
        startActivity(mainView);
    }
}
