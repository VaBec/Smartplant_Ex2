package de.htwg.smartplant.main;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import de.htwg.smartplant.rest.HttpNotifier;

public class MainPresenter implements HttpNotifier {

    private IMainActivity view;
    private Context context;
    private String user;
    private PlantModel plantModel;


    public MainPresenter(IMainActivity view, Context context, String user)
    {
        this.user = user;
        this.view = view;
        this.context = context;
        plantModel = new PlantModel(this, user);
    }

    public List<String> getPlants(){
        return plantModel.getUserPlants(user);
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

    public interface IMainActivity{
        void setupTabs();
        List<String> getPlants();
    }

    public interface IPlantsView {

    }

    public interface IUserView {

    }

    public interface IAnalyzerView{

    }
}
