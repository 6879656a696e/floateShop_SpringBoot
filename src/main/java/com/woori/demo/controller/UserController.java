package com.woori.demo.controller;

import com.woori.demo.dto.UserDto;
import com.woori.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @PostMapping("/user/signup")
    @ResponseBody
    public String signup(@RequestBody UserDto dto){
        userService.createUser(dto);
        return "회원가입 성공";
    }

    @PostMapping("/user/duplicateCheck")
    @ResponseBody
    public String existUser(@RequestBody UserDto dto){
        boolean emailCheck=userService.existUser(dto.getEmail());
        String result="";
        if(!emailCheck){
            result= "가입이 가능한 이메일 주소입니다.";
        } else {
            result= "이미 존재하는 회원입니다.";
        }
        return result;
    }
}
