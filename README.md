# Arquitetura Camel

### __Objetivo__

Esta POC tem como objetivo demonstrar uma abordagem de uso do framework Apache Camel de uma forma mais simples, focando seu uso exclusivamente à integrações de sistemas e cuidar de algumas pequenas lógicas, desde que as mesmas envolvam interação entre os diversos sistemas integrados pelas rotas.

### __Configurando o ambiente__

Antes de abrir sua IDE de preferência para rodar os serviços desta POC, é preciso criar as imagens do Docker dos bancos de dados com as configurações das tabelas e de alguns conteúdos mockados, e com isso é possível rodar os containeres.

Para facilitar este processo de criação de imagens, containeres, start e stop dos mesmos e acesso ao bash do mysql, foi criado um conjunto de scripts do Powershell, para automatizar estas tarefas.

Iniciando o setup dos containeres, devemos rodar o script abaixo, para criar as imagens baseadas no mysql:

    .\create-images.ps1

Depois já é possível criar todos os containeres, com o script abaixo:

    .\create-containers.ps1

Para coordenar o início ou fim de todos os containeres, pode se usar o script abaixo, com o comando do docker desejado:

    .\containers.ps1 <start ou stop>

Veja que outros comandos do docker como rm, rmi, etc podem ser usados por esse script.

Para poder acessar o banco de dados como root user, basta usar o script abaixo, fornecendo o nome do sistema:

    .\open-mysql-root.ps1 <sistema>

Onde o sistema pode ser: atendente, prestador, cliente e ordem-servico.

Depois de configurar e subir todos os containeres, basta importar os projetos abaixo para sua IDE e rodar todos:

- atendente-server
- cliente-server
- prestador-server
- atendimento-system

Todas as configurações de portas e acessos aos db's já estão definidas. Não nenhuma necessidade de se configurar isso para rodar e testar a POC.

### __Descrição do sistema__

Basicamente há 3 sistemas, o atendente-server, cliente-server e o prestador-server, em que cada um possue a responsabilidade de gerenciar os dados de seu escopo (atendente-server cuida dos atendentes, suas lógicas próprias e nada mais, por exemplo).

No atendimento-system é onde ocorre o controle do registro de prestadores, clientes e ordens de serviço, por parte dos atendentes registrados.

Essas operações do atendimento-system respeitam o seguinte conjunto de regras de negócio:

1. Somente supervisores podem apagar clientes e prestadores, desde que os mesmos sejam da mesma base do supervisor.
2. Supervisores e analistas, são os únicos atendentes que podem registrar clientes e prestadores, e os mesmos são registrados na mesma base do atendente que está fazendo o registro.
3. Todos os atendentes podem ver os clientes e prestadores registrados em sua base.
4. Todos os atendentes podem criar ordens de serviço (OS) para clientes de sua base e o prestador designado para o atendimento, será da mesma base do cliente.
5. Somente supervisores e analistas podem fechar as OS's.

### __Vantagens__

Com essa abordagem, têm-se alguns ganhos no trabalho de integrar os diversos sistemas:

- Rotas mais simples e fácis de prestar suporte
- Com exchanges diferentes pa ra cada chamada de rota, não temos mais o problema de ficar com uma imensão de dados armazenados nos headers e nas properties
- Tira-se a responsabilidade da lógica de negócio da rota
- Melhor aproveitamento dos componentes do Camel

### __Observações__

Dentro da pasta PostmanScripts, há scripts que podem ser importados no postman com testes de todos os endpoints de todos o sistemas desta POC. A exportação foi feita para versões do postman, a partir do v2.1.

Os tratamentos de erros e algumas verificações de variáveis nulas foram implementados de uma forma muito simplória, visto que o foco desta POC era apresentar uma forma de utilizar as rotas do Camel dentro do código Java, para facilitar a vida do desenvolvedor, evitando a criação de rotas que fizessem tudo e, por isso, ficassem muito complexas e de difícil manutenção.

Todas as configurações de portas dos sistemas, url's dos bancos de dados, etc. Estão nos arquivos application.properties de cada aplicação.

Para alterar as configurações dos bancos de dados, em cada projeto há uma pasta db com o seguinte arquivo:

    CreateDatabase.sql

Onde pode-se configurar as tabelas e os dados mockados do db. E dentro do projeto, há um Dockerfile onde pode-se configurar a imagem base do Mysql e algumas variáveis de ambiente.

## TODO

1. ~~Montar mensagens de erros quando houver exceções~~
2. Criar camada de service, para englobar chamadas de rotas e algumas lógicas de negócio
3. Realizar testes unitários
4. ~~Tentar implementar lógica pra gerar pdf da ordem de serviço (usar componente do apache camel)~~
