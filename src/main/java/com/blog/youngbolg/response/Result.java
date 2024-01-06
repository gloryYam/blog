package com.blog.youngbolg.response;

public class Result<T>{

    private T data;


    public Result(T data) {
        this.data = data;
    }
}
