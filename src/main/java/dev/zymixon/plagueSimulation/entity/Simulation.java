package dev.zymixon.plagueSimulation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //wielkość populacji
    @Column(name = "population_size")
    private Long populationSize;

    //początkowa liczba osób zakażonych
    @Column(name = "starting_population_size")
    private Long startingPopulationSize;

    @Column(name = "spread_ratio")
    private Long spreadRatio;

    @Column(name = "death_ratio")
    private float deathRatio;

    @Column(name = "recovery_time")
    private Integer recoveryTime;

    @Column(name = "death_time")
    private Integer deathTime;

    @Column(name = "simulation_time")
    private Integer simulationTime;

    @OneToMany(mappedBy="simulation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Population> populations;

    @Override
    public String toString() {
        return "Simulation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", populationSize=" + populationSize +
                ", startingPopulationSize=" + startingPopulationSize +
                ", spreadRatio=" + spreadRatio +
                ", deathRatio=" + deathRatio +
                ", recoveryTime=" + recoveryTime +
                ", deathTime=" + deathTime +
                ", simulationTime=" + simulationTime +
                '}';
    }

    public Simulation(String name, Long populationSize, Long startingPopulationSize, Long spreadRatio, float deathRatio, Integer recoveryTime, Integer deathTime, Integer simulationTime, List<Population> populations) {
        this.name = name;
        this.populationSize = populationSize;
        this.startingPopulationSize = startingPopulationSize;
        this.spreadRatio = spreadRatio;
        this.deathRatio = deathRatio;
        this.recoveryTime = recoveryTime;
        this.deathTime = deathTime;
        this.simulationTime = simulationTime;
        this.populations = populations;
    }
}
