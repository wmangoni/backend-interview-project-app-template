package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.domain.JobServiceType;
import com.ninjaone.backendinterviewproject.model.*;
import com.ninjaone.backendinterviewproject.service.CustomerDeviceService;
import com.ninjaone.backendinterviewproject.service.CustomerJobServiceService;
import com.ninjaone.backendinterviewproject.service.CustomerService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class CustomerControllerTest {
    public static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CustomerService service;

    @MockBean
    private CustomerDeviceService customerDeviceService;

    @MockBean
    private CustomerJobServiceService customerJobServiceService;

    private Customer entity;

    private static final String path = "/customer";

    @BeforeEach
    void setup() {
        entity = new Customer(ID, "value");
    }

    @Test
    void getCustomerEntity() throws Exception {
        when(service.getCustomerEntity(ID)).thenReturn(Optional.of(entity));

        mockMvc.perform(get(path + "/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entity)));
    }

    @Test
    void postCustomerEntity() throws Exception {
        when(service.saveCustomerEntity(any())).thenReturn(entity);

        String DeviceEntityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DeviceEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(DeviceEntityString));
    }

    @Test
    void deleteCustomerEntity() throws Exception {
        doNothing().when(service).deleteCustomerEntity(ID);

        mockMvc.perform(delete(path + "/" + ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void calculatePriceByCustomerWith32$() throws Exception {
        List<CustomerDevice> devices = List.of(
                new CustomerDevice(
                        1L,
                        new Customer(1L, "name"),
                        new Device(1L, "mac", DeviceType.MAC),
                        1L),
                new CustomerDevice(
                        1L,
                        new Customer(1L, "name"),
                        new Device(1L, "windows", DeviceType.WINDOWS_WORKSTATION),
                        2L));

        BigDecimal macQty = devices.stream()
                .filter(c -> c.getDevice().getType() == DeviceType.MAC)
                .map(c -> BigDecimal.valueOf(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal windowsQty = devices.stream()
                .filter(c -> c.getDevice().getType() != DeviceType.MAC)
                .map(c -> BigDecimal.valueOf(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal devicesPrice = devices.stream()
                .map(customerDevice -> new BigDecimal("4.0").multiply(BigDecimal.valueOf(customerDevice.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CustomerJobService> jobServices = List.of(
                new CustomerJobService(
                        1L,
                        new Customer(1L, "name"),
                        new JobService(1L, "antivirus", "5.0")),
                new CustomerJobService(
                        1L,
                        new Customer(1L, "name"),
                        new JobService(1L, "screen share", "1.0")));

        BigDecimal jobServicesPrice = jobServices.stream()
                .filter(cjs -> !cjs.getJobService().getName().toUpperCase().equals(JobServiceType.ANTIVIRUS.name()))
                .map(js -> new BigDecimal(js.getJobService().getCost()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(macQty.add(windowsQty));

        when(customerDeviceService.getDevicesByCustomerId(any())).thenReturn(devices);
        when(customerJobServiceService.getServicesByCustomerId(any())).thenReturn(jobServices);

        when(customerDeviceService.getMacQty(any())).thenReturn(macQty);
        when(customerDeviceService.getWindowsQty(any())).thenReturn(windowsQty);
        when(customerDeviceService.getDevicesPrice(any())).thenReturn(devicesPrice);

        when(customerJobServiceService.getJobServicesCost(any(), any(), any())).thenReturn(jobServicesPrice);

        mockMvc.perform(get(path + "/" + ID + "/calculate-price"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new BigDecimal("32.0").toString()));
    }

    @Test
    void calculatePriceByCustomerWith80$() throws Exception {
        List<CustomerDevice> devices = List.of(
                new CustomerDevice(
                        1L,
                        new Customer(1L, "name"),
                        new Device(1L, "mac", DeviceType.MAC),
                        5L),
                new CustomerDevice(
                        1L,
                        new Customer(1L, "name"),
                        new Device(1L, "windows", DeviceType.WINDOWS_WORKSTATION),
                        2L));

        BigDecimal macQty = devices.stream()
                .filter(c -> c.getDevice().getType() == DeviceType.MAC)
                .map(c -> BigDecimal.valueOf(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal windowsQty = devices.stream()
                .filter(c -> c.getDevice().getType() != DeviceType.MAC)
                .map(c -> BigDecimal.valueOf(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal devicesPrice = devices.stream()
                .map(customerDevice -> new BigDecimal("4.0").multiply(BigDecimal.valueOf(customerDevice.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CustomerJobService> jobServices = List.of(
                new CustomerJobService(
                        1L,
                        new Customer(1L, "name"),
                        new JobService(1L, "antivirus", "5.0")),
                new CustomerJobService(
                        1L,
                        new Customer(1L, "name"),
                        new JobService(1L, "screen share", "1.0")));

        BigDecimal jobServicesPrice = jobServices.stream()
                .filter(cjs -> !cjs.getJobService().getName().toUpperCase().equals(JobServiceType.ANTIVIRUS.name()))
                .map(js -> new BigDecimal(js.getJobService().getCost()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(macQty.add(windowsQty));

        when(customerDeviceService.getDevicesByCustomerId(any())).thenReturn(devices);
        when(customerJobServiceService.getServicesByCustomerId(any())).thenReturn(jobServices);

        when(customerDeviceService.getMacQty(any())).thenReturn(macQty);
        when(customerDeviceService.getWindowsQty(any())).thenReturn(windowsQty);
        when(customerDeviceService.getDevicesPrice(any())).thenReturn(devicesPrice);

        when(customerJobServiceService.getJobServicesCost(any(), any(), any())).thenReturn(jobServicesPrice);

        mockMvc.perform(get(path + "/" + ID + "/calculate-price"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new BigDecimal("80.0").toString()));
    }
}
