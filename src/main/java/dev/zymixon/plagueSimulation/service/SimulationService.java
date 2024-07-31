package dev.zymixon.plagueSimulation.service;

import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;

import java.util.List;

public interface SimulationService {

    Simulation getSimulation(Long id) throws SimulationNotFoundException;
    List<Simulation> getAllSimulations();
    Simulation saveSimulation(Simulation simulation);
    void deleteSimulation(Long id);

}
