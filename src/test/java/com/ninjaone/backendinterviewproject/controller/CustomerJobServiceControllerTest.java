package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import com.ninjaone.backendinterviewproject.model.JobService;
import com.ninjaone.backendinterviewproject.service.CustomerJobServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@WebMvcTest(CustomerJobServiceController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class CustomerJobServiceControllerTest {
    public static final Long ID = 1L;
    private static final String path = "/customer-jobservices";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerJobServiceService service;
    private CustomerJobService entity;

    @BeforeEach
    void setup() {
        entity = new CustomerJobService(
                ID,
                new Customer(1L, "customerName"),
                new JobService(1L, "serviceName", "1.0"));
    }

    @Test
    void getCustomerJobServiceEntity() throws Exception {
        when(service.getCustomerJobServiceEntity(ID)).thenReturn(Optional.of(entity));

        mockMvc.perform(get(path + "/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entity)));
    }

    @Test
    void postCustomerJobServiceEntity() throws Exception {
        when(service.saveCustomerJobServiceEntity(any())).thenReturn(entity);

        String DeviceEntityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DeviceEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(DeviceEntityString));
    }

    @Test
    void deleteCustomerJobServiceEntity() throws Exception {
        doNothing().when(service).deleteCustomerJobServiceEntity(ID);

        mockMvc.perform(delete(path + "/" + ID))
                .andExpect(status().isNoContent());
    }
}
