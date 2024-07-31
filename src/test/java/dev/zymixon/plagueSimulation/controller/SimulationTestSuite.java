package dev.zymixon.plagueSimulation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zymixon.plagueSimulation.dto.SimulationDto;
import dev.zymixon.plagueSimulation.entity.Simulation;
import dev.zymixon.plagueSimulation.service.impl.SimulationServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringJUnitWebConfig
@WebMvcTest(SimulationController.class)
public class SimulationTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    SimulationController simulationController;
    @MockBean
    SimulationServiceImpl simulationService;

    //to be deleted
    @Test
    void testThatPopulationEqualsTotalPopulation(){
        //Given
        int totalPopulation = 1000000;
        int infected = 178058;
        int healthy = 0;
        int dead = 154143;
        int recovered = 667799;

        //When
        int result = infected + healthy + dead + recovered;

        //Then
        Assertions.assertEquals(result, totalPopulation);
    }

    @Test
    void testGetAllSimulations() throws Exception {

        //Given

        //When & then
        ResponseEntity<List<SimulationDto>> simulationResponse = new ResponseEntity<>(List.of(), HttpStatus.OK);
        when(simulationController.getAllSimulations()).thenReturn(simulationResponse);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/simulation")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testGetSimulation() throws Exception {

        ResponseEntity<SimulationDto> simulation = new ResponseEntity<>(new SimulationDto(), HttpStatus.OK);
        Objects.requireNonNull(simulation.getBody()).setId(999L);
        when(simulationController.getSimulation(999L)).thenReturn(simulation);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/simulation/" + 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(999)));

    }

    @Test
    void testTest(){

        Assertions.assertEquals(1,1);
    }



    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
