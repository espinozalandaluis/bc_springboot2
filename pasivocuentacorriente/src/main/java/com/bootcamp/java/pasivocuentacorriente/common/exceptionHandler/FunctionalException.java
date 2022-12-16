package com.bootcamp.java.pasivocuentacorriente.common.exceptionHandler;

public class FunctionalException extends RuntimeException{
    public FunctionalException(String messageError){
        super(messageError);
    }
}
