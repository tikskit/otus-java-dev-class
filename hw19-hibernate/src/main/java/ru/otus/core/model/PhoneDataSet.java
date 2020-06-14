package ru.otus.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phones")
public class PhoneDataSet {
    @Id
    private long id;
    @Column(name = "phone")
    private String phone;

    public PhoneDataSet(long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
