package com.example.containers.controller;

import com.example.containers.model.Container;
import com.example.containers.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ContainerController {

    @Autowired
    private ContainerRepository containerRepository;

    @GetMapping("/containers/{id}")
    public Container getContainerById(
            @PathVariable int id) {
        return containerRepository.findContainerById(id);
    }

    @GetMapping("/containers/gewicht/{gewichtMin}&{gewichtMax}")
    public List<Container> getContainersByGewichtBetween(
            @PathVariable float gewichtMin, @PathVariable float gewichtMax) {
        return containerRepository.findContainersByGewichtBetween(gewichtMin, gewichtMax);
    }

    @GetMapping("/containers/inhoud/{inhoud}")
    public List<Container> getContainersByInhoudContaining(
            @PathVariable String inhoud) {
        return containerRepository.findContainersByInhoudContaining(inhoud);
    }

    @GetMapping("/containers/startLocatie/{startLocatie}")
    public List<Container> getContainersByStartLocatieContaining(
            @PathVariable String startLocatie) {
        return containerRepository.findContainersByStartLocatieContaining(startLocatie);
    }

    @GetMapping("/containers/eindLocatie/{eindLocatie}")
    public List<Container> getContainersByEindLocatieContaining(
            @PathVariable String eindLocatie) {
        return containerRepository.findContainersByEindLocatieContaining(eindLocatie);
    }

    @PostConstruct()
    public void fillDB() {
        if (containerRepository.count() == 0) {
            containerRepository.save(new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam"));
            containerRepository.save(new Container(1,1420.00, "Speelgoed", "Hong Kong", "Antwerpen"));
        }
    }
}
