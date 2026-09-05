package com.aug2026springboot.service;

import com.aug2026springboot.Repository.HobbyRepo;
import com.aug2026springboot.Repository.UserRepo;
import com.aug2026springboot.dto.UserRequest;
import com.aug2026springboot.model.HobbyModel;
import com.aug2026springboot.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserService {

    final UserRepo userRepo ;
    final HobbyRepo hobbyRepo;
    public UserService(UserRepo userRepo,HobbyRepo hobbyRepo ){
        this.userRepo=userRepo;
        this.hobbyRepo=hobbyRepo;
    }

    public List<UserModel> getUserList()throws Exception{
        //get all the data from user table.
        List<UserModel> list= this.userRepo.getUserList();
        if (list.size() <=0){
            throw new Exception("No user found");
        }
        return  list;
    }

    public  UserModel login(String name,String password)throws Exception{
        //get all the data from user table.
        UserModel userModel = this.userRepo.login(name,password).orElseThrow(()->new Exception("No user Found"));
        return  userModel;
    }
    public UserModel getUserId(Long id)throws Exception{
        //get all the data from user table.
        return this.userRepo.findById(id).orElseThrow(()->new Exception("NO USER Found"));

    }
    public Boolean storeUser(UserRequest userRequest)throws  Exception{
           UserModel userModel = new UserModel();
           userModel.setName(userRequest.getName());
           userModel.setAddress(userRequest.getAddress());
           userModel.setPhone(userRequest.getMobile());
            //insert the data into the table
           userRepo.save(userModel);
           return  true;
    }

    public Boolean storeHobby(Long user_id,String hobbyName)throws  Exception{
        UserModel userModel = getUserId(user_id);
        HobbyModel hobbyModel = new HobbyModel();
        //one user can have only one hobby..if user has alrady it has to throw the error
        hobbyModel.setName(hobbyName);
        hobbyModel.setUserModel(userModel);
        this.hobbyRepo.save(hobbyModel);
        return  true;
    }

    public Boolean updateUser(Long userId,UserRequest userRequest)throws  Exception{
        UserModel userModel = getUserId(userId);
        if (Objects.nonNull(userRequest.getName())){
            userModel.setName(userRequest.getName());
        }
        if (Objects.nonNull(userRequest.getAddress())){
            userModel.setAddress(userRequest.getAddress());
        }
        if (Objects.nonNull(userRequest.getMobile())){
            userModel.setPhone(userRequest.getMobile());
        }
        userRepo.save(userModel);
        return  true;
    }

    public Boolean deleteUser(Long userId)throws  Exception{
        UserModel userModel = getUserId(userId);
        userRepo.delete(userModel);
        return  true;
    }


}
