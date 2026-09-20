package com.aug2026springboot.controller;

import com.aug2026springboot.config.MyContollerException;
import com.aug2026springboot.dto.GeneralResponse;
import com.aug2026springboot.dto.UserDto;
import com.aug2026springboot.model.UserModel;
import com.aug2026springboot.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

//http:localhost:8080/login/user
//http:localhost:8080/user/login

@RestController
@RequestMapping("/api")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    String UPLOAD_DIR = "/Users/praveen/Documents/projects/uploads/";
    @Value("${JWT_SECURITY_KEY}")
    String jwt_security_key;
    @Value("${COUNTRY}")
    String country;

    private final JavaMailSender javaMailSender;
    @Value("${spring.profiles.active}")
    String active_profile;

    final UserService userService;
    //spring boot will inject the object of userservice
    public HomeController(UserService userService,JavaMailSender javaMailSender){
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }
    @GetMapping("login")
    public String login(){
        return "hello spring boot";
    }

    @GetMapping("user")
    public ResponseEntity<?> user(@RequestParam(required = false) Long id,
                                  @RequestParam(required = false) String name,
                                @RequestParam(required = false,defaultValue = "0") Integer pageNumber,
                              @RequestParam(required = false,defaultValue = "10") Integer pageSize
    ){
        try{
            logger.info("get user start security key {} profile {} id-param {} name {}",jwt_security_key,active_profile,id,name);
            if (id == null){
                List<UserModel> list = this.userService.getUserList(pageNumber,pageSize);
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
    public ResponseEntity<?> storeUser(@Valid @RequestBody UserDto userRequest
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

    @PostMapping("file")
    public ResponseEntity<?> file_upload(MultipartFile file){
        try{

            Path dir = Paths.get(UPLOAD_DIR+file.getOriginalFilename());
            Files.createDirectories(dir);
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            Files.copy(file.getInputStream(),dir, StandardCopyOption.REPLACE_EXISTING);
            return  ResponseEntity.ok(new GeneralResponse(file.getOriginalFilename()));

        }catch (Exception e){
            logger.error("Error in store user {}",e);
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }

    @PostMapping("files")
    public ResponseEntity<?> file_upload(MultipartFile[] files){
        try{
            for (MultipartFile file : files){
                Path dir = Paths.get(UPLOAD_DIR+file.getOriginalFilename());
                Files.createDirectories(dir);
                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                Files.copy(file.getInputStream(),dir, StandardCopyOption.REPLACE_EXISTING);
            }
            return  ResponseEntity.ok(new GeneralResponse(files.length+" file uploaded"));
        }catch (Exception e){
            logger.error("Error in store user {}",e);
            return new ResponseEntity<>(new GeneralResponse(e.getMessage()), HttpStatus.valueOf(404));
        }
    }

    @GetMapping("file")
    public ResponseEntity<?> file_download(@RequestParam String filename){
        try{
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
            Resource resource =  new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO file found test");
            }
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).
                    header(HttpHeaders.CONTENT_DISPOSITION,"attachement;filename="+filename)
                    .body(resource);
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

    @PostMapping("user/email")
    public ResponseEntity<?> email(){
        try{
            System.out.println("email started");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("demo@local");
            simpleMailMessage.setSubject("test");
            simpleMailMessage.setTo("demo2@localhost");
            simpleMailMessage.setText("sample mail content");

            javaMailSender.send(simpleMailMessage);

            return  ResponseEntity.ok(new GeneralResponse("Email sent!!"));
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
