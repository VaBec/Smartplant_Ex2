package de.htwg.smartplant.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.login.LoginView;
import de.htwg.smartplant.main.fragments.AnalyseFragment;
import de.htwg.smartplant.main.fragments.PlantsFragment;
import de.htwg.smartplant.plantdetail.PlantDetailObjectModel;

public class MainActivity extends AppCompatActivity implements MainPresenter.IMainActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private MainPresenter mainPresenter;
    private String userName;
    private String password;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Wirklich ausloggen?")
                .setPositiveButton("Ja", (dialog, which) -> {
                    finish();
                    Intent loginView = new Intent(MainActivity.this, LoginView.class);
                    startActivity(loginView);
                })
                .setNegativeButton("Nein", null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        this.userName = intent.getStringExtra("username");
        this.password = intent.getStringExtra("password");

        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, this.getApplicationContext(), this.userName);
        setupTabs();
        hideKeyBoard();
    }

    void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = findViewById(android.R.id.content);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void setupTabs() {
        tablayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.view_pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        try {
            getPlants();
            tabsPagerAdapter.AddFragment(new PlantsFragment(), getString(R.string.tab_text_1) );
            tabsPagerAdapter.AddFragment(new AnalyseFragment(), getString(R.string.tab_text_2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewPager.setAdapter(tabsPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public void getPlants() throws UnsupportedEncodingException, JSONException {
        mainPresenter.getPlants();
    }

    @Override
    public void showToast(String text, int toastLength) {
        Toast toast = Toast.makeText(getApplicationContext(), text, toastLength);
        toast.show();
    }

    @Override
    public void updatePlantsData(JSONArray plantData) {
        PlantsFragment plantsFragment = (PlantsFragment) tabsPagerAdapter.getItem(0);
        plantsFragment.addPlantsData(createPlants(plantData), this.userName, this.password);

        AnalyseFragment analyseFragment = (AnalyseFragment) tabsPagerAdapter.getItem(1);
        analyseFragment.addPlantsData(createPlants(plantData), this.userName, this.password, plantsFragment);
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

        if(!errorMessage.equals("")) {
            showToast(errorMessage, Toast.LENGTH_LONG);
        }

        return result;
    }
}