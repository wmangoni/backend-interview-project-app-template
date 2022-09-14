package com.ninjaone.backendinterviewproject.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueServiceNameCost", columnNames = {"name", "cost"})
        })
@Getter
public class JobService {
    @Id
    private Long id;

    private String name;
    private String cost;

    public JobService() {
    }

    public JobService(Long id, String name, String cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
}
