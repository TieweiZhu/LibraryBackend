package com.example.librarybackend.bean;

public enum BookType {
    Education(0),
    Learning(1),
    Comprehensive(2);

    private int value = 0;

    private BookType(int value) {     //必须是private的，否则编译错误
        this.value = value;
    }
}
