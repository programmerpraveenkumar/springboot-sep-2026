package com.aug2026springboot.cron_job;

import com.aug2026springboot.model.UserModel;
import com.aug2026springboot.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
public class Cron_Sample {

        final UserService userService;
       public Cron_Sample(UserService userService){
            this.userService = userService;
       }


    @Scheduled(cron = "0 0 9 * * *")
    public void sample(){
           try{
               List<UserModel> userList = userService.getUserList();
//               /for extract the details and send the mail or sms
               System.out.println(LocalDateTime.now());
           }catch (Exception e){
               e.printStackTrace();
           }
    }


//    daily 8pm
    @Scheduled(cron = "0 0 20 * * *")
    public void sendReport(){
        try{

//               /for extract the details and send the mail or sms
            System.out.println(LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
