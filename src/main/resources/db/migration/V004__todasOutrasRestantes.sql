create table forma_pagamento (
	id bigint not null auto_increment, 
	descricao varchar(60), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo (
	id bigint not null auto_increment, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo_permissao (
	grupo_id bigint not null, 
	permissao_id bigint not null
) engine=InnoDB default charset=utf8;

create table permissao (
	id bigint not null auto_increment, 
	descricao varchar(150), 
	nome_string varchar(60), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table produto (
	id bigint not null auto_increment,
	ativo bit not null, 
	descricao varchar(150), 
	nome varchar(60), 
	preco decimal(19,2), 
	restaurante_id bigint, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante (
	id bigint not null auto_increment, 
	data_atualizacao datetime not null, 
	data_cadastro datetime not null, 
	endereco_bairro varchar(100), 
	endereco_cep varchar(10), 
	endereco_complemento varchar(60), 
	endereco_logradouro varchar(100), 
	endereco_numero varchar(10), 
	nome varchar(150), 
	taxa_frete decimal(19,2) not null, 
	cozinha_id bigint, 
	endereco_cidade_id bigint, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante_forma_pagamento (
	restaurante_id bigint not null, 
	forma_pagamento_id bigint not null
) engine=InnoDB default charset=utf8;

create table usuario (
	id bigint not null auto_increment, 
	data_cadastro datetime(6), 
	nome varchar(10), 
	senha varchar(30), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario_grupo (
	usuario_id bigint not null, 
	grupo_id bigint not null
) engine=InnoDB default charset=utf8;

alter table grupo_permissao add constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id);
alter table produto add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint fk_restaurante_cidade foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_forma_pagamento add constraint fk_rest_forma_pgto_forma_pgto foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_forma_pagamento add constraint fk_rest_forma_pgto_restaurante foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint fk_usr_grupo_grupo foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint fk_usr_grupo_usuario foreign key (usuario_id) references usuario (id);