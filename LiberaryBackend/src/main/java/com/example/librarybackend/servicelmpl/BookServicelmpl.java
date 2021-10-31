package com.example.librarybackend.servicelmpl;

import com.example.librarybackend.bean.ResourcesBean;
import com.example.librarybackend.bean.CollectBean;
import com.example.librarybackend.bean.UserBean;
import com.example.librarybackend.mapper.IBookMapper;
import com.example.librarybackend.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.util.List;

@Service
public class BookServicelmpl implements IBookService {

    @Autowired
    private IBookMapper bookMapper;

    @Override
    public int Upload(String userid, String bookid, String Name, String Introduction, String Category, String Type, String path) {

        long time = System.currentTimeMillis();
        BigInteger t = BigInteger.valueOf(time);
        int r = bookMapper.insertResourcesBean(userid, bookid, Name, Introduction, Category, Type, t, 0, path);
        return r;
    }

    @Override
    public int UUpload(String userid, String bookid, String Name, String Introduction, String Category, String Type, String path) {

        long time = System.currentTimeMillis();
        BigInteger t = BigInteger.valueOf(time);
        int r = bookMapper.insertResourcesBean(userid, bookid, Name, Introduction, Category, Type, t, 0, path);
        return r;
    }

    @Override
    public List<ResourcesBean> show(){
        List<ResourcesBean> r = bookMapper.selectResources();
        return r;
    }

    @Override
    public List<ResourcesBean> pshow(String userid){

        List<ResourcesBean> r = bookMapper.selectResources2(userid);
        return r;
    }

    @Override
    public List<ResourcesBean> lshow(){

        List<ResourcesBean> r = bookMapper.selectResources3();
        return r;
    }

    @Override
    public List<ResourcesBean> dshow(){

        List<ResourcesBean> r = bookMapper.selectResources4();
        return r;
    }

    @Override
    public List<CollectBean> cshow(String userid){

        List<CollectBean> r = bookMapper.selectCollect(userid);
        return r;
    }

    @Override
    public List<ResourcesBean> search(String key){

        List<ResourcesBean> r = bookMapper.searchResources(key);
        return r;
    }

    @Override
    public List<ResourcesBean> usearch(String key, String userid){

        List<ResourcesBean> r = bookMapper.usearchResources(key, userid);
        return r;
    }


    @Override
    public int delete(String bookid){

        int r = bookMapper.deleteResources(bookid);
        return r;
    }

    @Override
    public int collect(String bookid,String useid, String name, String introduction, String category, String type){

        int r = bookMapper.insertCollectBean(bookid,useid,name,introduction,category,type);
        return r;
    }

    @Override
    public int cdelete(String bookid){

        int r = bookMapper.deleteCollect(bookid);
        return r;
    }

    @Override
    public ResourcesBean download(String bookid){

        ResourcesBean r = bookMapper.downloadResources(bookid);
        return r;
    }

    @Override
    public int count(String count){

        int r = bookMapper.countResources(count);
        return r;
    }

}









