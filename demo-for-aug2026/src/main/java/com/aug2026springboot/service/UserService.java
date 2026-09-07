package com.aug2026springboot.service;

import com.aug2026springboot.Repository.HobbyRepo;
import com.aug2026springboot.Repository.UserRepo;
import com.aug2026springboot.config.MyContollerException;
import com.aug2026springboot.dto.UserDto;
import com.aug2026springboot.model.HobbyModel;
import com.aug2026springboot.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    final ObjectMapper objectMapper;
    final UserRepo userRepo ;
    final HobbyRepo hobbyRepo;
    public UserService(UserRepo userRepo,HobbyRepo hobbyRepo,ObjectMapper objectMapper ){
        this.userRepo=userRepo;
        this.objectMapper=objectMapper;
        this.hobbyRepo=hobbyRepo;
    }

    @Cacheable("users")
    public List<UserModel> getUserList()throws Exception{
        //get all the data from user table.
        List<UserModel> list= this.userRepo.getUserList();
        logger.info("inside service");
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
    @Cacheable(value = "user",key="#id")
    public UserModel getUserId(Long id)throws MyContollerException{
        logger.info( "cache added for {} ",id);
        //get all the data from user table.
        return this.userRepo.findById(id).orElseThrow(()->new MyContollerException("NO USER Found"));

    }
    public Boolean storeUser(UserDto userRequest)throws  Exception{
//           UserModel userModel = new UserModel();
             UserModel userModel =  objectMapper.convertValue(userRequest, UserModel.class);
//           userModel.setName(userRequest.getName());
//           userModel.setAddress(userRequest.getAddress());
//           userModel.setPhone(userRequest.getMobile());
            //insert the data into the table
           userRepo.save(userModel);
       return  true;
    }

    public Boolean storeHobby(Long user_id,String hobbyName)throws MyContollerException {
        UserModel userModel = getUserId(user_id);
        Optional<HobbyModel> hobbyOpt = hobbyRepo.getHobbyFromUserId(user_id);
        if (hobbyOpt.isPresent()){
            logger.error("Error Hobby exist for {} {}",user_id,hobbyName);
            throw new MyContollerException("Already hobby found!!");
        }
        HobbyModel hobbyModel = new HobbyModel();
        //one user can have only one hobby..if user has already it has to throw the error
        hobbyModel.setName(hobbyName);
        hobbyModel.setUserModel(userModel);
        this.hobbyRepo.save(hobbyModel);
        return  true;
    }

    public Boolean updateUser(Long userId, UserDto userRequest)throws  Exception{
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
