package com.ninjaone.backendinterviewproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueCustomerDeviceId", columnNames = {"customerId", "deviceId"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDevice {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="customerId", referencedColumnName="id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deviceId", referencedColumnName="id")
    private Device device;

    private Long quantity;
}
