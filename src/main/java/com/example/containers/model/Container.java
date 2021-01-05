package com.example.containers.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Container {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int schipId;
    private double gewicht;
    private String inhoud;
    private String startLocatie;
    private String eindLocatie;
    private String serieCode;

    public Container() {
    }

    public Container(int schipId, double gewicht, String inhoud, String startLocatie, String eindLocatie, String serieCode) {
        this.schipId = schipId;
        this.gewicht = gewicht;
        this.inhoud = inhoud;
        this.startLocatie = startLocatie;
        this.eindLocatie = eindLocatie;
        this.serieCode = serieCode;
    }
}
