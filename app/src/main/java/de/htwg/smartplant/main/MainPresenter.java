package de.htwg.smartplant.main;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
        plantModel = new PlantModel(this, user, context);
    }

    public void getPlants() throws UnsupportedEncodingException, JSONException {
        plantModel.getUserPlants(user);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void showFailure(JSONObject response) {

    }

    @Override
    public void showSuccess(JSONObject response) {
        try {
            JSONArray plants = (JSONArray) response.get("payload");
            JSONObject first = plants.getJSONObject(0);

            view.showToast(plants.length() + "", Toast.LENGTH_LONG);
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
        void getPlants() throws UnsupportedEncodingException, JSONException;
        void showToast(String message, int lengthLong);
    }

    public interface IPlantsView {

    }

    public interface IUserView {

    }

    public interface IAnalyzerView{

    }
}
