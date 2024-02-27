package org.zydd.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Customer extends PanacheEntity {
    @Column
    public String name;

    @Column
    public String email;

    @Column
    public String phoneNumber;
}
