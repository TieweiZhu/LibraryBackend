package com.example.librarybackend.bean;
import java.math.BigInteger;

public class ResourcesBean {
    private String UserID;
    private String BookID;
    private String Name;
    private String Introduction;
    private String Category;
    private String Type;
    private BigInteger Time;
    private String Count;
    private String URL;

    public String getUserID() {
        return UserID;
    }
    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getBookID() {
        return BookID;
    }
    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getIntroduction() {
        return Introduction;
    }
    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getCategory() {
        return Category;
    }
    public void setCategory(String category) {
        Category = category;
    }

    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }

    public String getCount() {
        return Count;
    }
    public void setCount(String count) {
        Count = count;
    }

    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }

    public BigInteger getTime() {
        return Time;
    }
    public void setTime(BigInteger time) {
        Time = time;
    }
}
