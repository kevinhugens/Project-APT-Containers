package com.example.containers.service;

import com.example.containers.model.Container;
import com.example.containers.repository.ContainerRepository;

import java.util.logging.Logger;

public class ContainerService {

    Logger logger = Logger.getLogger(ContainerService.class.getName());

    private ContainerRepository containerRepository;

    public ContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    public Container getContainerById(int id) {
        Container container;
        container = containerRepository.findContainerById(id);

        //logging
        return container;
    }

    public Container insertContainer(Container container){
        //logging
        return containerRepository.save(container);
    }

    public void deleteContainer(int containerId){
        //logging
        containerRepository.deleteById(containerId);
    }

}
