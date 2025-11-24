package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	
	@Autowired
	private TemaRepository temasRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(temasRepository.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id){
		return temasRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build()); 
	}
	
	@GetMapping("/descricao/{descricao}") 
	public ResponseEntity<List<Tema>> getAllByDescricao(@PathVariable String descricao){ 
		return ResponseEntity.ok(temasRepository.findAllByDescricaoContainingIgnoreCase(descricao)); 
	}
	
	@PostMapping
	public ResponseEntity<Tema> post (@Valid @RequestBody Tema temas){
		temas.setId(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(temasRepository.save(temas));
	}
	@PutMapping 
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema temas){
			return temasRepository.findById(temas.getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK)
							.body(temasRepository.save(temas))) 
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Tema> temas = temasRepository.findById(id);
		
		if(temas.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
		
		temasRepository.deleteById(id); 
	}
}
