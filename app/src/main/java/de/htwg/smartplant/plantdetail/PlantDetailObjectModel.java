package de.htwg.smartplant.plantdetail;

import java.io.Serializable;

public class PlantDetailObjectModel implements Serializable {

    private String userName;
    private int plantType;
    private String mac;
    private String timeStamp;
    private int waterValue;
    private String plantId;
    private int image;
    private String id;

    public void setUser(String userName) {
        this.userName = userName;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getMac() {
        return mac;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
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
