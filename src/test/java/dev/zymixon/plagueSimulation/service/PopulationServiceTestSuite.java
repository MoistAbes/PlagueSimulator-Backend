package dev.zymixon.plagueSimulation.service;

import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;
import dev.zymixon.plagueSimulation.service.impl.PopulationServiceImpl;
import dev.zymixon.plagueSimulation.service.impl.SimulationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PopulationServiceTestSuite {

    @Autowired
    PopulationServiceImpl populationService;
    @Autowired
    SimulationServiceImpl simulationService;


    @Test
    void testGeneratingFirstPopulation() throws SimulationNotFoundException {
        //Given
        Simulation simulation = new Simulation(
               "Test Simulation",
               1000000L,
               10L,
               3L,
               5,
               10,
               5,
               100,
               List.of()
        );
        Simulation savedSimulation = simulationService.saveSimulation(simulation);

        //When
        Population generatedPopulation = populationService.generatePopulation(savedSimulation.getId());
        Long totalPopulation = 0L;
        totalPopulation += generatedPopulation.getInfected();
        totalPopulation += generatedPopulation.getDead();
        totalPopulation += generatedPopulation.getHealthy();
        totalPopulation += generatedPopulation.getRecovered();

        //Then
        Assertions.assertEquals(1000000, totalPopulation);

        //clean up
        simulationService.deleteSimulation(savedSimulation.getId());
    }
}
