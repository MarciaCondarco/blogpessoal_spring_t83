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

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemasRepository;

import jakarta.validation.Valid;

@RestController //informa que essa classe é um controller
@RequestMapping("/postagens") 	//localhost:8080/postagens
@CrossOrigin(origins = "*", allowedHeaders = "*") //permite que qualquer aplicação acesse a api
public class PostagemController {
	
	@Autowired 	 //spring faz a injeção de dependencia automaticamente 
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemasRepository temasRepository;
	
	@GetMapping //mapeia a requisição do tipo get
	public ResponseEntity<List<Postagem>> getAll(){ //metodo para interface lIST para listar todas as postagens
		return ResponseEntity.ok(postagemRepository.findAll()); //retorna o status 200 e a lista de postagens
		//findall -> select * from tb_postagens;
	}
	
	@GetMapping("/{id}") //mapeia a requisição do tipo get -> variavel de caminho -> variavel que esta no endereço
	//procura o id na url e passa como parametro para o metodo
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		// response entity -> retorna uma resposta http
		//<Postagem> -> retorna um objeto do tipo postagem
		// @PathVariable -> informa que o parametro id vem da url
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta)) //se for diferente de nulo então encontrar o id, retorna o status 200 e a postagem
				.orElse(ResponseEntity.notFound().build()); //se for nulo, retorna o status 404, metodo build constrói a resposta http
				
		//findById -> select * from tb_postagens where id = ?
	}
	
	@GetMapping("/titulo/{titulo}") //mapeia a requisição do tipo get
	public ResponseEntity<List<Postagem>> getAllByTitulo(@PathVariable String titulo){ //metodo para interface lIST para listar todas as postagens
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo)); //retorna o status 200 e a lista de postagens
		//findAllByTituloContainingIgnoreCase -> select * from tb_postagens where titulo like %?%;
	}
	
	@PostMapping //mapeia a requisição do tipo post
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		
		if(temasRepository.existsById(postagem.getTemas().getId())) {
			// @RequestBody -> informa que o parametro postagem vem do corpo da requisição
			// @Valid -> valida o objeto postagem conforme as anotações da classe postagem
			postagem.setId(null); //não dar erro no banco de dados
			
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
			//returna 201 e o objeto que foi salvo no banco de dados e para salvar usa o metodo save
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe", null);
	}
				
	@PutMapping //mapeia a requisição do tipo put	
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		
		    if(postagemRepository.existsById(postagem.getId())){
	    		if(temasRepository.existsById(postagem.getTemas().getId())) {
	    			// @RequestBody -> informa que o parametro postagem vem do corpo da requisição
	    			// @Valid -> valida o objeto postagem conforme as anotações da classe postagem
	    			postagem.setId(null); //não dar erro no banco de dados
	    			
	    			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	    			//returna 201 e o objeto que foi salvo no banco de dados e para salvar usa o metodo save
	    		}
	    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe", null);
		    }
		    return ResponseEntity.notFound().build(); //se o id não existir, retorna 404
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT) //parametros de metodo se a exclusão der certo
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);//verifica se o id existe no banco de dados
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); //se o id não existir, retorna 404
		
		postagemRepository.deleteById(id); //se o id existir, deleta a postagem
	}

}
