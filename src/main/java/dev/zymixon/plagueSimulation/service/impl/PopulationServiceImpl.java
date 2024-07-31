package dev.zymixon.plagueSimulation.service.impl;

import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.exception.PopulationNotFoundException;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;
import dev.zymixon.plagueSimulation.repository.PopulationRepository;
import dev.zymixon.plagueSimulation.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final SimulationServiceImpl simulationService;
    private final PopulationRepository populationRepository;

    @Autowired
    public PopulationServiceImpl(SimulationServiceImpl simulationService, PopulationRepository populationRepository) {
        this.simulationService = simulationService;
        this.populationRepository = populationRepository;
    }

    public Population generatePopulation(Long simulationId) throws SimulationNotFoundException {
        Simulation simulation = simulationService.getSimulation(simulationId);

        //if already generated delete previous
        if (!simulation.getPopulations().isEmpty()){
//            if (populationRepository.existsById(simulation.getPopulations().get(0).getId())){
//                System.out.println("REPLACED POPULATION WITH NEW");
//                populationRepository.deleteById(simulation.getPopulations().get(0).getId());
//            }
            populationRepository.deleteAllById(simulation.getPopulations().stream().map(Population::getId).collect(Collectors.toSet()));
        }

        Population generatedPopulation = new Population();
        generatedPopulation.setSimulation(simulation);
        generatedPopulation.setDay(1);
        Long randomNumber;

        //infected
        Long population = simulation.getPopulationSize();
        population -= simulation.getStartingPopulationSize();
        generatedPopulation.setInfected(simulation.getStartingPopulationSize());

        //new infected
        generatedPopulation.setNewInfected(simulation.getStartingPopulationSize());

        //healthy
        randomNumber = getRandomNumber(population / 2, population);
        population -= randomNumber;
        generatedPopulation.setHealthy(randomNumber);

        //dead
        randomNumber = getRandomNumber(0L, population);
        population -= randomNumber;
        generatedPopulation.setDead(randomNumber);

        //recovered
        randomNumber = getRandomNumber(0L, population);
        population -= randomNumber;
        generatedPopulation.setRecovered(randomNumber);

        //adding rest of population to healthy
        generatedPopulation.setHealthy(generatedPopulation.getHealthy() + population);
        return generatedPopulation;
    }

    @Override
    public List<Population> saveAllPopulations(List<Population> populations) {
        return populationRepository.saveAll(populations);
    }

    @Override
    public Population getPopulation(Long id) throws PopulationNotFoundException {
        Optional<Population> population = populationRepository.findById(id);

        if (population.isPresent()){
            return population.get();
        }else {
            throw new PopulationNotFoundException();
        }
    }

    @Override
    public List<Population> getAllPopulations() {
        return populationRepository.findAll();
    }

    @Override
    public List<Population> getAllPopulationsWithSimulationId(Long simulationId) {
        return populationRepository.findAllBySimulationId(simulationId);
    }

    @Override
    public Population savePopulation(Population population) {
        return populationRepository.save(population);
    }

    private Long getRandomNumber(Long min, Long max){

        Random rand = new Random();
        return rand.nextLong(max - min + 1) + min;
    }
}
