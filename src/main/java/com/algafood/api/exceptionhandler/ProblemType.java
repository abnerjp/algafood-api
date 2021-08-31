package com.algafood.api.exceptionhandler;

public enum ProblemType {

	ENTIDADE_NAO_ENCONTRADA(
			"/entidade-nao-encontrada", 
			"Entidade não encontrada");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getUri() {
		return uri;
	}
	
}
