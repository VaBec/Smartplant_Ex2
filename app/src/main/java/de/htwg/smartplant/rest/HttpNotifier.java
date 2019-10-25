package de.htwg.smartplant.rest;

import org.json.JSONObject;

public interface HttpNotifier {

    void showRetry();
    void showFailure(JSONObject response);
    void showSuccess(JSONObject response);
    void showStart();

}
