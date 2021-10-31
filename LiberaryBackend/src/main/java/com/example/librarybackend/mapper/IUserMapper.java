package com.example.librarybackend.mapper;

import com.example.librarybackend.bean.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface IUserMapper {

    @Select("SELECT * FROM user WHERE Account = #{Account} AND Password = #{Password}")
    UserBean selectUserBean(String Account, String Password);

    @Insert("INSERT INTO user(UserID,Account,UserName,Password,Email) Values(#{UserID},#{Account},#{UserName},#{Password},#{Email})")
    int insertUserBean(String UserID, String Account, String UserName, String Password, String Email);

    @Update("UPDATE user SET Password = #{Password} WHERE UserID = #{UserID}")
    int updateUserBean(String UserID, String Password);

    @Update("UPDATE user SET Password = #{Password} WHERE Account = #{Account}")
    int updateUserBean2(String Account, String Password);

}
