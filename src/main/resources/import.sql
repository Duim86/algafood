INSERT INTO cozinha (id, nome) values (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) values (2, 'Indiana');
INSERT INTO cozinha (id, nome) values (3, 'Japonesa');

INSERT INTO estado (id, nome) values (1, 'Paraná');
INSERT INTO estado (id, nome) values (2, 'Bahia');
INSERT INTO estado (id, nome) values (3, 'Minas Gerais');

INSERT INTO cidade (id, nome, estado_id) values (1, 'Foz do Iguaçu', 1);
INSERT INTO cidade (id, nome, estado_id) values (2, 'Salvador', 2);
INSERT INTO cidade (id, nome, estado_id) values (3, 'Uberaba', 3);
INSERT INTO cidade (id, nome, estado_id) values (4, 'Curitiba', 1);
INSERT INTO cidade (id, nome, estado_id) values (5, 'Belo Horizonte', 3);



INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, bairro, cep, complemento, logradouro, numero, endereco_cidade_id) values ('Thai Food', 0, 1, utc_timestamp, utc_timestamp, 'Água Verde', '80320-20', 'loja 1', 'Avenida República Argentina', '2000', 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Indian Food', 10.5, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Japan Sushi', 12.35, 3, utc_timestamp, utc_timestamp);

INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES (true, 'Água 500ml Ouro Fino', 'Água', 2.75, 1);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES (true, 'Cerveja 355ml Heineken', 'Cerveja', 5.25, 1);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES (true, 'Suco de Laranja 1L', 'Suco', 10, 1);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES (true, 'CheeseBurguer', 'Lanche', 10, 2);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES (true, 'Batata Frita', 'Acompanhamento', 6, 2);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES (true, 'Pizza Calabresa', 'Pizza', 25, 3);


INSERT INTO forma_de_pagamento(id, descricao) VALUES (1, 'Cartão de crédito');
INSERT INTO forma_de_pagamento(id, descricao) VALUES (2, 'Cartão de débito');
INSERT INTO forma_de_pagamento(id, descricao) VALUES (3, 'Dinheiro');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1,1), (1,2), (2,2), (3,3), (3,1), (2,1);





