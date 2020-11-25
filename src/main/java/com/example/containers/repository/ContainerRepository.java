package com.example.containers.repository;

import com.example.containers.model.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Integer> {

    Container findContainerById(int id);

    List<Container> findContainersByGewichtBetween(float gewichtMin, float gewichtMax);

    List<Container> findContainersByInhoudContaining(String inhoud);

    List<Container> findContainersByStartLocatieContaining(String startLocatie);

    List<Container> findContainersByEindLocatieContaining(String eindLocatie);
}