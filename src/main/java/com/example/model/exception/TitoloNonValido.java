package com.example.model;

public class TitoloNonValido extends IllegalArgumentException{
    public TitoloNonValido(String msg){
        super(msg);

    }
}
