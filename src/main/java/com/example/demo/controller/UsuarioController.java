package com.example.demo.controller;

import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.service.TareaService;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Endpoints for User management")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TareaService tareaService;

    @Operation(summary = "Get the full list of users", description = "Returns the complete list of all users")
    @GetMapping
    public List<Usuario> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @Operation(summary = "Saves the user", description = "Saves all the information of a user like: Name, Lastname, email and Password")
    @PostMapping
    public void guardarUsuario(@Valid @RequestBody Usuario usuario) {
        usuarioService.guardarUsuario(usuario.getNombre() , usuario.getApellido(), usuario.getEmail(), usuario.getPassword());
    }

    @Operation(summary = "Update a user", description = "Lets you update the information of a user via their corresponding ID")
    @PutMapping("/{usuario_id}")
    public void actualizarUsuario(@PathVariable ("usuario_id")Long usuario_id, @Valid @RequestBody Usuario usuario){
        usuarioService.actualizarUsuario(usuario_id, usuario.getNombre() , usuario.getApellido());
    }

    @Operation(summary = "Deletes a user via ID", description = "This deletes a user from the DB via ID")
    @DeleteMapping("/{usuario_id}")
    public void borrarUsuario(@PathVariable ("usuario_id") Long usuario_id){
        usuarioService.borrarUsuario(usuario_id);
    }


    @Operation(summary = "Add a task to a user via iD", description = "Lets you add a task to a specific user via their ID")
    @PostMapping("/{usuario_id}/tareas")
    public void tareaAUsuario (@PathVariable ("usuario_id") Long usuario_id, @Valid @RequestBody Tarea tarea){
        tareaService.agregarTareaAUsuario(usuario_id, tarea.getDescripcion());
    }

    @Operation(summary = "Get the task of a user", description = "Returns the tasks of a specific user via ID")
    @GetMapping("/{usuario_id}/tareas")
    public List<Tarea> obtenTareasPorUsuario (@PathVariable ("usuario_id") Long usuario_id){
        return usuarioService.obtenTareasPorUsuario(usuario_id);
    }
}
