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
public class SimulationServiceTestSuite {

    @Autowired
    PopulationServiceImpl populationService;
    @Autowired
    SimulationServiceImpl simulationService;

    @Test
    void testThatSimulationGeneratesCorrectPopulations() throws SimulationNotFoundException {

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
        Population generatedPopulation = populationService.generatePopulation(savedSimulation.getId());
        populationService.savePopulation(generatedPopulation);

        //When
        List<Population> resultPopulations = simulationService.processSimulation(savedSimulation);

        //Then
        Assertions.assertEquals(resultPopulations.size(), simulation.getSimulationTime());
        resultPopulations
                .forEach(population -> Assertions.assertEquals(1000000, population.getTotalPopulation()));

        //Clean up
        simulationService.deleteSimulation(savedSimulation.getId());

    }
}
