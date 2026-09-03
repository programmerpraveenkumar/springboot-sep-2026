package com.aug2026springboot.Repository;

import com.aug2026springboot.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long>{
    @Query("select user from UserModel user")
    List<UserModel> getUserList();
    @Query("select user from UserModel user where name=?1 and password=?2")
    Optional<UserModel> login(String name,String password);


}