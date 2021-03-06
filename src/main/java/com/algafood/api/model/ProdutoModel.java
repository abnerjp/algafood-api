package com.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Bolo de fubá")
	private String nome;

	@Schema(example = "Assado no dia")
	private String descricao;

	@Schema(example = "23.9")
	private BigDecimal preco;

	@Schema(example = "true")
	private Boolean ativo;
	
}
