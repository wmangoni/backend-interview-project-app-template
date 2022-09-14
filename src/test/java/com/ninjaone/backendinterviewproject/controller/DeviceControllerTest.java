package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.DeviceService;
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
@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class DeviceControllerTest {
    public static final Long ID = 1L;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeviceService service;

    private Device entity;

    @BeforeEach
    void setup() {
        entity = new Device(ID, "value", DeviceType.WINDOWS_WORKSTATION);
    }

    @Test
    void getDeviceEntity() throws Exception {
        when(service.getDeviceEntity(ID)).thenReturn(Optional.of(entity));

        mockMvc.perform(get("/device/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entity)));
    }

    @Test
    void postDeviceEntity() throws Exception {
        when(service.saveDeviceEntity(any())).thenReturn(entity);

        String DeviceEntityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post("/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DeviceEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(DeviceEntityString));
    }

    @Test
    void deleteDeviceEntity() throws Exception {
        doNothing().when(service).deleteDeviceEntity(ID);

        mockMvc.perform(delete("/device/" + ID))
                .andExpect(status().isNoContent());
    }
}
