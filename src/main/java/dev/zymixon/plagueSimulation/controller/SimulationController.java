package dev.zymixon.plagueSimulation.controller;

import dev.zymixon.plagueSimulation.dto.PopulationDto;
import dev.zymixon.plagueSimulation.dto.SimulationDto;
import dev.zymixon.plagueSimulation.entity.Population;
import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.exception.SimulationNotFoundException;
import dev.zymixon.plagueSimulation.mapper.PopulationMapper;
import dev.zymixon.plagueSimulation.mapper.SimulationMapper;
import dev.zymixon.plagueSimulation.service.impl.PopulationServiceImpl;
import dev.zymixon.plagueSimulation.service.impl.SimulationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/simulation")
public class SimulationController {

    private final SimulationServiceImpl simulationService;
    private final SimulationMapper simulationMapper;
    private final PopulationServiceImpl populationService;
    private final PopulationMapper populationMapper;

    @Autowired
    public SimulationController(SimulationServiceImpl simulationService, SimulationMapper simulationMapper, PopulationServiceImpl populationService, PopulationMapper populationMapper) {
        this.simulationService = simulationService;
        this.simulationMapper = simulationMapper;
        this.populationService = populationService;
        this.populationMapper = populationMapper;
    }

    @GetMapping
    public ResponseEntity<List<SimulationDto>> getAllSimulations(){
        List<Simulation> simulations = simulationService.getAllSimulations();
        List<SimulationDto> mappedSimulations = simulationMapper.mapToSimulationsDtoList(simulations);
        return ResponseEntity.ok(mappedSimulations);
    }

    @GetMapping("/{simulationId}")
    public ResponseEntity<SimulationDto> getSimulation(@PathVariable Long simulationId) throws SimulationNotFoundException {
        Simulation simulation = simulationService.getSimulation(simulationId);
        SimulationDto mappedSimulation = simulationMapper.mapToSimulationDto(simulation);

        return ResponseEntity.ok(mappedSimulation);
    }

    @PostMapping()
    public ResponseEntity<SimulationDto> saveSimulation(@RequestBody SimulationDto simulationDto){
        Simulation simulation = simulationMapper.mapToSimulation(simulationDto);
        Simulation savedSimulation = simulationService.saveSimulation(simulation);
        SimulationDto mappedSimulation = simulationMapper.mapToSimulationDto(savedSimulation);
        return new ResponseEntity<>(mappedSimulation, HttpStatus.CREATED);
    }

    @GetMapping("/start/{simulationId}")
    public ResponseEntity<List<PopulationDto>> startSimulation(@PathVariable Long simulationId) throws SimulationNotFoundException {
        Simulation simulation = simulationService.getSimulation(simulationId);

        List<Population> processedPopulations = simulationService.processSimulation(simulation);
        List<Population> savedPopulations = populationService.saveAllPopulations(processedPopulations);
        List<PopulationDto> mappedPopulations = populationMapper.mapToPopulationDtoList(savedPopulations);

        return ResponseEntity.ok(mappedPopulations);
    }

    @DeleteMapping("/{simulationId}")
    public ResponseEntity<Void> deleteSimulation(@PathVariable Long simulationId){
        simulationService.deleteSimulation(simulationId);

        return ResponseEntity.ok().build();
    }
}
