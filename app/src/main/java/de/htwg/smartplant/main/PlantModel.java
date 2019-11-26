package de.htwg.smartplant.main;

import java.util.Arrays;
import java.util.List;

public class PlantModel {

    private Long id;
    private float waterValue;
    private String name;

    private String retryMessage;
    private String errorMessage;

    private MainPresenter presenter;
    private String user;

    public PlantModel(MainPresenter presenter, String username)
    {
        this.presenter = presenter;
        this.user = username;
    }


    public List<String> getUserPlants(String user){

        //Send Request

        return Arrays.asList("Plant 1", "Plant 2");
    }
}
