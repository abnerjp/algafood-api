package com.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {

	@Schema(example = "Gerente", required = true)
	@NotBlank
	private String nome;
	
}
