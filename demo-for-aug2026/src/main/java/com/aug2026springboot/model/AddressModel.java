package com.aug2026springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class AddressModel {
    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long address_id;
//    String user_id;
    String description;
    @Column(insertable = false,updatable = false)
    String created_date_time;


//    by default join column is primary key
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserModel userModel;




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_date_time() {
        return created_date_time;
    }

    public void setCreated_date_time(String created_date_time) {
        this.created_date_time = created_date_time;
    }





}