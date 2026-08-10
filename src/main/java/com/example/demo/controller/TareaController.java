package com.example.demo.controller;

import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> obtenerTareas(){
       return tareaService.obtenerTareas();
    }

    @PostMapping
    public void agregarTarea(@Valid @RequestBody Tarea tarea){
        tareaService.guardarTarea(tarea.getDescripcion());
    }

    @DeleteMapping("/{id}")
    public void borrarTarea(@PathVariable Long id){
        tareaService.borrarTarea(id);

    }

    @PostMapping("/lista")
    public void agregarTareasGuardar (@Valid @RequestBody List<Tarea> tarea){
        List<String> miLista = new ArrayList<>();
        for(Tarea tareas : tarea){
           miLista.add(tareas.getDescripcion());
        }
        tareaService.listaTareasGuardar(miLista);
    }

    @PutMapping("/{id}")
    public void updateDescripcion (@PathVariable ("id") Long id, @Valid @RequestBody Tarea tarea){
        tareaService.actualizarDescripcion(id, tarea.getDescripcion());
    }
}
