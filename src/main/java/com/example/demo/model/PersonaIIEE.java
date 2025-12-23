package com.example.demo.model;

public class PersonaIIEE {
        private Integer codigo;
        private String descripcion;



    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public PersonaIIEE() {
    }
    public PersonaIIEE(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }


}
