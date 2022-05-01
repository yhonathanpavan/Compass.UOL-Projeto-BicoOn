# BicoOn

<div align="center">
  <a href="https://www.java.com/pt-BR/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  </a>
  <a href="https://spring.io/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring"/>
  </a>
  <a href="https://git-scm.com/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Git-E34F26?style=for-the-badge&logo=git&logoColor=white" alt="Git"/>
  </a>
  <a href="https://www.microsoft.com/pt-br/windows/?r=1" target="_blank" rel="noreferrer" rel="noopener">
  <img src="https://img.shields.io/badge/Windows-017AD7?style=for-the-badge&logo=windows&logoColor=white" alt="Windows"/>
</div></br>

<div align="center">
  <img src="BicoOn.jpg" alt="BicoOn" height="350px">
</div></br>

> O BicoOn é um projeto desenvolvido para conectar prestadores de serviços informais e clientes, ele permite que pessoas acessem o contato e os serviços de diferentes profissionais por cidade.

## ⚙️ Funcionalidades

- [x] Autenticação usuários Clientes e Prestadores
- [x] Clientes podem se cadastrar enviando:
  - Nome
  - Email
  - Senha
  - Cidade
  - Sexo
  - [x] Visualizar o contato de Prestadores por cidade e categoria:
  - [x] Avaliar o serviço de Prestadores com:
  - Nota
  - Comentário
- [x] Prestadores podem se cadastrar enviando:
  - Nome
  - Telefone
  - Cidade
  - Email
  - Senha
  - Sexo
  - [x] Cadastrar Categorias de Serviço com:
  - Nome
  - [x] Cadastrar Serviços com:
  - Categoria
  - Descrição

## 📈 Ajustes e melhorias

O projeto ainda está em desenvolvimento e as próximas atualizações serão voltadas nas seguintes tarefas:

- [x] Monitoramento com Actuator
- [x] Documentação com Swagger
- [ ] Deploy no Heroku
- [ ] Interface Gráfica
- [ ] Agendamento de serviços do Prestador
- [ ] Chat interno para comunicação entre Prestador e Cliente

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:

* Ter instalado a  linguagem `Java JDK 17 LTS`.
* Ter instalado a IDE `IntelliJIDEA` ou `Eclipse Spring`. 
* Ter uma máquina `Windows 10` ou `11`.

## 🚀 Instalação

Passo-a-passo que informa o que você deve executar para ter um ambiente de desenvolvimento em execução.

```
# Clone este repositório
$ git clone git@github.com:yhonathanpavan/Compass.UOL-Projeto-BicoOn.git

# Acesse a pasta do projeto no seu terminal
$ code .

# Selecione a IDE desejada

# Execute a aplicação em modo de desenvolvimento

# A aplicação será aberta na porta:8080 - acesse http://localhost:8080
```

## 📃 Swagger

Para acessar a documentação BicoOn, baixe o arquivo e siga as etapas:
1. [BicoOnApi](https://github.com/yhonathanpavan/Compass.UOL-Projeto-BicoOn/blob/master/BicoOnApi.yml)
2. Com o [Swagger Editor](https://editor.swagger.io/?_ga=2.185135209.135771497.1650906792-744667863.1647286784)
3. Em seguida importe, clique em File, Import File e selecione o arquivo BicoOnApi. 
4. Por fim, execute o aplicação BicoOnApplication, após isso a documentação estará pronta para uso. 

## 🧑‍🚀 Postman

Para acessar a collection BicoOn, baixe o arquivo e siga as etapas:
1. [BicoOnCollection](https://github.com/yhonathanpavan/Compass.UOL-Projeto-BicoOn/blob/master/BicoOnCollection.json)
2. Abra a plataforma [Postman](https://www.postman.com/product/what-is-postman/)
3. Em seguida importe, clique em Import e selecione o arquivo BicoOnCollection.
4. Por fim, execute o aplicação BicoOnApplication, após isso a plataforma estará pronta para uso. 

## 🔐 Segurança
O projeto BicoOn segue padrões de segurança API REST, sendo os seguintes endpoints liberados para acesso de qualquer usuario:
```
  POST - /bicoon/clientes      (O cadastro de clientes é aberto para que seja possível criar uma conta do tipo Cliente) 
  POST - /bicoon/prestadores   (O cadastro de prestadores é aberto para que seja possível criar uma conta do tipo Prestador) 
  POST - /bicoon/auth          (Autetica de acordo com as credenciais se o usuário tem acesso)
```
Para acessar os endpoints restantes é necessário estar autenticado. 

## 📌 EndPoints
Para acessar os endpoints da Entidade Cliente:
```
  GET - /bicoon/clientes         (Lista todos os clientes cadastrados) 
  GET - /bicoon/clientes/{id}    (Detalha o cadastro de um cliente existente pelo ID)
  POST - /bicoon/clientes        (Cadastra um novo cliente) 
  PUT - /bicoon/clientes/{id}    (Atualiza o cadastro de um cliente existente pelo ID) 
  DELETE - /bicoon/clientes/{id} (Remove o cadastro de um cliente existente pelo ID) 
```
Para acessar os endpoints da Entidade Prestador:
```
  GET - /bicoon/prestadores         (Lista todos os prestadores cadastrados) 
  GET - /bicoon/prestadores         (Detalha o cadastro de um prestador existente pelo ID)
  POST - /bicoon/prestadores        (Cadastra um novo prestador) 
  PUT - /bicoon/prestadores/{id}    (Atualiza o cadastro de um prestador existente pelo ID) 
  DELETE - /bicoon/prestadores/{id} (Remove o cadastro de um prestador existente pelo ID) 
```
Para acessar os endpoints da Entidade Avaliação:
```
  POST - /bicoon/avaliacoes/clientes/{clienteId}/prestadores/{prestadorId} (Cadastra uma nova avaliação para um Prestador) 
  PUT - /bicoon/avaliacoes/{id}                                            (Atualiza uma avaliação existente pelo ID) 
  DELETE - /bicoon/avaliacoes/{id}                                         (Remove uma avaliação existente pelo ID) 
```
Para acessar os endpoints da Entidade Serviço:
```
  GET - /bicoon/servicos         (Lista todos os serviços) 
  PUT - /bicoon/servicos/{id}    (Atualiza um serviço existente pelo ID) 
  DELETE - /bicoon/servicos/{id} (Remove um serviço existente pelo ID) 
```

<b> ⚠️ Endpoints de Acesso do Administrador ⚠️</b> </br>
Para acessar os endpoints da Entidade Categoria:
```
  GET - /bicoon/categorias         (Lista todos as categorias) 
  PUT - /bicoon/categorias/{id}    (Atualiza uma categoria existente pelo ID) 
  DELETE - /bicoon/categorias/{id} (Remove uma categoria existente pelo ID) 
```

Para acessar os endpoints do Monitoramento:
```
  GET - /actuator         (Exibe os Endpoints disponíveis) 
```

## 🕷️ Testes
Foram usadas as seguintes tecnologias e ferramentas para Testes da API: 
* [JUnit 5](https://junit.org/junit5/docs/current/user-guide/) - Framework de Testes
* [Mockito](https://site.mockito.org/) - Estrutura de Testes

## 🚧🛠️ Tecnologias e Ferramentas
  
Foram usadas as seguintes tecnologias e ferramentas para a construção da API: 
* [Java](https://www.java.com/pt-BR/) - Linguagem de Programação
* [SpringBoot](https://spring.io/) - FrameWork Java
* [Git](https://git-scm.com/) - Ferramenta de Versionamento de Código
* [H2](https://www.h2database.com/html/main.html) - Sistema de gerenciamento de banco de dados relacional
* [IntelliJIDEA](https://www.jetbrains.com/pt-br/idea/) - IDE (Ambiente de desenvolvimento integrado)
* [Postman](https://www.postman.com/) - Plataforma da API
* [Swagger](https://swagger.io/tools/swagger-editor/) - Editar de design da API
* [Actuator](https://www.zup.com.br/blog/spring-actuator) - Dependência de Monitoramento
* [Windows](https://www.microsoft.com/pt-br/windows/?r=1) - Sistema Operacional

## ☎️ Suporte BicoOn
  
Caso tenha dúvidas, reclamações ou sugestões, contate os desenvolvedores. 

## 👩‍💻👨‍💻 Desenvolvedores

Agradecemos às seguintes pessoas que contribuíram para este projeto:

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/87677794?v=4" width="100px;" alt="Foto do Alan da Silva"/><br>
        <sub>
          <div align="center">
            <b>Alan Silva</b></br></br>
            <a href="https://www.linkedin.com/in/dev-alanfernando/" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="Linkedin"/>
            </a></br>
            <a href="mailto:alanfernando2809@gmail.com" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail"/></br>
            </a>
          </div>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/61952169?v=4" width="100px;" alt="Foto da Elaine Cristina"/><br>
        <sub>
          <div align="center">
            <b>Elaine Cristina</b></br></br>
            <a href="https://www.linkedin.com/in/elaine-cristina-52504120a/" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="Linkedin"/>
            </a></br>
            <a href="mailto:elaine.paula2178@gmail.com" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail"/></br>
            </a>
          </div>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/76978080?v=4" width="100px;" alt="Foto do Mateus Cardoso"/><br>
        <sub>
          <div align="center">
            <b>Mateus Cardoso</b></br></br>
            <a href="https://www.linkedin.com/in/mateus-cardoso-de-moraes/" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="Linkedin"/>
            </a></br>
            <a href="mailto:mateus.moraes0507@gmail.com" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail"/></br>
            </a>
          </div>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/51124985?v=4" width="100px;" alt="Foto do Yhonathan Pavan"/><br>
        <sub>
          <div align="center">
            <b>Yhonatan Pavan</b></br></br>
            <a href="https://www.linkedin.com/in/yhonathan-pavan/" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="Linkedin"/>
            </a></br>
            <a href="mailto:yhonathannpavan@gmail.com" target="_blank" rel="noreferrer" rel="noopener">
              <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail"/></br>
            </a>
          </div>
        </sub>
      </a>
    </td>
  </tr>
</table>

## ❤️ Apoio

Agradecemos a empresa por todo apoio prestado neste projeto.

<sub>
  <div>
    <a href="https://compass.uol/pt/" target="_blank" rel="noreferrer" rel="noopener">
      <img src="compass.uol.png" alt="compass.uol" width="300px;"/><br>
    </a></br>
  </div>
</sub>
</br>

[⬆ Voltar ao topo](#BicoOn)<br>

