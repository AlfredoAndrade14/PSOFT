INSERT INTO tipo_veiculo (ID, TIPO) VALUES (1, 'CARRO'), (2, 'MOTO');

INSERT INTO tamanho_pizza (ID, TAMANHO) VALUES (1, 'Médio'), (2, 'Grande');

INSERT INTO tipo_sabor (ID, TIPO) VALUES (1, 'Salgado'), (2, 'Doce');
--
--INSERT INTO estabelecimento (ID, CODIGO_ACESSO, ENDERECO, NOME) VALUES (1, 123456, 'Rua Rua, Numero 1 Bairro, cidade, estado', 'Pizzaria');
--
--INSERT INTO cliente (ID, CODIGO_ACESSO, ENDERECO_PRINCIPAL, NOME) VALUES (2, 123456, 'Rua dos bobos numero 0', 'Diego');
--INSERT INTO cliente (ID, CODIGO_ACESSO, ENDERECO_PRINCIPAL, NOME) VALUES (3, 654321, 'Rua dos bobos numero 0', 'Lancho');
--
--INSERT INTO entregador (ID, ACEITO, CODIGO_ACESSO, COR_DO_VEICULO, NOME, PLACA_VEICULO, TIPO_DO_VEICULO_ID, ESTABELECIMENTO_ID) VALUES (4, 2, 123456, 'Preto', 'Entregador', 'AAA0A00', 2, 1);
--
--INSERT INTO SABOR_PIZZA (ID,	DISPONIVEL,	NOME,	VALOR,	TAMANHO_PIZZA_ID,	TIPO_SABOR_ID) VALUES (5, TRUE, 'Peperoni', 10.50, 2, 1);
--INSERT INTO SABOR_PIZZA (ID,	DISPONIVEL,	NOME,	VALOR,	TAMANHO_PIZZA_ID,	TIPO_SABOR_ID) VALUES (6, TRUE, 'Pedregal', 15.50, 2, 1);
--INSERT INTO SABOR_PIZZA (ID,	DISPONIVEL,	NOME,	VALOR,	TAMANHO_PIZZA_ID,	TIPO_SABOR_ID) VALUES (7, FALSE, 'Portuguesa', 5.19, 1, 1);
--INSERT INTO SABOR_PIZZA (ID,	DISPONIVEL,	NOME,	VALOR,	TAMANHO_PIZZA_ID,	TIPO_SABOR_ID) VALUES (8, TRUE, 'Brigadeiro', 12, 2, 2);
--
--INSERT INTO ESTABELECIMENTO_SABOR_PIZZA_SET (ESTABELECIMENTO_ID, SABOR_PIZZA_SET_ID ) VALUES (1, 5);
--INSERT INTO ESTABELECIMENTO_SABOR_PIZZA_SET (ESTABELECIMENTO_ID, SABOR_PIZZA_SET_ID ) VALUES (1, 6);
--INSERT INTO ESTABELECIMENTO_SABOR_PIZZA_SET (ESTABELECIMENTO_ID, SABOR_PIZZA_SET_ID ) VALUES (1, 7);
--INSERT INTO ESTABELECIMENTO_SABOR_PIZZA_SET (ESTABELECIMENTO_ID, SABOR_PIZZA_SET_ID ) VALUES (1, 8);
--
--INSERT INTO ESTABELECIMENTO_ENTREGADORES (ESTABELECIMENTO_ID, ENTREGADORES_ID) VALUES (1, 4);