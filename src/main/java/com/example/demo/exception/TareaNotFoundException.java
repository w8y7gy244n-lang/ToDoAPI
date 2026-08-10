package com.example.demo.exception;

public class TareaNotFoundException extends RuntimeException{
    public TareaNotFoundException(String mensaje){
        super(mensaje);
    }
}
