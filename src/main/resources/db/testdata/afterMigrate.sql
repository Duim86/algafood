set foreign_key_checks = 0;

delete
from cidade;
delete
from cozinha;
delete
from estado;
delete
from forma_de_pagamento;
delete
from grupo;
delete
from grupo_permissao;
delete
from permissao;
delete
from produto;
delete
from restaurante;
delete
from restaurante_forma_pagamento;
delete
from usuario;
delete
from usuario_grupo;
delete
from restaurante_usuario_responsavel;
delete
from pedido;
delete
from item_pedido;
delete
from foto_produto;


set foreign_key_checks = 1;

alter table cidade
    auto_increment = 1;
alter table cozinha
    auto_increment = 1;
alter table estado
    auto_increment = 1;
alter table forma_de_pagamento
    auto_increment = 1;
alter table grupo
    auto_increment = 1;
alter table grupo_permissao
    auto_increment = 1;
alter table permissao
    auto_increment = 1;
alter table produto
    auto_increment = 1;
alter table restaurante
    auto_increment = 1;
alter table restaurante_forma_pagamento
    auto_increment = 1;
alter table usuario
    auto_increment = 1;
alter table usuario_grupo
    auto_increment = 1;
alter table restaurante_usuario_responsavel
    auto_increment = 1;
alter table pedido
    auto_increment = 1;
alter table item_pedido
    auto_increment = 1;

INSERT INTO cozinha (id, nome)
VALUES (1, 'Tailandesa'),
       (2, 'Indiana'),
       (3, 'Japonesa'),
       (4, 'Argentina'),
       (5, 'Brasileira');


INSERT INTO estado (id, nome)
VALUES (1, 'Paraná'),
       (2, 'Bahia'),
       (3, 'Minas Gerais');

INSERT INTO cidade (id, nome, estado_id)
VALUES (1, 'Foz do Iguaçu', 1),
       (2, 'Salvador', 2),
       (3, 'Uberaba', 3),
       (4, 'Curitiba', 1),
       (5, 'Belo Horizonte', 3);

INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_bairro, endereco_cep,
                         endereco_complemento,
                         endereco_logradouro, endereco_numero, endereco_cidade_id, ativo, aberto)
VALUES ('Thai Food', 0, 1, utc_timestamp, utc_timestamp, 'Água Verde', '80320-20', 'loja 1',
        'Avenida República Argentina', '2000', 1, true, true),

       ('Indian Food', 10.5, 2, utc_timestamp, utc_timestamp, 'Água Verde', '80320-20', 'loja 1',
        'Avenida República Argentina', '2000', 2, true, true),

       ('Japan Sushi', 12.35, 3, utc_timestamp, utc_timestamp, 'Água Verde', '80320-20', 'loja 1',
        'Avenida República Argentina', '2000', 3, true, true);

INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id)
VALUES (true, 'Água 500ml Ouro Fino', 'Água', 2.75, 1),
       (true, 'Cerveja 355ml Heineken', 'Cerveja', 5.25, 1),
       (true, 'Suco de Laranja 1L', 'Suco', 10, 1),
       (false, 'Leite Desnatado 1L', 'Leite', 4, 1),
       (true, 'CheeseBurguer', 'Lanche', 10, 2),
       (true, 'Batata Frita', 'Acompanhamento', 6, 2),
       (true, 'Pizza Calabresa', 'Pizza', 25, 3);


INSERT INTO forma_de_pagamento(id, descricao)
VALUES (1, 'Cartão de crédito'),
       (2, 'Cartão de débito'),
       (3, 'Dinheiro');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (3, 1),
       (2, 1);


INSERT INTO grupo (id, nome)
VALUES (1, 'Gerente'),
       (2, 'Secretária'),
       (3, 'Cadastrador');

INSERT INTO permissao (id, nome, descricao)
VALUES (1, 'CONSULTAR_COZINHA', 'Permite consultar cozinha'),
       (2, 'CADASTRAR_COZINHA', 'Permite cadastrar cozinha'),
       (3, 'CADASTRAR_RESTAURANTE', 'Permite cadastrar restaurante');

INSERT INTO grupo_permissao (grupo_id, permissao_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (3, 1),
       (2, 1);


INSERT INTO usuario(id, data_cadastro, email, nome, senha)
VALUES (1, utc_timestamp, 'zanlorenzi@gmail.com', 'Alexandre Zanlorenzi', '123'),
       (2, utc_timestamp, 'azanlorenzi@yahoo.com', 'Alexandre Zanlorenzi', '123'),
       (3, utc_timestamp, 'android.duim@gmail.com', 'Alexandre Zanlorenzi', '123'),
       (4, utc_timestamp, 'Manoel Lima', 'manoel.loja@gmail.com', '123');

INSERT INTO usuario_grupo (usuario_id, grupo_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (3, 1),
       (2, 1);

INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id)
VALUES (1, 4),
       (3, 4);

INSERT INTO pedido (id, codigo, restaurante_id, cliente_id, forma_de_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (1, '5b084069-ff49-4549-8a53-748ee33979ff', 3, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CONFIRMADO', '2021-06-17 17:52:51', 79, 0, 79),
       (2, 'b172992b-37a7-4a07-8e06-cf230bcb186a', 2, 2, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'ENTREGUE', '2021-06-18 17:52:51', 125.5, 5, 130.50),
       (3, '3b084069-ff49-4549-8a53-748ee33979ff', 3, 2, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CONFIRMADO', '2021-06-21 17:52:51', 45, 12, 57),
       (4, 'l172992b-37a7-4a07-8e06-cf230bcb186a', 1, 3, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'ENTREGUE', '2021-06-20 17:52:51', 298.90, 10, 308.90),
       (5, 'l372992b-37a7-4a07-8e06-cf230bcb186a', 3, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'CONFIRMADO', '2021-06-19 17:52:51', 95.25, 7, 102.25),
       (6, 'l372992b-3ea7-4a07-8e06-cf230bcb186a', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'ENTREGUE', '2021-06-19 17:52:51', 95.25, 7, 102.25),
       (7, 'l372992b-37ac-4a07-8e06-cf230bcb186a', 2, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'ENTREGUE', '2021-06-19 01:52:51', 95.25, 7, 102.25);


INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (3, 2, 6, 1, 79, 79, 'Ao ponto'),
       (2, 1, 2, 2, 110, 220, 'Menos picante, por favor'),
       (1, 1, 1, 1, 78.9, 78.9, null);





