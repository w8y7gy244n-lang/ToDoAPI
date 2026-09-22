package com.example.demo.service;

import com.example.demo.exception.TareaNotFoundException;
import com.example.demo.exception.UsuarioNotFoundException;
import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.repository.TareaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Tarea> obtenerTareas(){
        return tareaRepository.findAll();
    }

    public void  guardarTarea(String descripcion){
        Tarea tarea = new Tarea(); //Creas el objeto
        tarea.setDescripcion(descripcion); //Asignas la descripcion
        tareaRepository.save(tarea); //Guardas el objeto

    }

    public void borrarTarea(Long id){
        Optional<Tarea> optional = tareaRepository.findById(id);
        if(optional.isPresent()){
            tareaRepository.deleteById(id);
        }else{
            throw new TareaNotFoundException("Numero de tarea no existe");
        }

    }

    public void listaTareasGuardar(List<String> descripciones){
        for(String descripcion : descripciones ){
            guardarTarea(descripcion);
        }
    }

    public void actualizarDescripcion(Long id, String descripcion){
        Optional<Tarea> optional = tareaRepository.findById(id);
        if(optional.isPresent()){
            Tarea tarea = optional.get();
            tarea.setDescripcion(descripcion);
            tareaRepository.save(tarea);
        }else{
            throw new TareaNotFoundException("Numero de tarea no existe");
        }
    }

    public void agregarTareaAUsuario(Long usuario_id, String descripcion){
        Optional<Usuario> uoptional = usuarioRepository.findById(usuario_id);
        if(uoptional.isPresent()){
            Usuario usuario = uoptional.get();
            Tarea tarea = new Tarea();
            tarea.setUsuario(usuario);
            tarea.setDescripcion(descripcion);
            tareaRepository.save(tarea);
        } else {
            throw new UsuarioNotFoundException("Numero de tarea no existe");
        }
    }

}
