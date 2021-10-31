package com.example.librarybackend.servicelmpl;

import com.example.librarybackend.bean.UserBean;
import com.example.librarybackend.mapper.IUserMapper;
import com.example.librarybackend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public UserBean loginIn(String Account, String Password){

        UserBean u = userMapper.selectUserBean(Account,Password);
        return u;
    }

    @Override
    public int signUp(String Account, String Username, String Password, String Email) {
        String UserID = UUID.randomUUID().toString();
        int r = userMapper.insertUserBean(UserID, Account, Username, Password, Email);
        return r;
    }

    @Override
    public int change(String UserID,String Password) {
        int r = userMapper.updateUserBean(UserID, Password);
        return r;
    }

    @Override
    public int aChange(String Account,String Password) {
        int r = userMapper.updateUserBean2(Account, Password);
        return r;
    }
}































