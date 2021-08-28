create table restaurante_usuario_responsavel
(
    restaurante_id bigint not null,
    usuario_id     bigint not null,

    primary key (restaurante_id, usuario_id),

    constraint fk_rest_user_rest foreign key (restaurante_id) references forma_de_pagamento (id),
    constraint fk_rest_user_user foreign key (usuario_id) references usuario (id)

) engine = InnoDB
  default charset = utf8;