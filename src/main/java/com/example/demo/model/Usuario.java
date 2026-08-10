package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuario_id;

    @NotBlank(message = "Nombre de usuario no puede estar vacio")
    private String nombre;

    @NotBlank(message = "Apellido no puede estar vacio")
    private String apellido;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareas = new ArrayList<>();


    public long getUsuario_id() {
        return usuario_id;
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getApellido(){
        return apellido;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}
