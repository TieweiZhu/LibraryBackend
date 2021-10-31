package com.example.librarybackend.controller;

import com.example.librarybackend.bean.AdminBean;
import com.example.librarybackend.common.GCache;
import com.example.librarybackend.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping(value="Lib")
@CrossOrigin
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @RequestMapping(value="/AdminLogin.action",method= RequestMethod.GET)
    @ResponseBody
    private String AdminLoginAction(@RequestParam(value= "account")String account, @RequestParam(value= "password")String password, HttpServletRequest req) {
        String res ="";
        if(account == null || "".equals(account)|| password == null || "".equals(password)) {
            res = "{\"status\":\"400\",\"errmsg\":\"empty account/pwd\"}";
        }
        String sid = UUID.randomUUID().toString();
        AdminBean u =  adminService.loginIn(account, password);
        if(u != null){
            GCache.LoginAdmin.put(sid, u);
            res = "{\"status\":\"200\",\"sid\":\"" + sid + "\"}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"wrong admin account/pwd\"}";
        }
        return res;
    }
}
