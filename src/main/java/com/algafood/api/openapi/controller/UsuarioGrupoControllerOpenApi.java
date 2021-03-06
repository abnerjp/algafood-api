package com.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.MediaType;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@Operation(summary = "Lista as permissões associadas ao usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do usuário, inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	})
	public List<GrupoModel> listar(
			@Parameter(description = "ID do usuário", example = "1")
			Long usuarioId);
	
	@Operation(summary = "Desassociação de usuário com grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "desassociação realizada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	})
	public void desassociar(
			@Parameter(description = "ID do usuário", example = "1")
			Long usuarioId, 
			
			@Parameter(description = "ID do grupo", example = "1")
			Long grupoId);
	
	@Operation(summary = "Associação de usuário com grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	})
	public void associar(
			@Parameter(description = "ID do usuário", example = "1")
			Long usuarioId, 
			
			@Parameter(description = "ID do grupo", example = "1")
			Long grupoId);
	
}
