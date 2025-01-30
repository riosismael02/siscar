package com.app.web.controller;

import com.app.web.entity.Persona;
import com.app.web.repository.PersonaRepository;
import com.app.web.service.PersonaService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Controller
@RequestMapping("/personas")
public class PersonaController {

	@Autowired
	private PersonaService personaService;

	

	@GetMapping("/formulario")
	public String mostrarFormulario(Model model) {
		model.addAttribute("persona", new Persona());
		return "persona-formulario";
	}

	@PostMapping("/guardar")
	public String guardarPersona(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
			@RequestParam("foto") MultipartFile foto, Model model) {

		try {
			byte[] fotoBytes = foto.isEmpty() ? null : foto.getBytes();
			if (foto.isEmpty()) {
				System.out.println("No se cargó ninguna foto");
			} else {
				System.out.println("Tamaño de la foto: " + foto.getSize());
			}
			personaService.guardarPersona(nombre, apellido, fotoBytes);
			model.addAttribute("mensaje", "Persona guardada exitosamente");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar la persona: " + e.getMessage());
		}

		return "redirect:/personas/formulario";
	}

	@GetMapping("/foto/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> mostrarFoto(@PathVariable Long id) {
		Persona persona = personaService.obtenerPersonaPorId(id);
		byte[] imagen = persona.getFoto();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
	}

	@GetMapping("/listar")
	public String listarPersonas(Model model) {
		List<Persona> personas = personaService.listarPersonas();
		model.addAttribute("personas", personas);

		return "persona-lista";
	}
	
	@GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Persona persona = personaService.obtenerPersonaPorId(id);
        model.addAttribute("persona", persona);
        return "editar-persona";
    }

    @PostMapping("/actualizar")
    public String actualizarPersona(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("foto") MultipartFile foto,
            Model model) {

        try {
            byte[] fotoBytes = foto.isEmpty() ? null : foto.getBytes();
            personaService.actualizarPersona(id, nombre, apellido, fotoBytes);
            model.addAttribute("mensaje", "Persona actualizada exitosamente");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar la persona: " + e.getMessage());
        }

        return "redirect:/personas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable Long id, Model model) {
        try {
            personaService.eliminarPersona(id);
            model.addAttribute("mensaje", "Persona eliminada exitosamente");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar la persona: " + e.getMessage());
        }

        return "redirect:/personas/listar";
    }



}