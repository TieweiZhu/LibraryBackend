package com.example.librarybackend.service;

import com.example.librarybackend.bean.AdminBean;

public interface IAdminService {
    AdminBean loginIn(String Account, String Password);
}
