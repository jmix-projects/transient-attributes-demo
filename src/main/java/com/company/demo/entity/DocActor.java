package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "DOC_ACTOR", indexes = {
        @Index(name = "IDX_DOCACTOR_DOC_ID", columnList = "DOC_ID"),
        @Index(name = "IDX_DOCACTOR_ACTOR_ID", columnList = "ACTOR_ID"),
        @Index(name = "IDX_DOCACTOR_ROLE_ID", columnList = "ROLE_ID")
})
@Entity
public class DocActor {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @JoinColumn(name = "DOC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document doc;

    @JoinColumn(name = "ACTOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Actor actor;

    @JoinColumn(name = "ROLE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
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
}