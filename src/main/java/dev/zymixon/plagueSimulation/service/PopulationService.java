package dev.zymixon.plagueSimulation.service;

import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.exception.PopulationNotFoundException;

import java.util.List;

public interface PopulationService {

    Population getPopulation(Long id) throws PopulationNotFoundException;
    List<Population> getAllPopulations();
    List<Population> getAllPopulationsWithSimulationId(Long simulationId);
    Population savePopulation(Population population);
    List<Population> saveAllPopulations(List<Population> populations);


}
