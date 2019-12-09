package de.htwg.smartplant.plantdetail;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.htwg.smartplant.rest.HttpNotifier;

public class PlantDetailPresenter implements HttpNotifier {

    private final IDetailView view;
    private final Context context;

    public PlantDetailPresenter(IDetailView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void deletePlant(String id, String userName, String password) {
        PlantDetailModel plantDetailModel = new PlantDetailModel(id, userName, password, this.context, this);
        plantDetailModel.sendDeletePlantRequest();
    }

    @Override
    public void showRetry() {
    }

    @Override
    public void showFailure(JSONObject response) {
        String message;

        try {
            message = (String) response.get("payload");
        } catch(JSONException e) {
            message = e.getMessage();
        }

        view.showToast(message, Toast.LENGTH_LONG);
    }

    @Override
    public void showSuccess(JSONObject response) {
        String message;

        try {
            message = (String) response.get("payload");
        } catch(JSONException e) {
            message = e.getMessage();
        }

        view.showToast(message, Toast.LENGTH_LONG);
        view.openMainView();
    }

    @Override
    public void showStart() {
        view.startDeleting();
    }

    public void showException(Exception e) {
        view.showToast(e.getMessage(), Toast.LENGTH_LONG);
    }

    public interface IDetailView{
        void startDeleting();
        void showToast(String text, int length);
        void openMainView();
    }

}
