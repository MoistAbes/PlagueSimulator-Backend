package dev.zymixon.plagueSimulation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PopulationDto {

    private Long id;
    private Integer day;
    private Long infected;
    private Long healthy;
    private Long dead;
    private Long recovered;
    private Long simulationId;

}
