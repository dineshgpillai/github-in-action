package com.db.code.examples.sampledemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

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

    public Department(String name,  String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return Objects.equals(id, department.id) &&
                Objects.equals(name, department.name) &&
                Objects.equals(description, department.description) &&
                Objects.equals(version, department.version);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name,  description, version);
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
