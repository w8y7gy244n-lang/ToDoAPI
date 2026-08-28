package com.example.demo.service;

import com.example.demo.exception.UsuarioNotFoundException;
import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.repository.TareaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public void guardarUsuario(String nombre, String apellido, String email, String password){
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);
    }

    public void borrarUsuario(Long usuario_id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario_id);
        if(optionalUsuario.isPresent()){
            usuarioRepository.deleteById(usuario_id);
        }else{
            throw new UsuarioNotFoundException("El Usuario no existe");
        }
    }

    public void actualizarUsuario(Long usuario_id, String nombre, String apellido){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario_id);
        if(optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuarioRepository.save(usuario);
        }else{
            throw new UsuarioNotFoundException("El Usuario no existe");
        }
    }

    public List<Tarea> obtenTareasPorUsuario(Long usuario_id){
        Optional <Usuario> optUsuario = usuarioRepository.findById(usuario_id);
        if(optUsuario.isPresent()){
            Usuario usuario = optUsuario.get();
            return usuario.getTareas();
        }else{
            throw new UsuarioNotFoundException("El usuario no existe");
        }

    }

}
