package de.htwg.smartplant.main;

import android.content.Context;
import android.view.View;

import org.json.JSONObject;

import java.util.List;

import de.htwg.smartplant.rest.HttpNotifier;

public class MainPresenter implements HttpNotifier {

    private IMainView view;
    private Context context;

    public MainPresenter(IMainView view, Context context)
    {
        this.view = view;
        this.context = context;
    }


    @Override
    public void showRetry() {

    }

    @Override
    public void showFailure(JSONObject response) {

    }

    @Override
    public void showSuccess(JSONObject response) {

    }

    @Override
    public void showStart() {

    }

    public interface IMainView {
        void setupTabs();
        List<PlantModel> getAllPlants();
    }
}
