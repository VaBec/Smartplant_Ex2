package de.htwg.smartplant.main;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import de.htwg.smartplant.rest.HttpNotifier;
import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;

public class MainPresenter implements HttpNotifier {

    private IMainActivity view;
    private Context context;
    private User user;
    private MainModel mainModel;


    public MainPresenter(IMainActivity view, Context context, User user) {
        this.user = user;
        this.view = view;
        this.context = context;
        this.mainModel = new MainModel(this, user, context);
    }

    public void getPlants() {
        mainModel.getUserPlants(user);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void showFailure(String errorMessage) {
        view.showToast(errorMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void showSuccess(JSONObject response) {
        try {
            JSONArray plants = (JSONArray) response.get("payload");
            view.setPlants(Plant.createPlantListFromJSON(plants));
            view.showToast(plants.length() + " Pflanzen gefunden!", Toast.LENGTH_LONG);
        } catch(Exception e){
            showException(e);
        }
    }

    @Override
    public void showStart() {

    }

    public void showException(Exception e) {
        view.showToast(e.getMessage(), Toast.LENGTH_LONG);
    }

    public interface IMainActivity{
        void setupTabs();
        void showToast(String message, int lengthLong);
        void setPlants(List<Plant> plants);
    }

    public interface IPlantsView {

    }

    public interface IUserView {

    }

    public interface IAnalyzerView{

    }
}
