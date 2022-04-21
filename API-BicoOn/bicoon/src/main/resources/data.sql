INSERT INTO PRESTADOR (NOME, EMAIL, SENHA, CIDADE, SEXO, TELEFONE) VALUES
('Jacinto', 'jacintotristezas@email.com', '1234', 'Mogi', 'MASCULINO', '(19)99988-7766'),
('Romildo', 'romildofelix@email.com', '1234', 'Araras', 'MASCULINO', '(19)98889-8877'),
('Joana', 'joanamarry@email.com', '1234', 'Pindamonhangaba', 'FEMININO', '(19)91234-4567');

INSERT INTO CLIENTE(CIDADE, EMAIL, NOME, SENHA, SEXO) VALUES
('Engenheiro Coelho', 'mateus@email', 'Mateus Cardoso', '123', 'MASCULINO'),
('Mogi Mirim', 'alan@email', 'Alan Fernando', '1234', 'MASCULINO'),
('Leme', 'yho@email', 'Yhonathan Mateus', '4321', 'MASCULINO'),
('São Paulo', 'fer@email', 'Fernanda Bueno', '1237', 'FEMININO'),
('Araras', 'paula@email', 'Paula Cleide', '123456', 'FEMININO'),
('Limeira', 'livia@email', 'Livia Silva', '1235', 'FEMININO'),
('Mogi Mirim', 'elaine@email', 'Elaine Cristina', '1235', 'FEMININO');

INSERT INTO AVALIACAO (CLIENTE_ID, COMENTARIO, DATA, NOTA, PRESTADOR_ID) VALUES
(1, 'Serviço excelente, ótima qualidade!', '2022-7-3', 5, 1),
(2, 'Serviço meia boca, corroeu o cano da pia!', '2022-7-3', 2, 1);

INSERT INTO CATEGORIA (NOME) VALUES
('Encanador'),
('Babá'),
('Serviços domésticos'),
('Alvenaria');

INSERT INTO SERVICO (DESCRICAO, CATEGORIA_ID, PRESTADOR_ID) VALUES
('Troco e limpo a encanação', 1, 1),
('Cuido de qualquer criança com qualquer idade', 2, 3),
('Lavo, cozinho, limpo tudo', 3, 3);