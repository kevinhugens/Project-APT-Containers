package com.example.containers.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Container {

    public Container(
            int schipId,
            double gewicht,
            String inhoud,
            String startLocatie,
            String eindLocatie ) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int schipId;

    private float gewicht;

    private String inhoud;

    private String startLocatie;

    private String eindLocatie;

}
