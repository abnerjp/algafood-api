package com.algafood.core.springfox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.model.CozinhaModel;
import com.algafood.api.model.PedidoResumoModel;
import com.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algafood.api.openapi.model.PageableModelOpenApi;
import com.algafood.api.openapi.model.PedidosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
@EnableOpenApi
//@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("index.html")
		.addResourceLocations("classpath:/META-INF/resources/");
	}

	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	@Bean
	public Docket apiDocket() {
		// Acessar documentacao API
		// http://localhost:8080/swagger-ui/index.html#/
		
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algafood.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				.apiInfo(apiInfo())
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, PedidoResumoModel.class),
						PedidosModelOpenApi.class))
				.apiInfo(apiInfo())
				.tags(tags()[0], tags());
	}
	
	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
			          .description("Erro interno do Servidor")
			          .representation(MediaType.APPLICATION_JSON)
			          .apply(getProblemaModelReference())
			          .build(),
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
			          .description("Recurso n??o possui representa????o que pode ser aceita pelo consumidor")
			          .build()
			  );
	}
	
	private List<Response> globalPostPutResponseMessages() {
		  return Arrays.asList(
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
			          .description("Requisi????o inv??lida (erro do cliente)")
			          .representation(MediaType.APPLICATION_JSON)
			          .apply(getProblemaModelReference())
			          .build(),
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
			          .description("Erro interno do Servidor")
			          .representation(MediaType.APPLICATION_JSON)
			          .apply(getProblemaModelReference())
			          .build(),
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
			          .description("Recurso n??o possui representa????o que pode ser aceita pelo consumidor")
			          .build(),
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
			          .description("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
			          .representation(MediaType.APPLICATION_JSON)
			          .apply(getProblemaModelReference())
			          .build()
			  );
	}
	
	private List<Response> globalDeleteResponseMessages() {
		  return Arrays.asList(
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
			          .description("Requisi????o inv??lida (erro do cliente)")
			          .representation(MediaType.APPLICATION_JSON)
			          .apply(getProblemaModelReference())
			          .build(),
			      new ResponseBuilder()
			          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
			          .description("Erro interno do Servidor")
			          .representation(MediaType.APPLICATION_JSON)
			          .apply(getProblemaModelReference())
			          .build()
			  );
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API :: Abner J Pelisser")
				.description("Api desenvolvida no curso Especialista Spring Rest - AlgaWorks")
				.version("1")
				.contact(new Contact("Abner J Pelisser", "https://github.com/abnerjp", "abner.pelisser@gmail.com"))
				.build();
	}
	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
			return r -> r.model(m -> m.name("Problema")
					.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
							q -> q.name("Problema").namespace("com.algafood.api.exceptionhandler")))));
	}
	
	private Tag[] tags() {
		return new Tag[] {
			new Tag("Cidades", "Gerencia as cidades"),
			new Tag("Grupos", "Gerencia os grupos de usu??rios"),
			new Tag("Cozinhas", "Gerencia as cozinhas"),
			new Tag("Formas Pagamento", "Gerencia as formas de pagamento"),
			new Tag("Pedidos", "Gerencia os pedidos"),
			new Tag("Restaurantes", "Gerencia os restaurantes"),
			new Tag("Estados", "Gerencia os estados"),
			new Tag("Produtos", "Gerencia os produtos de restaurantes"),
			new Tag("Usu??rios", "Gerencia os usu??rios"),
			new Tag("Estat??sticas", "Estat??sticas da AlgaFood")
		};
	}
}
