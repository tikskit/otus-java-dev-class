package ru.otus.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressDataSet {
    @Id
    private long id;
    @Column(name = "street")
    private String street;

    public AddressDataSet(long id, String street) {
        this.id = id;
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }
}
