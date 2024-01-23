package socialapp.chatservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.common.UserContextHolder;
import socialapp.chatservice.model.entity.AppUser;

@RestController
public class TestController {

    @GetMapping
    AppUser get(){
        return UserContextHolder.getCurrentUser();
    }

    @MessageMapping("/test")
    void test(@Header("profile") String profile) throws
            JsonProcessingException{
        System.out.println(new ObjectMapper().readValue(profile, AppUser.class));
    }


}
