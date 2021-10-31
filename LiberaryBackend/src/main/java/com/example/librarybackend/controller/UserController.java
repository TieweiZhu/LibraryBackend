package com.example.librarybackend.controller;


import com.example.librarybackend.bean.UserBean;
import com.example.librarybackend.common.GCache;
import com.example.librarybackend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping(value="Lib")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;


    @RequestMapping(value="/Login.action",method=RequestMethod.GET)
    @ResponseBody
    private String LoginAction(@RequestParam(value= "account")String account, @RequestParam(value= "password")String password, HttpServletRequest req) {
        String res ="";
        if(account == null || "".equals(account)|| password == null || "".equals(password)) {
            res = "{\"status\":\"400\",\"errmsg\":\"empty account/pwd\"}";
        }
        String sid = UUID.randomUUID().toString();
        UserBean u =  userService.loginIn(account, password);
        if(u != null){
            GCache.LoginUser.put(sid, u);
            res = "{\"status\":\"200\",\"sid\":\"" + sid + "\"}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"wrong account/pwd\"}";
        }
        return res;
    }


    @RequestMapping(value="/Signup.action",method=RequestMethod.GET)
    @ResponseBody
    private String SignupAction(@RequestParam(value = "account") String account,@RequestParam(value = "username")String username, @RequestParam(value = "password")String password, @RequestParam(value = "email")String email, HttpServletRequest req){
        String res = "";
        if(account == null || username == null || password == null || email == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Incomplete registration information\"}";
        }
        HttpSession session =  req.getSession();
        int suc = userService.signUp(account, username, password, email);
        if (suc > 0){
            res = "{\"status\":\"200\"}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"Account or name is already used\"}";
        }
        return res;
    }

    @RequestMapping(value="/Change.action",method=RequestMethod.GET)
    @ResponseBody
    private String ChangeAction(@RequestParam(value = "oldpassword") String oldpassword,@RequestParam(value = "newpassword")String newpassword, @RequestParam(value = "sid")String sid, HttpServletRequest req){
        String res = "";
        if(oldpassword == null || newpassword == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Please enter the old password and the new password\"}";
        }
        if(GCache.LoginUser.containsKey(sid)) {
            UserBean ad = GCache.LoginUser.get(sid);
            if (ad == null) {
                res = "{\"status\":\"400\",\"errmsg\":\"Failed\"}";
            } else {
                String userid = ad.getUserID();
                String password = ad.getPassword();
                if (userid == null || "".equals(userid) || oldpassword == null || "".equals(password)) {
                    res = "{\"status\":\"400\",\"errmsg\":\"empty account/pwd\"}";
                } else {
                    int cp = userService.change(userid, newpassword);
                    if (cp > 0) {
                        res = "{\"status\":\"200\"}";
                    } else {
                        res = "{\"status\":\"400\",\"errmsg\":\"Failed to change password\"}";
                    }
                }
            }
        }
        return res;
    }

    @RequestMapping(value="/AChange.action",method=RequestMethod.GET)
    @ResponseBody
    private String AChangeAction(@RequestParam(value = "account") String account,@RequestParam(value = "password")String password, HttpServletRequest req){
        String res = "";
        if(account == null || password == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"The user's account and new password cannot be empty\"}";
        }
        int suc = userService.aChange(account, password);
        if (suc > 0){
            res = "{\"status\":\"200\"}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"Failed to change password\"}";
        }
        return res;
    }

}
