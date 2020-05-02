# Arquitetura Camel

### Configurando o ambiente

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

