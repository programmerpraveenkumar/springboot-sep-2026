package com.aug2026springboot.controller;

import com.aug2026springboot.config.MyContollerException;
import com.aug2026springboot.dto.GeneralResponse;
import com.aug2026springboot.dto.UserDto;
import com.aug2026springboot.model.UserModel;
import com.aug2026springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http:localhost:8080/login/user
//http:localhost:8080/user/login

@RestController
@RequestMapping("/api")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Value("${JWT_SECURITY_KEY}")
    String jwt_security_key;
    @Value("${COUNTRY}")
    String country;

    @Value("${spring.profiles.active}")
    String active_profile;

    final UserService userService;
    //spring boot will inject the object of userservice
    public HomeController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("login")
    public String login(){
        return "hello spring boot";
    }

    @GetMapping("user")
    public ResponseEntity<?> user(@RequestParam(required = false) Long id,@RequestParam(required = false) String name){
        try{
            logger.info("get user start security key {} profile {} id-param {} name {}",jwt_security_key,active_profile,id,name);
            if (id == null){
                List<UserModel> list = this.userService.getUserList();
                logger.info("total user length {} ",list.size());
                return ResponseEntity.ok(list);
            }else{
                UserModel userModel = this.userService.getUserId(id);
                logger.info("user id ",userModel.getId());
                return ResponseEntity.ok(userModel);
            }
        }catch (Exception e){
            logger.error("Error in user {}",e);
            return  ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }


//http://localhost:8080/user?name=98797
//    required = false make this param as optional
//    @GetMapping("user")
//    public ResponseEntity<?> user(){
//        try{
//            List<UserModel> list = this.userService.getUserList();
//            return ResponseEntity.ok(list);
//        }catch (Exception e){
//            return  ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
//        }
//    }
//
//    @GetMapping("user/{id}")
//    public ResponseEntity<?> user(@PathVariable Long id){
//        try{
//             UserModel userModel = this.userService.getUserId(id);
//            return ResponseEntity.ok(userModel);
//        }catch (Exception e){
//            return  ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
//        }
//    }

    @PostMapping("user")
    public ResponseEntity<?> storeUser(@RequestBody UserDto userRequest
                          ){

        try{
            logger.info("store user started {} {}",userRequest.getName(),userRequest.getMobile());
            userService.storeUser(userRequest);
            logger.info("store user completed ");
            return  ResponseEntity.ok(new GeneralResponse("user is stored!!"));
        }catch (Exception e){
            logger.error("Error in store user {}",e);
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }


    @PostMapping("hobby")
    public ResponseEntity<?> storeHobby(@RequestParam Long user_id,@RequestParam String hobby)throws MyContollerException{
            userService.storeHobby(user_id,hobby);
            return  ResponseEntity.ok(new GeneralResponse("hobby is stored!!"));
    }

    @PostMapping("user/login")
    public ResponseEntity<?> login(@RequestBody UserDto userRequest
    ){
        try{
           UserModel userModel =  userService.login(userRequest.getName(),userRequest.getPassword());
            return  ResponseEntity.ok(new GeneralResponse("Success!!user is Logged In!!"));
        }catch (Exception e){
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<?> PUTeUser(@RequestBody UserDto userRequest,
                           @PathVariable Long id){
        try{
            userService.updateUser(id,userRequest);
            return  ResponseEntity.ok(new GeneralResponse("user is updated!!"));
        }catch (Exception e){
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }

    @PatchMapping("user")
    public String PATCHUser(){
        System.out.println("patch user");
        return "PATCH-this is sample user";
    }

    @DeleteMapping("user")
    public ResponseEntity<?> DELETEUser(@RequestParam Long id){
        try{
            userService.deleteUser(id);
            return  ResponseEntity.ok(new GeneralResponse("user is Deleted!!"));
        }catch (Exception e){
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }
}
