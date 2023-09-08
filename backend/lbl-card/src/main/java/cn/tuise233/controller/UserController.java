package cn.tuise233.controller;

import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/checkExist")
    public ResponseResult checkExist(@RequestParam String open_id){
        return userService.checkExist(open_id);
    }

    @GetMapping("/getUserInfo")
    public ResponseResult getUserInfo(@RequestParam String open_id){
        return userService.getUserInfo(open_id);
    }

    @GetMapping("/createUser")
    public ResponseResult createUser(@RequestParam String open_id, @RequestParam String name){
        System.out.println("open_id: " + open_id + "  username: " + name);
        return userService.createUser(open_id, name);
    }
}
