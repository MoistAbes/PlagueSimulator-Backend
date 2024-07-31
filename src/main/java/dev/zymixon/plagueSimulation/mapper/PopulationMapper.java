package dev.zymixon.plagueSimulation.mapper;

import dev.zymixon.plagueSimulation.dto.PopulationDto;
import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;
import dev.zymixon.plagueSimulation.service.impl.SimulationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopulationMapper {

    private SimulationServiceImpl simulationService;

    @Autowired
    public PopulationMapper(SimulationServiceImpl simulationService) {
        this.simulationService = simulationService;
    }

    public PopulationDto mapToPopulationDto(Population population){

        return PopulationDto.builder()
                .id(population.getId())
                .day(population.getDay())
                .infected(population.getInfected())
                .healthy(population.getHealthy())
                .dead(population.getDead())
                .recovered(population.getRecovered())
                .simulationId(population.getSimulation().getId())
                .build();
    }

    public Population mapToPopulation(PopulationDto populationDto) throws SimulationNotFoundException {
        return Population.builder()
                .id(populationDto.getId())
                .day(populationDto.getDay())
                .infected(populationDto.getInfected())
                .healthy(populationDto.getHealthy())
                .dead(populationDto.getDead())
                .recovered(populationDto.getRecovered())
                .simulation(simulationService.getSimulation(populationDto.getSimulationId()))
                .build();
    }


    public List<PopulationDto> mapToPopulationDtoList(List<Population> populations){

        return populations.stream()
                .map(this::mapToPopulationDto)
                .toList();
    }
}
