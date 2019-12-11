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

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.Utils;
import de.htwg.smartplant.main.recycler.adapters.TabsPagerAdapter;
import de.htwg.smartplant.main.recycler.fragments.ManagePlantsFragment;
import de.htwg.smartplant.main.recycler.fragments.YourPlantsFragment;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;
import de.htwg.smartplant.start.StartView;

public class MainView extends AppCompatActivity implements MainPresenter.IMainActivity {

    private MainPresenter mainPresenter;
    private User user;

    private TabLayout tablayout;
    private ViewPager viewPager;

    private TabsPagerAdapter tabsPagerAdapter;
    private YourPlantsFragment yourPlantsFragment;
    private ManagePlantsFragment managePlantsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User) this.getIntent().getExtras().get("user");
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, this.getApplicationContext(), this.user);
        setupTabs();
        hideKeyBoard();
        mainPresenter.startPollingTask(yourPlantsFragment, managePlantsFragment, this);
    }

    void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = findViewById(android.R.id.content);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Wirklich ausloggen?")
                .setPositiveButton("Ja", (dialog, which) -> {
                    finish();
                    Intent loginView = new Intent(MainView.this, StartView.class);
                    startActivity(loginView);
                })
                .setNegativeButton("Nein", null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mainPresenter.stopPolling();
    }

    @Override
    public void setupTabs() {
        tablayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.view_pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        this.yourPlantsFragment = new YourPlantsFragment();
        this.managePlantsFragment = new ManagePlantsFragment();

        yourPlantsFragment.setMainView(this);
        managePlantsFragment.setMainView(this);

        tabsPagerAdapter.AddFragment(yourPlantsFragment, getString(R.string.tab_text_1) );
        tabsPagerAdapter.AddFragment(managePlantsFragment, getString(R.string.tab_text_2));

        viewPager.setAdapter(tabsPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showToast(String text) {
        Utils.showToast(this.getApplicationContext(), text);
    }

    @Override
    public void setPlants(List<Plant> plants) {
        YourPlantsFragment yourPlantsFragment = (YourPlantsFragment) tabsPagerAdapter.getItem(0);
        ManagePlantsFragment managePlantsFragment = (ManagePlantsFragment) tabsPagerAdapter.getItem(1);

        yourPlantsFragment.addPlantsData(plants, this.user.getUserName());
        managePlantsFragment.addPlantsData(plants, this.user, yourPlantsFragment);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public MainPresenter getMainPresenter() { return this.mainPresenter; }
}