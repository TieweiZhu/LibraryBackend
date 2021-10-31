package com.example.librarybackend.controller;

import com.alibaba.fastjson.JSON;
import com.example.librarybackend.bean.AdminBean;
import com.example.librarybackend.bean.ResourcesBean;
import com.example.librarybackend.bean.UserBean;
import com.example.librarybackend.bean.CollectBean;
import com.example.librarybackend.common.GCache;
import com.example.librarybackend.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="Lib")
@CrossOrigin
public class BookController {

    @Autowired
    private IBookService bookService;

    @RequestMapping(value="/Upload.action",method= RequestMethod.POST) //管理员上传功能
    @ResponseBody
    private String UploadAction(@RequestParam(value = "name") String name, @RequestParam(value = "introduction")String introduction, @RequestParam(value = "category")String category, @RequestParam(value = "type")String type, @RequestParam(value = "sid")String sid, HttpServletRequest req){
        String res = "";
        if(name == null || introduction == null || category == null || type == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Incomplete resources information\"}";
        }
        String bookID = UUID.randomUUID().toString();
        String path = saveFile(req, bookID, name);
        if(GCache.LoginAdmin.containsKey(sid)) {
            AdminBean ad = GCache.LoginAdmin.get(sid);
            if(ad == null || name == null || introduction == null || category == null || type == null) {
                res = "{\"status\":\"400\",\"errmsg\":\"Incomplete resources information\"}";
            } else {
                String userid = ad.getUserID();
                int suc = bookService.Upload(userid, bookID, name, introduction, category, type, path);
                if (suc > 0) {
                    res = "{\"status\":\"200\"}";
                } else {
                    res = "{\"status\":\"400\",\"errmsg\":\"Upload failed\"}";
                }
            }
        }
        return res;
    }

    @RequestMapping(value="/UUpload.action",method= RequestMethod.POST) //用户上传功能
    @ResponseBody
    private String UUploadAction(@RequestParam(value = "name") String name, @RequestParam(value = "introduction")String introduction, @RequestParam(value = "category")String category, @RequestParam(value = "type")String type, @RequestParam(value = "sid")String sid, HttpServletRequest req){
        String res = "";
        if(name == null || introduction == null || category == null || type == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Incomplete resources information\"}";
        }
        String bookID = UUID.randomUUID().toString();
        String path = saveFile(req, bookID, name);
        if(GCache.LoginUser.containsKey(sid)) {
            UserBean ad = GCache.LoginUser.get(sid);
            if(ad == null || name == null || introduction == null || category == null || type == null) {
                res = "{\"status\":\"400\",\"errmsg\":\"Incomplete resources information\"}";
            } else {
                String userid = ad.getUserID();
                int suc = bookService.UUpload(userid, bookID, name, introduction, category, type, path);
                if (suc > 0) {
                    res = "{\"status\":\"200\"}";
                } else {
                    res = "{\"status\":\"400\",\"errmsg\":\"Upload failed\"}";
                }
            }
        }
        return res;
    }
    public String saveFile(HttpServletRequest request, String bookId, String fileName){
        String savePath = "G:\\LiberaryBackend\\WEB-INF\\upload";
        String filePath = "";
        File file = new File(savePath);
        if(!file.exists()&&!file.isDirectory()){
            System.out.println("The directory or file does not exist！");
            file.mkdir();
        }
        String message = "";
        try {
                StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
                Iterator<String> iterator = req.getFileNames();
                while (iterator.hasNext()) {
                    MultipartFile mfile = req.getFile(iterator.next());
                    String fileNames = mfile.getOriginalFilename();
                    int split = fileNames.lastIndexOf(".");
                    fileNames.substring(0,split);
                    String ext = fileNames.substring(split+1,fileNames.length());
                    byte[] bs = mfile.getBytes();
                    String dir = savePath + File.separator + bookId ;
                    file = new File(dir);
                    if(!file.exists()&&!file.isDirectory()){
                        System.out.println("The directory or file does not exist！");
                        file.mkdir();
                    }
                    filePath =  dir + File.separator +fileName + "." + ext;

                    FileOutputStream fos = new FileOutputStream(filePath);
                    fos.write(bs, 0, bs.length);
                    fos.close();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @RequestMapping(value="/Show.action",method= RequestMethod.GET)  //管理员页面和资源页面的显示功能
    @ResponseBody
    private String ShowAction(HttpServletRequest req){
        String res = JSON.toJSONString(bookService.show());
        return res;
    }

    @RequestMapping(value="/PShow.action",method= RequestMethod.GET) //个人资源页面的显示功能
    @ResponseBody
    private String PShowAction(@RequestParam(value = "sid")String sid,HttpServletRequest req){
        GCache.LoginUser.containsKey(sid);
        UserBean ad = GCache.LoginUser.get(sid);
        String userid = ad.getUserID();
        List<ResourcesBean> u = bookService.pshow(userid);
        String res = JSON.toJSONString(bookService.pshow(userid));
        return res;
    }

    @RequestMapping(value="/LShow.action",method= RequestMethod.GET) //首页更新排行榜显示功能
    @ResponseBody
    private String LShowAction(HttpServletRequest req){
        String res = JSON.toJSONString(bookService.lshow());
        return res;
    }

    @RequestMapping(value="/DShow.action",method= RequestMethod.GET) //首页下载排行榜显示功能
    @ResponseBody
    private String DShowAction(HttpServletRequest req){
        String res = JSON.toJSONString(bookService.dshow());
        return res;
    }

    @RequestMapping(value="/CShow.action",method= RequestMethod.GET) //首页下载排行榜显示功能
    @ResponseBody
    private String CShowAction(@RequestParam(value = "sid")String sid,HttpServletRequest req){
        GCache.LoginUser.containsKey(sid);
        UserBean ad = GCache.LoginUser.get(sid);
        String userid = ad.getUserID();
        String res = JSON.toJSONString(bookService.cshow(userid));
        return res;
    }

    @RequestMapping(value="/Search.action",method= RequestMethod.GET) //管理员页面和资源页面的搜索功能
    @ResponseBody
    private String SearchAction(@RequestParam(value = "key") String key,HttpServletRequest req){
        String res = "";
        if(key == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Search criteria is empty\"}";
        }
        List<ResourcesBean> r = bookService.search(key);
        String rs = JSON.toJSONString(bookService.search(key));
        if (r != null){
            res = "{\"status\":\"200\",\"rs\":" + rs + "}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"Related resources not found\"}";
        }
        return res;
    }

    @RequestMapping(value="/USearch.action",method= RequestMethod.GET) //个人资源页面的搜索功能
    @ResponseBody
    private String USearchAction(@RequestParam(value = "key") String key,@RequestParam(value = "sid") String sid,HttpServletRequest req){
        String res = "";
        if(GCache.LoginUser.containsKey(sid)) {
            UserBean ad = GCache.LoginUser.get(sid);
            if(ad == null || key == null) {
                res = "{\"status\":\"400\",\"errmsg\":\"The user is not logged in or the search criteria are empty\"}";
            } else {
                String userid = ad.getUserID();
                List<ResourcesBean> r = bookService.usearch(key, userid);
                String rs = JSON.toJSONString(bookService.usearch(key, userid));
                if (r != null) {
                    res = "{\"status\":\"200\",\"rs\":" + rs + "}";
                } else {
                    res = "{\"status\":\"400\",\"errmsg\":\"Related resources not found\"}";
                }
            }
        }
        return res;
    }

    @RequestMapping(value="/Delete.action",method= RequestMethod.GET) //管理员页面的删除资源功能
    @ResponseBody
    private String DeleteAction(@RequestParam(value = "bookid") String bookid,HttpServletRequest req){
        String res = "";
        if(bookid == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Bookid is empty\"}";
        }
        int r = bookService.delete(bookid);
        String rs = JSON.toJSONString(bookService.delete(bookid));
        if (r > 0){
            res = "{\"status\":\"200\",\"rs\":\"" + rs + "\"}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"Related resources not found\"}";
        }
        return res;
    }

    @RequestMapping(value="/CDelete.action",method= RequestMethod.GET) //管理员页面和个人资源页面的删除资源功能
    @ResponseBody
    private String CDeleteAction(@RequestParam(value = "bookid") String bookid,HttpServletRequest req){
        String res = "";
        if(bookid == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"Bookid is empty\"}";
        }
        int r = bookService.cdelete(bookid);
        String rs = JSON.toJSONString(bookService.cdelete(bookid));
        if (r > 0){
            res = "{\"status\":\"200\",\"rs\":\"" + rs + "\"}";
        }else{
            res = "{\"status\":\"400\",\"errmsg\":\"Related resources not found\"}";
        }
        return res;
    }

    @RequestMapping(value="/Collect.action",method= RequestMethod.GET) //资源页面的收藏功能
    @ResponseBody
    private String CollectAction(@RequestParam(value = "bookid") String bookid,@RequestParam(value = "sid") String sid,@RequestParam(value = "name") String name,@RequestParam(value = "introduction") String introduction,@RequestParam(value = "category") String category,@RequestParam(value = "type") String type,HttpServletRequest req){
        String res = "";
        if(bookid == null || name == null || introduction == null || category == null || type == null) {
            res = "{\"status\":\"400\",\"errmsg\":\"User is not logged in\"}";
        }
        if(GCache.LoginUser.containsKey(sid)) {
            UserBean ad = GCache.LoginUser.get(sid);
            if(ad == null || bookid == null) {
                res = "{\"status\":\"400\",\"errmsg\":\"User is not logged in\"}";
            } else {
                String userid = ad.getUserID();
                int r = bookService.collect(bookid, userid, name, introduction, category, type);
                if (r > 0) {
                    res = "{\"status\":\"200\"}";
                } else {
                    res = "{\"status\":\"400\",\"errmsg\":\"Collection failed\"}";
                }
            }
        }
        return res;
    }


    @GetMapping("/Download.action")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "bookid") String bookid) throws Exception {
        ResourcesBean r = bookService.download(bookid);
        String path = r.getURL();
        String count = r.getCount();
        //怎么加count
        File file = new File(path);
        String filename = file.getName();
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(buffer.length);
        respHeaders.setContentType(new MediaType("application", "octet-stream"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        respHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
        return new ResponseEntity<byte[]>(buffer, respHeaders, HttpStatus.OK);
    }
}
