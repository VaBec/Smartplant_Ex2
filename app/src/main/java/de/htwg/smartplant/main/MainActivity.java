package de.htwg.smartplant.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.main.fragments.AnalyseFragment;
import de.htwg.smartplant.main.fragments.PlantsFragment;
import de.htwg.smartplant.main.fragments.UserFragment;

public class MainActivity extends AppCompatActivity implements MainPresenter.IMainActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        mainPresenter = new MainPresenter(this, this.getApplicationContext(), user);
        setupTabs();
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
            tabsPagerAdapter.AddFragment(new UserFragment(), getString(R.string.tab_text_3));
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
    public void updatePlantsData(JSONArray plants) {
        PlantsFragment plantsFragment = (PlantsFragment) tabsPagerAdapter.getItem(0);
        plantsFragment.addPlantsData(extractIDs(plants));
    }

    private List<String> extractIDs(JSONArray array) {
        List<String> result = new ArrayList<String>();
        try {
        for (int i = 0; i < array.length(); i++) {
                result.add(array.getJSONObject(i).getString("id") + "");
        }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return result;
    }
}