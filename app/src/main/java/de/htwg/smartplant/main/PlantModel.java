package de.htwg.smartplant.main;

public class PlantModel {

    private Long id;
    private float waterValue;
    private String name;

    public PlantModel(Long id, String name, float waterValue)
    {
        this.id = id;
        this.waterValue = waterValue;
        this.name = name;
    }
}
