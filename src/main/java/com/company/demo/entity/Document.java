package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "DOCUMENT")
@Entity
public class Document {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @Column(name = "DATE_")
    private LocalDate date;

    @Column(name = "NOTES")
    private String notes;

    @JmixProperty
    @Transient
    private Actor manager;

    @JmixProperty
    @Transient
    private List<Actor> salespersons;

    public List<Actor> getSalespersons() {
        return salespersons;
    }

    public void setSalespersons(List<Actor> salespersons) {
        this.salespersons = salespersons;
    }

    public Actor getManager() {
        return manager;
    }

    public void setManager(Actor manager) {
        this.manager = manager;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"date", "notes"})
    public String getInstanceName() {
        return String.format("%s %s", date, notes);
    }
}