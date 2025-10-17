package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_temas")
public class Temas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100)
	@NotBlank(message = "O atributo tema é obrigatorio")
	@Size(min = 10 , max = 100, message = "atributo com tamanho de minimo 10 a maximo 100")
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "temas", cascade = CascadeType.REMOVE) 
	//FETCH - > CARREGAMENTO DE DADOS preguiçoso , melhoar a performace
	//mappedby -> indica a chave estrangeira
	//
	@JsonIgnoreProperties(value = "temas", allowSetters = true)//allowsetters permite que metodos set sejam considerados 
	//durante o momento que o json for gerados e os metodos get serão 
	//ignorados para evitar o loop infinito
	private List<Postagem> postagem;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}	
	
}
