package com.example.containers.service;

import com.example.containers.model.Container;
import com.example.containers.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ContainerService {


    @Autowired
    private ContainerRepository containerRepository;

    Logger logger = Logger.getLogger(ContainerService.class.getName());

    public Container getByContainerId(int id) {
        Container container;
        container = containerRepository.findById(id).orElseThrow();

        return container;
    }

    public Container getContainerBySerieCode(String serieCode) {
        Container container;
        container = containerRepository.findContainerBySerieCode(serieCode);

        return container;
    }

    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    public List<Container> getContainersBySchipId(int schipID) {
        List<Container> containers;
        containers = containerRepository.findContainersBySchipId(schipID);

        return containers;
    }

    public List<Container> getContainersByInhoud(String inhoud) {
        List<Container> containers;
        containers = containerRepository.findContainersByInhoudContaining(inhoud);

        return  containers;
    }

    public List<Container> getContainersByGewichtBetween(double minGewicht, double maxGewicht) {
        if (minGewicht >= 0 && maxGewicht >= 0) {
            List<Container> containers;
            containers = containerRepository.findContainersByGewichtBetween(minGewicht, maxGewicht);

            return containers;
        } else {
            logger.setLevel(Level.INFO);
            logger.info("Gewicht was onder 0");

            return null;
        }
    }

    public List<Container> getContainersByStartLocatie(String startLocatie) {
        List<Container> containers;
        containers = containerRepository.findContainersByStartLocatieContaining(startLocatie);

        return containers;
    }

    public List<Container> getContainersByEindLocatie(String eindLocatie) {
        List<Container> containers;
        containers = containerRepository.findContainersByEindLocatieContaining(eindLocatie);

        return containers;
    }

    public Container updateContainer(Container container){
        Container newContainer = containerRepository.findById(container.getId()).orElseThrow();

        newContainer.setSchipId(container.getSchipId());
        newContainer.setInhoud(container.getInhoud());
        newContainer.setGewicht(container.getGewicht());
        newContainer.setStartLocatie(container.getStartLocatie());
        newContainer.setEindLocatie(container.getEindLocatie());

        containerRepository.save(newContainer);

        return newContainer;
    }

    public Container insertContainer(Container container) {
        return containerRepository.save(container);
    }

    public void deleteContainer(int containerID) {
        containerRepository.deleteById(containerID);
    }

}
