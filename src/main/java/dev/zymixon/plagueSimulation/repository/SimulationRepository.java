package dev.zymixon.plagueSimulation.repository;

import dev.zymixon.plagueSimulation.entity.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {
}
