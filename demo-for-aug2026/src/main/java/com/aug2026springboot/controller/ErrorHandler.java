package com.aug2026springboot.controller;


import com.aug2026springboot.config.MyContollerException;
import com.aug2026springboot.dto.GeneralResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ErrorHandler{
    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> UserNotFound(RuntimeException ex){
        logger.error("Error in UserNotFound {}",ex);
        return ResponseEntity.badRequest().body(new GeneralResponse(ex.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> SQLExceptionHandler(SQLException ex){
        logger.error("Error in SQLExceptionHandler {}",ex);
        return ResponseEntity.badRequest().body(new GeneralResponse(ex.getMessage()));
    }

    @ExceptionHandler(MyContollerException.class)
    public ResponseEntity<?> MyContollerExceptionHandler(MyContollerException ex){
        logger.error("Error in MyContollerExceptionHandler {}",ex);
        return ResponseEntity.badRequest().body(new GeneralResponse(ex.getMessage()));
    }
}
