package dev.zymixon.plagueSimulation.controller;

import dev.zymixon.plagueSimulation.dto.PopulationDto;
import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.exception.PopulationNotFoundException;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;
import dev.zymixon.plagueSimulation.mapper.PopulationMapper;
import dev.zymixon.plagueSimulation.service.impl.PopulationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/population")
public class PopulationController {

    private final PopulationServiceImpl populationService;
    private final PopulationMapper populationMapper;

    @Autowired
    public PopulationController(PopulationServiceImpl populationService, PopulationMapper populationMapper) {
        this.populationService = populationService;
        this.populationMapper = populationMapper;
    }

    @GetMapping("/{populationId}")
    public ResponseEntity<PopulationDto> getPopulation(@PathVariable Long populationId) throws PopulationNotFoundException {
        Population population = populationService.getPopulation(populationId);
        PopulationDto mappedPopulation = populationMapper.mapToPopulationDto(population);
        return ResponseEntity.ok(mappedPopulation);
    }

    @GetMapping
    public ResponseEntity<List<PopulationDto>> getAllPopulations() {
        List<Population> populations = populationService.getAllPopulations();
        List<PopulationDto> mappedPopulations = populationMapper.mapToPopulationDtoList(populations);

        return ResponseEntity.ok(mappedPopulations);
    }

    @GetMapping("/all/{simulationId}")
    public ResponseEntity<List<PopulationDto>> getAllPopulationsWithSimulationId(@PathVariable Long simulationId){
        List<Population> populations = populationService.getAllPopulationsWithSimulationId(simulationId);
        List<PopulationDto> mappedPopulations = populationMapper.mapToPopulationDtoList(populations);

        return ResponseEntity.ok(mappedPopulations);
    }

    @GetMapping("/generate/{simulationId}")
    public ResponseEntity<PopulationDto> generatePopulation(@PathVariable Long simulationId) throws SimulationNotFoundException {
        Population generatePopulation = populationService.generatePopulation(simulationId);
        Population savedPopulation = populationService.savePopulation(generatePopulation);
        PopulationDto mappedPopulation = populationMapper.mapToPopulationDto(savedPopulation);
        return ResponseEntity.ok(mappedPopulation);
    }
}
