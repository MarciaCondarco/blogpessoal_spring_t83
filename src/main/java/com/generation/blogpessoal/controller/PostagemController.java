package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController //informa que essa classe é um controller
@RequestMapping("/postagens") 	//localhost:8080/postagens
@CrossOrigin(origins = "*", allowedHeaders = "*") //permite que qualquer aplicação acesse a api
public class PostagemController {
	
	@Autowired 	 //spring faz a injeção de dependencia automaticamente 
	private PostagemRepository postagemRepository;
	
	@GetMapping //mapeia a requisição do tipo get
	public ResponseEntity<List<Postagem>> getAll(){ //metodo para interface lIST para listar todas as postagens
		return ResponseEntity.ok(postagemRepository.findAll()); //retorna o status 200 e a lista de postagens
		//findall -> select * from tb_postagens;
	}
}
