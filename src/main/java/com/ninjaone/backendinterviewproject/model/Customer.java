package com.ninjaone.backendinterviewproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueCustomerName", columnNames = {"name"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Customer {

    @Id
    private Long id;

    private String name;
}
