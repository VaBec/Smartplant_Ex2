package de.htwg.smartplant.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.main.fragments.AnalyseFragment;
import de.htwg.smartplant.main.fragments.PlantsFragment;
import de.htwg.smartplant.main.fragments.UserFragment;

public class MainActivity extends AppCompatActivity implements MainPresenter.IMainView {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this, this.getApplicationContext());
        setupTabs();
        //loadPlants();
    }

    @Override
    public List<PlantModel> getAllPlants()
    {
        return null;
    }

    @Override
    public void setupTabs() {
        tablayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.view_pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        //Add Fragments
        tabsPagerAdapter.AddFragment(new PlantsFragment(), getString(R.string.tab_text_1) );
        tabsPagerAdapter.AddFragment(new AnalyseFragment(), getString(R.string.tab_text_2));
        tabsPagerAdapter.AddFragment(new UserFragment(), getString(R.string.tab_text_3));

        viewPager.setAdapter(tabsPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}