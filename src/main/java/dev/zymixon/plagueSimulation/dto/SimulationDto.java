package dev.zymixon.plagueSimulation.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationDto {

    private Long id;
    private String name;
    private Long populationSize;
    private Long startingPopulationSize;
    private Long spreadRatio;
    private float deathRatio;
    private Integer recoveryTime;
    private Integer deathTime;
    private Integer simulationTime;
    private List<Long> populationIdList;
}
