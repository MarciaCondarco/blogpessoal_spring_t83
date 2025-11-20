package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_postagens") //create table tb_postagem
public class Postagem {
	
	@Id //PRIMARY KEY(id)
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(length = 100)
	@NotBlank(message = "o atributo titulo é obriagatorio") //not null
	@Size(min = 5, max = 100, message = "o atributo titulo deve conter no minimo 5 e no maximo 100 caracteres")
	private String titulo;
	
	@Column(length = 1000)
	@NotBlank(message = "o atributo texto é obriagatorio") //not null
	@Size(min = 3, max = 1000, message = "o atributo texto deve conter no minimo 10 e no maximo 1000 caracteres")
	private String texto;
	
	@UpdateTimestamp //atualiza a data e hora sempre que houver uma alteração na postagem
	private LocalDateTime data;
	
	
	@ManyToOne //muitas postagens associadas para um tema
	@JsonIgnoreProperties("postagem") //evita o loop infinito gerado po menyto one
	private Temas temas;
	
	@ManyToOne //muitas postagens associadas para um tema
	@JsonIgnoreProperties("postagem") //evita o loop infinito gerado po menyto one
	private Usuario usuario;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Temas getTemas() {
		return temas;
	}
	public void setTemas(Temas temas) {
		this.temas = temas;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
