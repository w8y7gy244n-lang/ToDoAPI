package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity // Le dice a Spring que esta clase es una tabla en la base de datos
public class Tarea {

    @Id //marca cual es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Hace que el id se genere automaticamente sin asignarlo tu
    private Long id;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
