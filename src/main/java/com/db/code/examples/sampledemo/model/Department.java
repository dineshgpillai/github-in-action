package com.db.code.examples.sampledemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author Dinesh Pillai
 */

@Entity
@Table(name="department")
public class Department {

    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String description;

    private @Version
    @JsonIgnore
    Long version;

    @OneToOne(mappedBy = "department")
    private Employee employee;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
