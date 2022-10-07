package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.CustomerDeviceService;
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
@WebMvcTest(CustomerDeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
public class CustomerDeviceControllerTest {
    public static final Long ID = 1L;
    private static final String path = "/customer-device";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerDeviceService service;
    private CustomerDevice entity;

    @BeforeEach
    void setup() {
        entity = new CustomerDevice(ID, new Customer(1L, "name"), new Device(1L, "name", DeviceType.MAC), 1L);
    }

    @Test
    void getCustomerDeviceEntity() throws Exception {
        when(service.getCustomerDeviceEntity(ID)).thenReturn(Optional.of(entity));

        mockMvc.perform(get(path + "/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entity)));
    }

    @Test
    void postCustomerDeviceEntity() throws Exception {
        when(service.saveCustomerDeviceEntity(any())).thenReturn(entity);

        String DeviceEntityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DeviceEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(DeviceEntityString));
    }

    @Test
    void deleteCustomerDeviceEntity() throws Exception {
        doNothing().when(service).deleteCustomerDeviceEntity(ID);

        mockMvc.perform(delete(path + "/" + ID))
                .andExpect(status().isNoContent());
    }
}
