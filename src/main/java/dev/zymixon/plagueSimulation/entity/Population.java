package dev.zymixon.plagueSimulation.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer day;
    private Long infected;
    @Column(name = "new_infected")
    private Long newInfected;
    private Long healthy;
    private Long dead;
    private Long recovered;

    @ManyToOne
    @JoinColumn(name="simulation_id", nullable=false)
    private Simulation simulation;

    @Override
    public String toString() {
        return "Population{" +
                "id=" + id +
                ", day=" + day +
                ", infected=" + infected +
                ", newInfected=" + newInfected +
                ", healthy=" + healthy +
                ", dead=" + dead +
                ", recovered=" + recovered +
                '}';
    }

    public Long getTotalPopulation(){
        Long totalPopulation = 0L;
        totalPopulation += infected;
        totalPopulation += healthy;
        totalPopulation += dead;
        totalPopulation += recovered;

        return totalPopulation;
    }


}
