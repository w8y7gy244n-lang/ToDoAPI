package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuario_id;

    @NotBlank(message = "Name cannot be empty")
    private String nombre;

    @NotBlank(message = "Last name cannot be empty")
    private String apellido;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareas = new ArrayList<>();

    @NotBlank(message = "Email/Username cannot be empty")
    private String email;

    private String password;


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

    public void setPassword(String password){this.password = password;}

    public  String getPassword(){return password;}

    public void setEmail(String username){this.email = email;}

    public  String getEmail(){return email;}

}
