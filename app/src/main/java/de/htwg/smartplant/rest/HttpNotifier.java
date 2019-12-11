package de.htwg.smartplant.rest;

import org.json.JSONObject;

public interface HttpNotifier {

    void showRetry();
    void showFailure(String errorMessage);
    void showSuccess(JSONObject response);
    void showStart();

}
