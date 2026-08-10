package com.example.demo.controller;

import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.service.TareaService;
import com.example.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Usuario> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @PostMapping
    public void guardarUsuario(@Valid @RequestBody Usuario usuario) {
        usuarioService.guardarUsuario(usuario.getNombre() , usuario.getApellido());
    }

    @PutMapping("/{usuario_id}")
    public void actualizarUsuario(@PathVariable ("usuario_id")Long usuario_id, @Valid @RequestBody Usuario usuario){
        usuarioService.actualizarUsuario(usuario_id, usuario.getNombre() , usuario.getApellido());
    }

    @DeleteMapping("/{usuario_id}")
    public void borrarUsuario(@PathVariable ("usuario_id") Long usuario_id){
        usuarioService.borrarUsuario(usuario_id);
    }


    @PostMapping("/{usuario_id}/tareas")
    public void tareaAUsuario (@PathVariable ("usuario_id") Long usuario_id, @Valid @RequestBody Tarea tarea){
        tareaService.agregarTareaAUsuario(usuario_id, tarea.getDescripcion());
    }

    @GetMapping("/{usuario_id}/tareas")
    public List<Tarea> obtenTareasPorUsuario (@PathVariable ("usuario_id") Long usuario_id){
        return usuarioService.obtenTareasPorUsuario(usuario_id);
    }
}
