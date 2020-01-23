package de.htwg.smartplant.rest.jsonmodels;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Water {

    private String macAddress;

    public Water(String macAddress) {
        this.macAddress = macAddress;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(new Gson().toJson(this));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
