package socialapp.chatservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.common.UserContextHolder;
import socialapp.chatservice.model.entity.AppUser;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    AppUser get(){
        return UserContextHolder.getCurrentUser();
    }
}
