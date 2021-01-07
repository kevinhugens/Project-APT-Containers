package com.example.containers;

import com.example.containers.model.Container;
import com.example.containers.repository.ContainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContainerIntegrationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContainerRepository containerRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private Container container1 = new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123");
    private Container container2 = new Container(1,1420.00, "Speelgoed", "Hong Kong", "Antwerpen","DEF456");
    private Container container3 = new Container(3,2500.00, "Schoenen", "New York", "Antwerpen","HIJ789");
    private Container container4 = new Container(2,1000.00, "Eten", "Amsterdam", "Dover","KLM012");

    @BeforeEach
    public void beforeAllTests() {
        containerRepository.deleteAll();
        containerRepository.save(container1);
        containerRepository.save(container2);
        containerRepository.save(container3);
        containerRepository.save(container4);
    }

    @AfterEach
    public void afterAllTests() {
        containerRepository.deleteAll();
    }

    @Test
    public void testGetContainerBySchipId() throws Exception {
        mockMvc.perform(get("/containers/schip/{schipId}", 3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schipId", is(3)))
                .andExpect(jsonPath("$.gewicht", is(2500.00)))
                .andExpect(jsonPath("$.inhoud", is("Schoenen")))
                .andExpect(jsonPath("$.startLocatie", is("New York")))
                .andExpect(jsonPath("$.eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$.serieCode", is("HIJ789")));
    }

    @Test
    public void testGetContainerBySerieCode() throws Exception {
        mockMvc.perform(get("/containers/serieCode/{serieCode}", "HIJ789"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schipId", is(3)))
                .andExpect(jsonPath("$.gewicht", is(2500.00)))
                .andExpect(jsonPath("$.inhoud", is("Schoenen")))
                .andExpect(jsonPath("$.startLocatie", is("New York")))
                .andExpect(jsonPath("$.eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$.serieCode", is("HIJ789")));
    }

    @Test
    public void testGetContainersBetweenGewicht() throws Exception {
        List<Container> containers = new ArrayList<>();
        containers.add(container1);
        containers.add(container2);
        containers.add(container3);
        containers.add(container4);

        mockMvc.perform(get("/containers/gewicht/{gewichtMin}/{gewichtMax}", 1250.00, 2400.00))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].schipId", is(1)))
                .andExpect(jsonPath("$[0].gewicht", is(2300.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Koelkasten")))
                .andExpect(jsonPath("$[0].startLocatie", is("New York")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$[0].serieCode", is("ABC123")))
                .andExpect(jsonPath("$[0].schipId", is(1)))
                .andExpect(jsonPath("$[0].gewicht", is(1420.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Speelgoed")))
                .andExpect(jsonPath("$[0].startLocatie", is("Hong Kong")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$[0].serieCode", is("DEF456")));;
    }

    @Test
    public void testGetByInhoud() throws Exception {
        mockMvc.perform(get("/containers/inhoud/{inhoud}", "Koelkasten"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schipId", is(1)))
                .andExpect(jsonPath("$.gewicht", is(2300.00)))
                .andExpect(jsonPath("$.inhoud", is("Koelkasten")))
                .andExpect(jsonPath("$.startLocatie", is("New York")))
                .andExpect(jsonPath("$.eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$.serieCode", is("ABC123")));
    }

    @Test
    public void testGetByStartLocatie() throws Exception {
        mockMvc.perform(get("/containers/startlocatie/{startLocatie}", "Amsterdam"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schipId", is(2)))
                .andExpect(jsonPath("$.gewicht", is(1000.00)))
                .andExpect(jsonPath("$.inhoud", is("Eten")))
                .andExpect(jsonPath("$.startLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$.eindLocatie", is("Dover")))
                .andExpect(jsonPath("$.serieCode", is("KLM012")));
    }

    @Test
    public void testGetByEindLocatie() throws Exception {
        mockMvc.perform(get("/containers/eindlocatie/{eindLocatie}", "Amsterdam"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schipId", is(1)))
                .andExpect(jsonPath("$.gewicht", is(2300.00)))
                .andExpect(jsonPath("$.inhoud", is("Koelkasten")))
                .andExpect(jsonPath("$.startLocatie", is("New York")))
                .andExpect(jsonPath("$.eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$.serieCode", is("ABC123")));
    }

    @Test
    public void testPostContainer() throws Exception {
        Container containerTest = new Container(2,4300.00, "Tennisballen", "Helsinki", "Amsterdam","da57e");

        mockMvc.perform(post("/containers/insert")
                .content(mapper.writeValueAsString(containerTest))
                .contentType("application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schipId", is(2)))
                .andExpect(jsonPath("$.gewicht", is(4300.00)))
                .andExpect(jsonPath("$.inhoud", is("Tennisballen")))
                .andExpect(jsonPath("$.startLocatie", is("Helsinki")))
                .andExpect(jsonPath("$.eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$.serieCode", is("da57e")));
    }

    @Test
    public void testDeleteContainer() throws Exception {
        mockMvc.perform(delete("containers/delete/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
