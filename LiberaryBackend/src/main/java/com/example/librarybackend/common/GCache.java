package com.example.librarybackend.common;

import com.example.librarybackend.bean.AdminBean;
import com.example.librarybackend.bean.UserBean;

import java.util.HashMap;

public class GCache {

    public static HashMap<String, AdminBean> LoginAdmin = new HashMap<String, AdminBean> ();
    public static HashMap<String, UserBean> LoginUser = new HashMap<String, UserBean>();

}
