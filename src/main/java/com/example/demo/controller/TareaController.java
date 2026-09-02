package com.example.demo.controller;

import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.service.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tareas")
@Tag(name = "Tareas", description = "Endpoints for task management")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Operation(summary = "Get all tasks", description = "Returns the complete list of tasks")
    @GetMapping
    public List<Tarea> obtenerTareas(){
       return tareaService.obtenerTareas();
    }

    @Operation(summary = "Add a task", description = "Saves a new task to the list of tasks")
    @PostMapping
    public void agregarTarea(@Valid @RequestBody Tarea tarea){
        tareaService.guardarTarea(tarea.getDescripcion());
    }

    @Operation(summary = "Delete a task", description = "Deletes a task from the list via ID")
    @DeleteMapping("/{id}")
    public void borrarTarea(@PathVariable Long id){
        tareaService.borrarTarea(id);

    }

    @Operation(summary = "Add multiple tasks", description = "Adds a list of tasks in a single request")
    @PostMapping("/lista")
    public void agregarTareasGuardar (@Valid @RequestBody List<Tarea> tarea){
        List<String> miLista = new ArrayList<>();
        for(Tarea tareas : tarea){
           miLista.add(tareas.getDescripcion());
        }
        tareaService.listaTareasGuardar(miLista);
    }

    @Operation(summary = "Update a task", description = "Updates the task via ID")
    @PutMapping("/{id}")
    public void updateDescripcion (@PathVariable ("id") Long id, @Valid @RequestBody Tarea tarea){
        tareaService.actualizarDescripcion(id, tarea.getDescripcion());
    }
}
