package com.aug2026springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hobby")
public class HobbyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long hobby_id;
    String name;
    @Column(insertable = false,updatable = false)
    String created_datetime;




    @OneToOne
    @JoinColumn(name = "user_id")
    UserModel userModel;

    public Long getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(Long hobby_id) {
        this.hobby_id = hobby_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }








}