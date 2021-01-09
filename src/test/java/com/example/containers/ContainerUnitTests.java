package com.example.containers;

import com.example.containers.model.Container;
import com.example.containers.repository.ContainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContainerUnitTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContainerRepository containerRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAll() throws Exception {
        Container container1 = new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123");
        Container container2 = new Container(1,1420.00, "Speelgoed", "Hong Kong", "Antwerpen","DEF456");

        List<Container> containers = new ArrayList<>();
        containers.add(container1);
        containers.add(container2);

        given(containerRepository.findAll()).willReturn(containers);

        mockMvc.perform(get("/containers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].schipId", is(1)))
                .andExpect(jsonPath("$[0].gewicht", is(2300.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Koelkasten")))
                .andExpect(jsonPath("$[0].startLocatie", is("New York")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$[0].serieCode", is("ABC123")))
                .andExpect(jsonPath("$[1].schipId", is(1)))
                .andExpect(jsonPath("$[1].gewicht", is(1420.00)))
                .andExpect(jsonPath("$[1].inhoud", is("Speelgoed")))
                .andExpect(jsonPath("$[1].startLocatie", is("Hong Kong")))
                .andExpect(jsonPath("$[1].eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$[1].serieCode", is("DEF456")))
                .andExpect(jsonPath("$[2].schipId", is(3)))
                .andExpect(jsonPath("$[2].gewicht", is(2500.00)))
                .andExpect(jsonPath("$[2].inhoud", is("Schoenen")))
                .andExpect(jsonPath("$[2].startLocatie", is("New York")))
                .andExpect(jsonPath("$[2].eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$[2].serieCode", is("HIJ789")))
                .andExpect(jsonPath("$[3].schipId", is(2)))
                .andExpect(jsonPath("$[3].gewicht", is(1000.00)))
                .andExpect(jsonPath("$[3].inhoud", is("Eten")))
                .andExpect(jsonPath("$[3].startLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$[3].eindLocatie", is("Dover")))
                .andExpect(jsonPath("$[3].serieCode", is("KLM012")));
    }

    @Test
    public void unitTestGetContainerBySchipId() throws Exception {
        Container container3 = new Container(3,2500.00, "Schoenen", "New York", "Antwerpen","HIJ789");

        List<Container> containers = new ArrayList<>();
        containers.add(container3);

        given(containerRepository.findContainersBySchipId(3)).willReturn(containers);

        mockMvc.perform(get("/containers/schip/{schipId}", 3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].schipId", is(3)))
                .andExpect(jsonPath("$[0].gewicht", is(2500.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Schoenen")))
                .andExpect(jsonPath("$[0].startLocatie", is("New York")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$[0].serieCode", is("HIJ789")));
    }

    @Test
    public void unitTestGetContainersBetweenGewicht() throws Exception {
        Container container1 = new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123");
        Container container2 = new Container(1,1420.00, "Speelgoed", "Hong Kong", "Antwerpen","DEF456");

        List<Container> containers = new ArrayList<>();
        containers.add(container1);
        containers.add(container2);

        given(containerRepository.findContainersByGewichtBetween(1250.00, 2400.00)).willReturn(containers);

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
                .andExpect(jsonPath("$[1].schipId", is(1)))
                .andExpect(jsonPath("$[1].gewicht", is(1420.00)))
                .andExpect(jsonPath("$[1].inhoud", is("Speelgoed")))
                .andExpect(jsonPath("$[1].startLocatie", is("Hong Kong")))
                .andExpect(jsonPath("$[1].eindLocatie", is("Antwerpen")))
                .andExpect(jsonPath("$[1].serieCode", is("DEF456")));;
    }

    @Test
    public void unitTestGetByInhoud() throws Exception {
        Container container1 = new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123");

        List<Container> containers = new ArrayList<>();
        containers.add(container1);

        given(containerRepository.findContainersByInhoudContaining("Koelkasten")).willReturn(containers);

        mockMvc.perform(get("/containers/inhoud/{inhoud}", "Koelkasten"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].schipId", is(1)))
                .andExpect(jsonPath("$[0].gewicht", is(2300.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Koelkasten")))
                .andExpect(jsonPath("$[0].startLocatie", is("New York")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$[0].serieCode", is("ABC123")));;
    }

    @Test
    public void unitTestGetByStartLocatie() throws Exception {
        Container container4 = new Container(2,1000.00, "Eten", "Amsterdam", "Dover","KLM012");

        List<Container> containers = new ArrayList<>();
        containers.add(container4);

        given(containerRepository.findContainersByStartLocatieContaining("Amsterdam")).willReturn(containers);

        mockMvc.perform(get("/containers/startlocatie/{startLocatie}", "Amsterdam"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].schipId", is(2)))
                .andExpect(jsonPath("$[0].gewicht", is(1000.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Eten")))
                .andExpect(jsonPath("$[0].startLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Dover")))
                .andExpect(jsonPath("$[0].serieCode", is("KLM012")));;
    }

    @Test
    public void unitTestGetByEindLocatie() throws Exception {
        Container container1 = new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123");

        List<Container> containers = new ArrayList<>();
        containers.add(container1);

        given(containerRepository.findContainersByEindLocatieContaining("Amsterdam")).willReturn(containers);

        mockMvc.perform(get("/containers/eindlocatie/{eindLocatie}", "Amsterdam"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].schipId", is(1)))
                .andExpect(jsonPath("$[0].gewicht", is(2300.00)))
                .andExpect(jsonPath("$[0].inhoud", is("Koelkasten")))
                .andExpect(jsonPath("$[0].startLocatie", is("New York")))
                .andExpect(jsonPath("$[0].eindLocatie", is("Amsterdam")))
                .andExpect(jsonPath("$[0].serieCode", is("ABC123")));;
    }

    @Test
    public void unitTestPostContainer() throws Exception {

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
    public void unitTestDeleteContainer() throws Exception {
        Container container1 = new Container(1,2300.00, "Koelkasten", "New York", "Amsterdam","ABC123");

        given(containerRepository.findContainerById(1)).willReturn(container1);

        mockMvc.perform(delete("containers/delete/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
