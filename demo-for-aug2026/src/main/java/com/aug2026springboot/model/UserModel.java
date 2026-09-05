package com.aug2026springboot.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String address;
    @Column(name = "mobile")
    String phone;

    @OneToOne(mappedBy = "userModel")
    HobbyModel hobbyModel;

    @OneToMany(mappedBy = "userModel")
    List<AddressModel> addressModel;


    public List<AddressModel> getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(List<AddressModel> addressModel) {
        this.addressModel = addressModel;
    }


    public HobbyModel getHobbyModel() {
        return hobbyModel;
    }

    public void setHobbyModel(HobbyModel hobbyModel) {
        this.hobbyModel = hobbyModel;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void     setPhone(String phone) {
        this.phone = phone;
    }


}