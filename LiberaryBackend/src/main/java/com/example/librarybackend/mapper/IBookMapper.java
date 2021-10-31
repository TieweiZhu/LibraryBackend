package com.example.librarybackend.mapper;

import com.example.librarybackend.bean.ResourcesBean;
import com.example.librarybackend.bean.CollectBean;
import com.example.librarybackend.bean.UserBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;

public interface IBookMapper {

    @Insert("INSERT INTO resources(UserID,BookID,Name,Introduction,Category,Type,Time,Count,URL) Values(#{UserID},#{BookID},#{Name},#{Introduction},#{Category},#{Type},#{Time},#{Count},#{URL})")
    int insertResourcesBean(String UserID, String BookID, String Name, String Introduction, String Category, String Type, BigInteger Time, Integer Count, String URL);

    @Select("SELECT * FROM resources")
    List<ResourcesBean> selectResources();

    @Select("SELECT * FROM resources WHERE UserID = #{UserID}")
    List<ResourcesBean> selectResources2(String userid);

    @Select("SELECT * FROM resources ORDER BY Time DESC")
    List<ResourcesBean> selectResources3();

    @Select("SELECT * FROM resources ORDER BY Count DESC")
    List<ResourcesBean> selectResources4();

    @Select("SELECT * FROM collect WHERE UserID = #{userid}")
    List<CollectBean> selectCollect(String userid);

    @Select("SELECT * FROM resources WHERE Name like '%${key}%'")
    List<ResourcesBean> searchResources(String key);

    @Select("SELECT * FROM resources WHERE UserID = #{userid} AND Name like '%${key}%'")
    List<ResourcesBean> usearchResources(String key, String userid);

    @Delete("DELETE FROM resources WHERE BookID = #{bookid}")
    int deleteResources(String bookid);

    @Insert("INSERT INTO collect(BookID,UserID,Name,Introduction,Category,Type) Values(#{bookid},#{userid},#{name},#{introduction},#{category},#{type})")
    int insertCollectBean(String bookid, String userid, String name, String introduction, String category, String type);

    @Delete("DELETE FROM collect WHERE BookID = #{bookid}")
    int deleteCollect(String bookid);

    @Select("SELECT * FROM resources WHERE BookID = #{bookid}")
    ResourcesBean downloadResources(String bookid);

    @Insert("INSERT INTO resources(Count) Values(#{count})")
    int countResources(String count);

}
