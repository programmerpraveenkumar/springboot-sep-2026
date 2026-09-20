package com.aug2026springboot.dto;

import jakarta.validation.constraints.*;

public class UserDto {
    @NotBlank(message = "Name should not be empty")
    String name;

    String address;

    @NotBlank(message = "Mobile should not be empty")
    @Pattern(regexp = "^[56789]\\d{9}$", message = "Invalid mobile number format")
    String mobile;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 12, message = "Password not exceed 12 characters ")
    String password;

    @NotNull(message = "Age should not be empty")
    Integer age;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
