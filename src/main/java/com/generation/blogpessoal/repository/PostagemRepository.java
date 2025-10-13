package com.generation.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.Postagem;

//extends = herda todas as funcionalidades do jpa repository para fazer o crud de postagem 
//<Postagem = qual classe ele vai gerenciar, Long = qual o tipo do id da classe postagem>
public interface PostagemRepository extends JpaRepository<Postagem, Long>{

}
