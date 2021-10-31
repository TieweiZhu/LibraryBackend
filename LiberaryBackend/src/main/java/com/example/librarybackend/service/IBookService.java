package com.example.librarybackend.service;

import com.example.librarybackend.bean.ResourcesBean;
import com.example.librarybackend.bean.CollectBean;

import java.util.List;

public interface IBookService {


    int Upload(String userid, String bookid, String Name, String Introduction, String Category, String Type, String path);
    int UUpload(String userid, String bookid, String Name, String Introduction, String Category, String Type, String path);
    List<ResourcesBean> show();
    List<ResourcesBean> pshow(String userid);
    List<ResourcesBean> lshow();
    List<ResourcesBean> dshow();
    List<CollectBean> cshow(String userid);
    List<ResourcesBean> search(String key);
    List<ResourcesBean> usearch(String key, String userid);
    int delete(String bookid);
    int collect(String bookid, String userid, String name, String introduction, String category, String type);
    int cdelete(String bookid);
    ResourcesBean download(String bookid);
    int count(String count);
}
