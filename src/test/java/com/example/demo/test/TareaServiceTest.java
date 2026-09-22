package com.example.demo.test;

import com.example.demo.exception.TareaNotFoundException;
import com.example.demo.exception.UsuarioNotFoundException;
import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.repository.TareaRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.TareaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TareaService tareaService;

    @Test
    void obtenerTareas() {

        //Arrange
        Tarea tarea1 = new Tarea();
        tarea1.setDescripcion("Buy milk");

        Tarea tarea2 = new Tarea();
        tarea2.setDescripcion("Go workout");

        List<Tarea> tareasMock = List.of(tarea1, tarea2);
        when(tareaRepository.findAll()).thenReturn(tareasMock);

        //Act
        List<Tarea> resultado = tareaService.obtenerTareas();

        //Assert
        assertEquals(2,resultado.size());
        assertEquals("Buy milk", resultado.get(0).getDescripcion());
    }

    @Test
    void guardarTarea() {
        //Arrange
        String descripcion = "Description of the metod guardarTarea";

        //Act
        tareaService.guardarTarea(descripcion);

        //Assert
        verify(tareaRepository).save(any(Tarea.class));

    }

    @Test
    void borrarTarea() {
        //Arrange
        Tarea tarea3 = new Tarea();
        Long id = 34L;
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea3));

        //Act
        tareaService.borrarTarea(id);

        //Assert
        verify(tareaRepository).deleteById(id);

    }

    @Test
    void borrarTarea_idDoesNotExist_throwExc(){
        //Arrange
        Long id = 33L;
        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        //Assert
        assertThrows(TareaNotFoundException.class, () -> tareaService.borrarTarea(id));

    }

    @Test
    void listaTareasGuardar() {
    }

    @Test
    void actualizarDescripcion() {
        //Arrange
        Tarea tarea = new Tarea();
        Long id = 25L;
        String descripcion = "Description of Update Description method";
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));

        //Act
        tareaService.actualizarDescripcion(id, descripcion);

        //Assert
        verify(tareaRepository).save(tarea);

    }

    @Test
    void actualizarDescripcion_idDoesNotExist(){
        //Arrange
        String descripcion = "Description of Update Description method";
        Long id = 24L;
        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        assertThrows(TareaNotFoundException.class, ()->tareaService.actualizarDescripcion(id,descripcion));


    }

    @Test
    void agregarTareaAUsuario() {
        //Arrange
        Usuario usuario = new Usuario();
        Long usuario_id = 10L;
        String descripcion = "Description of Add Task to User";
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.of(usuario));

        //Act
        tareaService.agregarTareaAUsuario(usuario_id,descripcion);

        //Assert
        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    void agregarTareaAUsuario_notFound(){
        //Arrange
        Long usuario_id = 10L;
        String descripcion = "Description of Add task to User (not found)";
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.empty());

        //Act-Assert
        assertThrows(UsuarioNotFoundException.class, ()-> tareaService.agregarTareaAUsuario(usuario_id,descripcion));
    }
}