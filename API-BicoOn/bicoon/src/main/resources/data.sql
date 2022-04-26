INSERT INTO PRESTADOR (NOME, EMAIL, SENHA, CIDADE, SEXO, TELEFONE, DISPONIVEL) VALUES
('José', 'joseromano@email.com', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'Mogi', 'MASCULINO', '(19)99988-7766', TRUE),
('Romildo', 'romildofelix@email.com', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'Araras', 'MASCULINO', '(19)98889-8877', TRUE),
('Joana', 'joanamarry@email.com', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'Pindamonhangaba', 'FEMININO', '(19)91234-4567', TRUE);

INSERT INTO CLIENTE(CIDADE, EMAIL, NOME, SENHA, SEXO) VALUES
('Engenheiro Coelho', 'mateus@email.com', 'Mateus Cardoso', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'MASCULINO'),
('Mogi Mirim', 'alan@email.com', 'Alan Fernando', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'MASCULINO'),
('Leme', 'yho@email.com', 'Yhonathan Mateus', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'MASCULINO'),
('São Paulo', 'fer@email.com', 'Fernanda Bueno', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'FEMININO'),
('Araras', 'paula@email.com', 'Paula Cleide', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'FEMININO'),
('Limeira', 'livia@email.com', 'Livia Silva', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'FEMININO'),
('Mogi Mirim', 'elaine@email.com', 'Elaine Cristina', '$2a$10$4/64YPzg.ojbLDiPSkiHsuqjQS4bsu1uRiktDJlQw9.3APVZFzvFa', 'FEMININO');

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