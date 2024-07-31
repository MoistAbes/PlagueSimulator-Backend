package dev.zymixon.plagueSimulation.service.impl;

import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;
import dev.zymixon.plagueSimulation.repository.SimulationRepository;
import dev.zymixon.plagueSimulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimulationServiceImpl implements SimulationService {

    private final SimulationRepository simulationRepository;

    @Autowired
    public SimulationServiceImpl(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public List<Population> processSimulation(Simulation simulation){

        //printing simulation stats
        System.out.println("SIMULATION: " + simulation);

        //selecting starting population might be changed later ??
        //day 1 population
        Population firstPopulation = simulation.getPopulations().get(0);
        List<Population> populations = new ArrayList<>();
        populations.add(firstPopulation);

        int counter = 1;
        int daysCounter = 2;
        while (daysCounter <= simulation.getSimulationTime()){

            System.out.println("DAY: " + daysCounter);

            //get previous population to calculate
            Population previousPopulation = populations.get(counter - 1);

            //set new simulation
            Population newPopulation = new Population();
            newPopulation.setSimulation(simulation);
            populations.add(newPopulation);
            newPopulation.setInfected(previousPopulation.getInfected());
            newPopulation.setDead(previousPopulation.getDead());

            newPopulation = calculateDeadAmount(
                    daysCounter,
                    simulation,
                    populations,
                    newPopulation
            );

            //calculating recovered amount
            newPopulation = calculateRecoveredAmount(
                    daysCounter,
                    counter,
                    simulation,
                    populations,
                    newPopulation,
                    previousPopulation
            );

            newPopulation = calculateNewInfected(
                    simulation,
                    previousPopulation,
                    newPopulation
            );

            newPopulation.setDay(daysCounter);
            daysCounter++;
            counter++;
            System.out.println("POPULATION: " + newPopulation);
            System.out.println("");

        }
        return populations;
    }

    private Population calculateDeadAmount(
            int daysCounter,
            Simulation simulation,
            List<Population> populations,
            Population newPopulation
    ) {
        //calculating new dead amount
        //only calculate if amount of days passed
        if (daysCounter >= simulation.getDeathTime()){
            Population population = populations.get(daysCounter - simulation.getDeathTime());
            double deathRatio = simulation.getDeathRatio() * 0.01;

            long deadAmount;
            if (population.getNewInfected() != 0){
                deadAmount = (long) (population.getNewInfected() * deathRatio);
                System.out.println("new Dead: " + deadAmount);
                population.setNewInfected(population.getInfected() - deadAmount);

            }else{
                deadAmount = (long) (newPopulation.getInfected() * deathRatio);
                System.out.println("new dead after no new infections: " + deadAmount);
            }

            newPopulation.setInfected(newPopulation.getInfected() - deadAmount);
            newPopulation.setDead(newPopulation.getDead() + deadAmount);
            System.out.println("POPULATION AFTER DEAD: " + newPopulation);
        }
        return newPopulation;
    }

    private Population calculateRecoveredAmount(
            int daysCounter,
            int counter,
            Simulation simulation,
            List<Population> populations,
            Population newPopulation,
            Population prevPopulation
    ){
        //calculating new recevered amount
        if (daysCounter >= simulation.getRecoveryTime()){
            //get population with timer
            Population population = populations.get(daysCounter - simulation.getRecoveryTime());

            Long newRecovered = population.getNewInfected();

            if (newRecovered > newPopulation.getInfected()) {
                newRecovered = newPopulation.getInfected();
                newPopulation.setRecovered(newRecovered + prevPopulation.getRecovered());
                newPopulation.setInfected(0L);
            }
            else {
                newPopulation.setInfected(populations.get(counter).getInfected() - newRecovered);
                newPopulation.setRecovered(populations.get(counter - 1).getRecovered() + newRecovered);
                System.out.println("POPULATION AFTER RECOVERY: " + newPopulation);
            }

            System.out.println("NEW RECORVERED: " + newRecovered + " FROM DAY: " + population.getDay());

        }else {
            newPopulation.setRecovered(populations.get(counter - 1).getRecovered());
        }

        return newPopulation;
    }


    private Population calculateNewInfected(
            Simulation simulation,
            Population previousPopulation,
            Population newPopulation
    ){
        //set new infections
        long newInfected = previousPopulation.getNewInfected() * simulation.getSpreadRatio();
        System.out.println("NEW INFECTED: " + newInfected);
        newPopulation.setHealthy(0L);

        //if there are more infected then healthy
        if (newInfected != 0){
            //System.out.println("NO NEW INFECTED");
            if (newInfected >= previousPopulation.getHealthy()){
                newPopulation.setInfected(newPopulation.getInfected() + previousPopulation.getHealthy());
                newPopulation.setNewInfected(previousPopulation.getHealthy());

            }else{
                newPopulation.setInfected(newPopulation.getInfected() + newInfected);
                newPopulation.setNewInfected(newInfected);
                //set new healthy
                newPopulation.setHealthy(previousPopulation.getHealthy() - newInfected);
            }
        }else {
             newPopulation.setNewInfected(0L);
        }

        return newPopulation;
    }

    @Override
    public Simulation getSimulation(Long id) throws SimulationNotFoundException {
        Optional<Simulation> simulation = simulationRepository.findById(id);
        if (simulation.isPresent()){
            return simulation.get();
        }else {
            throw new SimulationNotFoundException();
        }
    }

    @Override
    public List<Simulation> getAllSimulations() {
        return simulationRepository.findAll();
    }

    @Override
    public Simulation saveSimulation(Simulation simulation) {
        return simulationRepository.save(simulation);
    }

    @Override
    public void deleteSimulation(Long id) {
        simulationRepository.deleteById(id);
    }
}
