create table pedido
(
    id                    bigint         not null auto_increment,
    sub_total             decimal(10, 2) not null,
    taxa_frete            decimal(10, 2) not null,
    valor_total           decimal(10, 2) not null,
    data_criacao          datetime       not null,
    data_confirmacao      datetime,
    data_cancelamento     datetime,
    data_entrega          datetime,
    status                varchar(10)    not null,

    endereco_cep          varchar(9)     not null,
    endereco_logradouro   varchar(100)   not null,
    endereco_numero       varchar(20)    not null,
    endereco_complemento  varchar(60),
    endereco_bairro       varchar(60)    not null,

    endereco_cidade_id    bigint         not null,
    restaurante_id        bigint         not null,
    forma_de_pagamento_id bigint         not null,
    usuario_id            bigint         not null,

    constraint fk_pedido_restaurante foreign key (restaurante_id)        references restaurante (id),
    constraint fk_pedido_cidade      foreign key (endereco_cidade_id)    references cidade (id),
    constraint fk_pedido_forma_pgto  foreign key (forma_de_pagamento_id) references forma_de_pagamento (id),
    constraint fk_pedido_usuario     foreign key (usuario_id)            references usuario (id),


    primary key (id)
) engine = InnoDB;

create table item_pedido
(
    id             bigint         not null auto_increment,
    quantidade     smallint       not null,
    preco_unitario decimal(10, 2) not null,
    preco_total    decimal(10, 2) not null,
    observacao     varchar(255),

    pedido_id      bigint         not null,
    produto_id     bigint         not null,

    primary key (id),

    unique key uk_item_pedido_produto (pedido_id, produto_id),

    constraint fk_item_pedido_pedido  foreign key (pedido_id)  references pedido (id),
    constraint fk_item_pedido_produto foreign key (produto_id) references produto (id)
) engine = InnoDB;