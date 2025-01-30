package com.app.web.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.app.web.entity.Persona;
import com.app.web.repository.PersonaRepository;
import com.app.web.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Persona guardarPersona(String nombre, String apellido, byte[] foto) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setFoto(foto);
        return personaRepository.save(persona);
    }

    @Override
    public Persona obtenerPersonaPorId(Long id) {
        Optional<Persona> optionalPersona = personaRepository.findById(id);
        return optionalPersona.orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));
    }

    @Override
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public void eliminarPersona(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Persona no encontrada con id: " + id);
        }
    }

    public void actualizarPersona(Long id, String nombre, String apellido, byte[] foto) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        persona.setNombre(nombre);
        persona.setApellido(apellido);

        if (foto != null) {
            persona.setFoto(foto);
        }

        personaRepository.save(persona);
    }
}