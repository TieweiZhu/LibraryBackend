package com.example.librarybackend.mapper;

import com.example.librarybackend.bean.AdminBean;
import org.apache.ibatis.annotations.Select;

public interface IAdminMapper {
    @Select("SELECT * FROM admin WHERE Account = #{Account} AND Password = #{Password}")
    AdminBean selectAdminBean(String Account, String Password);
}
