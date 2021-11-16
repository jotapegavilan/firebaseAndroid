package com.gavilan.firebaseapp.model;

public class Nota {

    private String id;
    private String titulo;
    private String descripcion;

    public Nota() {
    }

    public Nota(String titulo, String descripcion,String id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
