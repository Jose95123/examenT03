package com.example.demo.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Genero;
import com.example.demo.model.Libro;
import com.example.demo.service.GeneroService;
import com.example.demo.service.LibroService;

@Controller
@RequestMapping("/libros")
public class LibroController {

	@Autowired
	private LibroService libroService;
	@Autowired
	private GeneroService generoService;

	@GetMapping("/getAllLibros")
	public String getAllLibros(Model model) {
		List<Libro> listLibros = libroService.getAllLibros();
		model.addAttribute("libros", listLibros);
		return "librosList";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("libro", new Libro());
		model.addAttribute("generos", generoService.getAllGeneros());
		return "libroRegister";
	}

	@PostMapping("/register")
	public String createLibro(@RequestParam("nombre") String nombre, @RequestParam("autor") String autor,
			@RequestParam("fechaPublicacion") String fecha, @RequestParam("genero") String genero, Model model) {
		Libro libro = new Libro();
		libro.setNombre(nombre);
		libro.setAutor(autor);
		System.out.println("Fecha de publicación: " + fecha);

		try {
			String fechaString = fecha;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        LocalDateTime localDateTime = LocalDateTime.parse(fechaString, formatter);

	        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		} catch (DateTimeParseException e) {
			libro.setFechaPublicacion(new Date());
		}

		Genero generoObj = generoService.getGeneroById(Long.parseLong(genero));
		libro.setGenero(generoObj);
		libroService.createLibro(libro);

		return "redirect:/libros/getAllLibros";
	}

	@GetMapping("/delete/{idLibro}")
	public String deleteLibro(@PathVariable Long idLibro, Model model) {
		libroService.deleteLibro(idLibro);

		List<Libro> listLibros = libroService.getAllLibros();
		model.addAttribute("libros", listLibros);

		return "librosList";
	}

	@GetMapping("/edit/{idLibro}")
	public String showEditForm(@PathVariable Long idLibro, Model model) {
		Libro libro = libroService.getLibroById(idLibro);
		List<Genero> generos = generoService.getAllGeneros();

		model.addAttribute("libro", libro);
		model.addAttribute("generos", generos);
		model.addAttribute("selectedGenero", libro.getGenero());
		model.addAttribute("idLibro", libro.getIdlibro());

		return "libroEdit";
	}

	@PostMapping("/edit/{idLibro}")
	public String editLibro(@PathVariable Long idLibro, @ModelAttribute Libro libro, Model model) {
		Libro existingLibro = libroService.getLibroById(idLibro);

		existingLibro.setNombre(libro.getNombre());
		existingLibro.setAutor(libro.getAutor());
		existingLibro.setFechaPublicacion(libro.getFechaPublicacion());
		existingLibro.setGenero(libro.getGenero());

		libroService.createLibro(existingLibro);

		return "redirect:/libros/getAllLibros";
	}

}
