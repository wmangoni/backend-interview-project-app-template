package com.ninjaone.backendinterviewproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueServiceNameCost", columnNames = {"name", "cost"})
        })
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
