package de.htwg.smartplant.jsonmodels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plant implements Serializable {

    private String owner;
    private int plantType;
    private String mac;
    private String timeStamp;
    private int waterValue;
    private int image;
    private String id;

    public Plant(String id, String mac, int waterValue, String timeStamp,
                 String owner, int plantType) {
        this.owner = owner;
        this.plantType = plantType;
        this.mac = mac;
        this.timeStamp = timeStamp;
        this.waterValue = waterValue;
        this.id = id;
    }

    public static List<Plant> createPlantListFromJSON(JSONArray jsonArray) throws JSONException {
        List<Plant> result = new ArrayList<>();

        for(int i=0 ; i<jsonArray.length() ; i++) {
            result.add(createPlantFromJSON(jsonArray.getJSONObject(i)));
        }

        return result;
    }

    public static Plant createPlantFromJSON(JSONObject jsonObject) throws JSONException {
        return new Plant(
                jsonObject.getString("id"),
                jsonObject.getString("macAddress"),
                Integer.valueOf(jsonObject.getString("watervalue")),
                jsonObject.getString("timeStamp"),
                jsonObject.getString("owner"),
                Integer.valueOf(jsonObject.getString("plantType"))
        );
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOwner() {
        return owner;
    }

    public String getMac() {
        return mac;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getPlantType() {
        return plantType;
    }

    public void setPlantType(int plantType) {
        this.plantType = plantType;
    }

    public int getWaterValue() {
        return waterValue;
    }

    public void setWaterValue(int waterValue) {
        this.waterValue = waterValue;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}