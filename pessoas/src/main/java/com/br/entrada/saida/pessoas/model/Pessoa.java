package com.br.entrada.saida.pessoas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index; // <--- O IMPORT CORRETO DO JPA

@Entity
@Table(name = "pessoas", indexes = {
		@Index(name = "idx_pessoa_cpf", columnList = "cpf"),
		@Index(name = "idx_pessoa_nome", columnList = "nome"),
		@Index(name = "idx_pessoa_unidade", columnList = "unidade")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	private String nome;
	private String telefone;

	@Enumerated(EnumType.STRING)
	private Unidade unidade;

	private String setor;
	private String andar;
	private String paciente;
	private String observacao;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("horaEntrada DESC")
	private List<Registro> registros = new ArrayList<>();

	@Column(length = 11)
	@Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
	private String cpf;

	@Column(length = 20)
	private String identidade;

	// Indica qual usuário do sistema cadastrou ou editou esta pessoa
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuarioResponsavel;

	@Transient
	public boolean isNovo() {
		return id == null;
	}
}