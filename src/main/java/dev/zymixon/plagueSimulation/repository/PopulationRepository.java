package dev.zymixon.plagueSimulation.repository;

import dev.zymixon.plagueSimulation.entity.Population;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopulationRepository extends JpaRepository<Population, Long> {

    List<Population> findAllBySimulationId(Long simulationId);
}
