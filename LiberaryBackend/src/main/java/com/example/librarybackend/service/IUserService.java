package com.example.librarybackend.service;

import com.example.librarybackend.bean.UserBean;

public interface IUserService {
    UserBean loginIn(String Account, String Password);

    int signUp(String Account, String Username, String Password, String Email);

    int change(String UserID,String Password);

    int aChange(String Account, String Password);
}
