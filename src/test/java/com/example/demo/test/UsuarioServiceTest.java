package com.example.demo.test;

import com.example.demo.exception.UsuarioNotFoundException;
import com.example.demo.model.Tarea;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerUsuarios() {
        //Arrange
        Usuario usuario1 = new Usuario();
        usuario1.setPassword("T@marindo.1234");
        usuario1.setApellido("Medina");
        usuario1.setNombre("Leshua");
        usuario1.setEmail("alex1103m@gmail.com");

        Usuario usuario2 = new Usuario();
        usuario2.setPassword("Winter.2018");
        usuario2.setApellido("Carranza");
        usuario2.setNombre("Maui");
        usuario2.setEmail("eaglesdare96@hotmail.com");

        List<Usuario> mockUserList = List.of(usuario1,usuario2);


        when(usuarioRepository.findAll()).thenReturn(mockUserList);

        //Act
        List<Usuario> result = usuarioService.obtenerUsuarios();

        //Assert
        assertEquals(2,result.size());
        assertEquals("Maui", result.get(1).getNombre());

    }

    @Test
    void guardarUsuario() {
        //Arrange
        String nombre = "Kike";
        String apellido = "Cuellar";
        String email = "kike9304@gmail.com";
        String password = "T@marindo.1234";
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        //Act
        usuarioService.guardarUsuario(nombre,apellido,email,password);

        //Assert
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void borrarUsuario() {
        //Arrange
        Usuario Alex = new Usuario();
        Long usuario_id = 11L;
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.of(Alex));

        //Act
        usuarioService.borrarUsuario(usuario_id);

        //Assert
        verify(usuarioRepository).deleteById(usuario_id);

    }
    @Test
    void borrarUsuario_idNotFound(){
        //Arrange
        Long usuario_id = 11L;
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UsuarioNotFoundException.class, ()-> usuarioService.borrarUsuario(usuario_id));
    }

    @Test
    void actualizarUsuario() {
        //Arrange
        Usuario tito = new Usuario();
        Long usuario_id = 15L;
        String nombre = "Tito";
        String apellido = "Heredia";
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.of(tito));

        //Act
        usuarioService.actualizarUsuario(usuario_id,nombre,apellido);

        //Assert
        verify(usuarioRepository).save(tito);
    }
    @Test
    void actualizarUsuario_idNotFound(){
        //Arrange
        Long usuario_id = 11L;
        String nombre = "Test";
        String apellido = "McArthur";
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UsuarioNotFoundException.class, ()-> usuarioService.actualizarUsuario(usuario_id,nombre,apellido));

    }

    @Test
    void obtenTareasPorUsuario() {
        //Arrange
        Usuario user = new Usuario();
        Long usuario_id = 10L;
        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.of(user));

        //Act
        List<Tarea> result = usuarioService.obtenTareasPorUsuario(usuario_id);

        //Assert
        assertEquals(0, result.size());
    }
    @Test
    void obtenerTareasPorUsuario_idNotFound(){
        Long usuario_id = 10L;

        when(usuarioRepository.findById(usuario_id)).thenReturn(Optional.empty());

        //Assert
        assertThrows(UsuarioNotFoundException.class, ()->usuarioService.obtenTareasPorUsuario(usuario_id));
    }
}