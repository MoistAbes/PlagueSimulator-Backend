package dev.zymixon.plagueSimulation.mapper;

import dev.zymixon.plagueSimulation.dto.SimulationDto;
import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.repository.PopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulationMapper {

    private final PopulationRepository populationRepository;

    @Autowired
    public SimulationMapper(PopulationRepository populationRepository) {
        this.populationRepository = populationRepository;
    }

    public SimulationDto mapToSimulationDto(Simulation simulation){

        System.out.println("SIMULATION FROM FRONTEND");

        return SimulationDto.builder()
                .id(simulation.getId())
                .name(simulation.getName())
                .populationSize(simulation.getPopulationSize())
                .startingPopulationSize(simulation.getStartingPopulationSize())
                .spreadRatio(simulation.getSpreadRatio())
                .deathRatio(simulation.getDeathRatio())
                .recoveryTime(simulation.getRecoveryTime())
                .deathTime(simulation.getDeathTime())
                .simulationTime(simulation.getSimulationTime())
                .populationIdList(simulation.getPopulations().stream()
                        .map(Population::getId)
                        .toList())
                .build();
    }

    public Simulation mapToSimulation(SimulationDto simulationDto){

        System.out.println("SIMULATION UPDATED: " + simulationDto);

        return Simulation.builder()
                .id(simulationDto.getId())
                .name(simulationDto.getName())
                .populationSize(simulationDto.getPopulationSize())
                .startingPopulationSize(simulationDto.getStartingPopulationSize())
                .spreadRatio(simulationDto.getSpreadRatio())
                .deathRatio(simulationDto.getDeathRatio())
                .recoveryTime(simulationDto.getRecoveryTime())
                .deathTime(simulationDto.getDeathTime())
                .simulationTime(simulationDto.getSimulationTime())
                .populations(populationRepository.findAllById(simulationDto.getPopulationIdList()))
                .build();
    }


    public List<SimulationDto> mapToSimulationsDtoList(List<Simulation> simulations){

        return simulations.stream()
                .map(this::mapToSimulationDto)
                .toList();
    }
}


