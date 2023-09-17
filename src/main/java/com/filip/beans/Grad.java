package com.filip.beans;

public class Grad {
    private int id;
    private String nazivgrada;
    private String postbroj;
    private Zupanija zupanija;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivgrada() {
        return nazivgrada;
    }

    public void setNazivgrada(String nazivgrada) {
        this.nazivgrada = nazivgrada;
    }

    public String getPostbroj() {
        return postbroj;
    }

    public void setPostbroj(String postbroj) {
        this.postbroj = postbroj;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }
    
    @Override
    public String toString(){
        return nazivgrada;
    }
}
