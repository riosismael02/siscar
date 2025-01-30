package com.app.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	// Aquí puedes agregar consultas personalizadas si las necesitas.
}