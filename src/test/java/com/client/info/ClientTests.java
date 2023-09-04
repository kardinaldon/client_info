package com.client.info;

import com.client.info.controller.ClientController;
import com.client.info.model.entity.Client;
import com.client.info.service.impl.ClientInfoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
public class ClientTests {

    @MockBean
    private ClientInfoServiceImpl clientInfoService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    private List<Client> clients;
    private Client client;
    @Value("${values.url.client.controller}")
    private String urlClientController;
    @Value("${values.url.client.new}")
    private String urlNewClient;
    @Value("${values.url.client.by_id}")
    private String urlClientById;
    @Value("${values.url.client.all}")
    private String urlClientAll;

    @Test
    void getById() throws Exception {
        client = new Client();
        client.setClientId(1L);
        client.setName("Ivan");
        client.setPatronymic("Ivanovich");
        client.setSurname("Ivanov");
        when(clientInfoService.clientById(any(Long.class))).thenReturn(Optional.of(client));
        ResultActions response = mockMvc.perform(get("/"+urlClientController+"/"+urlClientAll)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("id", "1")
        );
        response.andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        client = new Client();
        client.setClientId(1L);
        client.setName("Ivan");
        client.setPatronymic("Ivanovich");
        client.setSurname("Ivanov");
        clients = new ArrayList<>();
        clients.add(client);
        when(clientInfoService.allClients()).thenReturn(clients);
        ResultActions response = mockMvc.perform(get("/"+urlClientController+"/"+urlClientById)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        response.andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    void newClient() throws Exception {
        client = new Client();
        client.setClientId(1L);
        client.setName("Ivan");
        client.setPatronymic("Ivanovich");
        client.setSurname("Ivanov");
        mapper = new ObjectMapper();
        when(clientInfoService.newClient(any(Client.class))).thenReturn(client);
        ResultActions response = mockMvc.perform(post("/"+urlClientController+"/"+urlNewClient)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(client)));
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(client.getName())));
    }


}
