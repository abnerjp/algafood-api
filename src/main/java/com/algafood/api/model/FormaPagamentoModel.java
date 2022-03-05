package com.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {

	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "Dinheiro")
	private String descricao;
	
}
