package com.example.containers.controller;

import com.example.containers.model.Container;
import com.example.containers.repository.ContainerRepository;
import com.example.containers.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ContainerController {


    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerService containerService;

    @GetMapping("/containers")
    public List<Container> getAllContainers() {
        return containerService.getAllContainers();
    }

    @GetMapping("/containers/schip/{schipId}")
    public List<Container> getContainerBySchipId(
            @PathVariable int schipId) {
        return containerService.getContainersBySchipId(schipId);
    }

    @GetMapping("/containers/gewicht/{gewichtMin}/{gewichtMax}")
    public List<Container> getContainersByGewichtBetween(
            @PathVariable double gewichtMin,
            @PathVariable double gewichtMax) {
        return containerService.getContainersByGewichtBetween(gewichtMin, gewichtMax);
    }

    @GetMapping("/containers/inhoud/{inhoud}")
    public List<Container> getContainersByInhoudContaining(
            @PathVariable String inhoud) {
        return containerService.getContainersByInhoud(inhoud);
    }

    @GetMapping("/containers/serieCode/{serieCode}")
    public Container getContainersBySerieCode(
            @PathVariable String serieCode) {
        return containerService.getContainerBySerieCode(serieCode);
    }

    @GetMapping("/containers/startlocatie/{startLocatie}")
    public List<Container> getContainersByStartLocatieContaining(
            @PathVariable String startLocatie) {
        return containerService.getContainersByStartLocatie(startLocatie);
    }

    @GetMapping("/containers/eindlocatie/{eindLocatie}")
    public List<Container> getContainersByEindLocatieContaining(
            @PathVariable String eindLocatie) {
        return containerService.getContainersByEindLocatie(eindLocatie);
    }

    @PutMapping("/containers/update")
    public Container updateContainer(
            @RequestBody Container container){
        return containerService.updateContainer(container);
    }

    @PostMapping("/containers/insert")
    public Container insertContainer(
            @RequestBody Container container) {
        return containerService.insertContainer(container);
    }

    @DeleteMapping("containers/delete/{id}")
    public void deleteContainerById(
            @PathVariable int id){
        containerService.deleteContainer(id);
    }

    @PostConstruct()
    public void fillDB() {
        if (containerRepository.count() == 0) {
            containerRepository.save(new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123"));
            containerRepository.save(new Container(2,1420.00, "Speelgoed", "Hong Kong", "Antwerpen","DEF456"));
        }
    }
}
