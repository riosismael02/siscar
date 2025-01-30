package com.app.web.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.app.web.entity.Persona;

public interface PersonaService {

	    Persona guardarPersona(String nombre, String apellido, byte[] foto);

	    Persona obtenerPersonaPorId(Long id);
	    
	    void actualizarPersona(Long id, String nombre, String apellido, byte[] foto);

	    List<Persona> listarPersonas();

	    void eliminarPersona(Long id);
	}
