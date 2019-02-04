package com.example.paco.convertidordivisas;

public class Elemento {



    private String moneda, cantidad;
    public Elemento() {
    }
    public Elemento(String moneda, String cantidad) {
        this.moneda = moneda;
        this.cantidad = cantidad;
    }
    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCantidad() {
        return cantidad;
    }


    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }






}

