package com.example.librarybackend.servicelmpl;


import com.example.librarybackend.bean.AdminBean;
import com.example.librarybackend.mapper.IAdminMapper;
import com.example.librarybackend.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServicelmpl implements IAdminService {
    @Autowired
    private IAdminMapper AdminMapper;

    @Override
    public AdminBean loginIn(String Account, String Password){

        AdminBean u = AdminMapper.selectAdminBean(Account, Password);
        return u;
    }
}
