package com.aug2026springboot.controller;

import com.aug2026springboot.dto.GeneralResponse;
import com.aug2026springboot.dto.UserRequest;
import com.aug2026springboot.model.UserModel;
import com.aug2026springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http:localhost:8080/login/user
//http:localhost:8080/user/login
@RestController
@RequestMapping("/api")
public class HomeController {
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
            if (id == null){
                List<UserModel> list = this.userService.getUserList();
//                if (name != null)
//                    list = list.stream().filter((user)->user.getName().equals(name)).toList();
                return ResponseEntity.ok(list);
            }else{
                UserModel userModel = this.userService.getUserId(id);
                 return ResponseEntity.ok(userModel);
            }
        }catch (Exception e){
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
    public ResponseEntity<?> storeUser(@RequestBody UserRequest userRequest
                          ){

        try{
            userService.storeUser(userRequest);
            return  ResponseEntity.ok(new GeneralResponse("user is stored!!"));
        }catch (Exception e){
//            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }

    }


    @PostMapping("hobby")
    public ResponseEntity<?> storeHobby(@RequestParam Long user_id,@RequestParam String hobby){

        try{
            userService.storeHobby(user_id,hobby);
            return  ResponseEntity.ok(new GeneralResponse("hobby is stored!!"));
        }catch (Exception e){
//            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }

    @PostMapping("user/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest
    ){
        try{
           UserModel userModel =  userService.login(userRequest.getName(),userRequest.getPassword());
            return  ResponseEntity.ok(new GeneralResponse("Success!!user is Logged In!!"));
        }catch (Exception e){
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<?> PUTeUser(@RequestBody UserRequest userRequest,
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
