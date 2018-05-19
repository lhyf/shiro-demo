package org.lhyf.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.lhyf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping(value = "/login/user",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());

        try {
            token.setRememberMe(user.isRememberme());
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "登录成功";
    }

    @ResponseBody
    @RequestMapping(value = "/testrole",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public String testRole(){

        return "访问成功";
    }
    @ResponseBody
    @RequestMapping(value = "/testrole1",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public String testRole1(){

        return "访问成功";
    }
}
