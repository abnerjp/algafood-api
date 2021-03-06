package com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.AlgaLinks;
import com.algafood.api.controller.RestauranteController;
import com.algafood.api.model.RestauranteModel;
import com.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteModel);
		
		if (restaurante.ativacaoPermitida()) {
			restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
		}

		if (restaurante.inativacaoPermitida()) {
			restauranteModel.add(algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}
		
		if (restaurante.aberturaPermitida()) {
			restauranteModel.add(algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}
		
		if (restaurante.fechamentoPermitido()) {
			restauranteModel.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}

		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		if (restauranteModel.getEndereco() != null
				&& restauranteModel.getEndereco().getCidade() != null) {
			restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(
					restaurante.getEndereco().getCidade().getId()));
		}
		
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteModel.add(algaLinks.linkToFormasPagamentoRestaurante(restaurante.getId(),"formas-pagamento"));
		
		restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
		
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}
	
}
