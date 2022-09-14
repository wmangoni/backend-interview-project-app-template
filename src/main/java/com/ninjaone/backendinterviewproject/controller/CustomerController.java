package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.domain.JobServiceType;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import com.ninjaone.backendinterviewproject.model.JobService;
import com.ninjaone.backendinterviewproject.service.CustomerDeviceService;
import com.ninjaone.backendinterviewproject.service.CustomerJobServiceService;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import com.ninjaone.backendinterviewproject.service.JobServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;
    private final CustomerDeviceService customerDeviceService;
    private final CustomerJobServiceService customerJobServiceService;
    private final JobServiceService jobServiceService;

    public CustomerController(
            final CustomerService service,
            final CustomerDeviceService customerDeviceService,
            final CustomerJobServiceService customerJobServiceService,
            final JobServiceService jobServiceService) {
        this.service = service;
        this.customerDeviceService = customerDeviceService;
        this.customerJobServiceService = customerJobServiceService;
        this.jobServiceService = jobServiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Customer postCustomerEntity(@RequestBody Customer device) {
        return service.saveCustomerEntity(device);
    }

    @GetMapping()
    private List<Customer> getCustomers() {
        return service.getCustomers();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCustomerEntity(@PathVariable Long id) {
        Optional<Customer> customer = service.getCustomerEntity(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCustomerEntity(@PathVariable Long id) {
        service.deleteCustomerEntity(id);
    }

    @GetMapping("/{customerId}/calculate-price")
    private BigDecimal calculatePriceByCustomer(@PathVariable Long customerId) {
        List<CustomerDevice> devices = customerDeviceService.getDevicesByCustomerId(customerId);
        List<CustomerJobService> jobServices = customerJobServiceService.getServicesByCustomerId(customerId);

        BigDecimal macQty = customerDeviceService.getMacQty(devices);

        BigDecimal windowsQty = customerDeviceService.getWindowsQty(devices);

        BigDecimal devicesPrice = customerDeviceService.getDevicesPrice(devices);

        BigDecimal jobServicesPrice = customerJobServiceService.getJobServicesCost(jobServices, macQty, windowsQty);

        if (hasAntivirus(jobServices)) {
            List<JobService> jobAntivirusServicesPrice = jobServiceService.getJobServiceByName("Antivirus");
            BigDecimal antivirusMacPrice = new BigDecimal(jobAntivirusServicesPrice
                    .stream()
                    .filter(jobService -> jobService.getName().toUpperCase().contains("MAC"))
                    .map(JobService::getCost)
                    .findFirst()
                    .orElseThrow());

            BigDecimal antivirusWindowsPrice = new BigDecimal(jobAntivirusServicesPrice
                    .stream()
                    .filter(jobService -> jobService.getName().toUpperCase().contains("WINDOWS"))
                    .map(JobService::getCost)
                    .findFirst()
                    .orElseThrow());

            BigDecimal macAntivirusTotalCost = antivirusMacPrice.multiply(macQty);
            BigDecimal windowsAntivirusTotalCost = antivirusWindowsPrice.multiply(windowsQty);

            return devicesPrice.add(jobServicesPrice).add(macAntivirusTotalCost.add(windowsAntivirusTotalCost));
        }

        return devicesPrice.add(jobServicesPrice);
    }

    private boolean hasAntivirus(List<CustomerJobService> jobServices) {
        return jobServices.stream()
                .anyMatch(c -> c.getJobService().getName().equalsIgnoreCase(JobServiceType.ANTIVIRUS.name()));
    }
}
