package com.mybatishelper.core.exception;

public class NoSupportedMethodException extends RuntimeException{

    public NoSupportedMethodException(){
        this("no supported this method,do you what expect something else?");
    }
    public NoSupportedMethodException(String message){
        super(message);
    }
}
