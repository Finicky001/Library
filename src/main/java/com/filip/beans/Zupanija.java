package com.filip.beans;


public class Zupanija {
    private int id;
    private String nazivzupanije;
    private String pozivni;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivzupanije() {
        return nazivzupanije;
    }

    public void setNazivzupanije(String nazivzupanije) {
        this.nazivzupanije = nazivzupanije;
    }

    public String getPozivni() {
        return pozivni;
    }

    public void setPozivni(String pozivni) {
        this.pozivni = pozivni;
    }
    
    @Override
    public String toString(){
        return nazivzupanije;
    }
}
