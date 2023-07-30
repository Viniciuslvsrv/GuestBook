# Guestbook liferay

## Intro

A idéia é disponibilizar o [tutorial da liferay](https://help.liferay.com/hc/en-us/articles/360032887552-Introduction-to-Developing-a-Web-Application) que ensina a construir um aplicativo de ponta a ponta. O back-end usa o service builder para mapear objetos-relacionais no banco de dados, enquanto o front-end é desenvolvido a partir do MVC (model view controller). A aplicação está dividida em três principais partes:  

- [Guestbook-api](./guestbook-api/): Contém a API gerada pelo Service Builder. Tem como principal função servir de interface para acessar os métodos contidos no service.
- [Guestbook-service](./guestbook-service/): Contém os serviços usados para acessar e manipular os dados do banco de dados, oferecendo métodos e operações para criar, ler, atualizar e excluir dados no banco de dados, seguindo as regras de negócio definidas no modelo de dados.
- [Guestbook-web](./guestbook-web/): Cuida do ciclo de vida da aplicação, renderiza as JSP e chama os serviços de acordo com as ações tomadas pelo usuário. Funciona a partir do modelo MVC, ou Model-View-Controller, do Liferay. O "Model" representa a camada de dados e lógica de negócios, o "View" representa a interface do usuário e o "Controller" coordena as interações entre o Model e a View. 

## Dependências principais

- IDE Liferay 
- Liferay versão 7.2
- Java 1.8

## Rodar o projeto

1. Entrar na IDE Liferay

![ide](https://media.discordapp.net/attachments/962347020432048188/1135298804871086120/image_1.png)

2. Criar um novo workspace usando a versão 7.2 do Liferay

![passo22](https://media.discordapp.net/attachments/962347020432048188/1135301104184332348/IMG_6805.jpg?width=1224&height=658)

![passo2](https://media.discordapp.net/attachments/962347020432048188/1135298424288329783/tela1.png)

3. Clonar o repositório pra pasta modules do workspace, usando:

``` 
git clone https://github.com/Viniciuslvsrv/GuestBook
```

4. Caso não tenha feito o download do server da Liferay na hora da criação do workspace, pode dar um "initialize server bundle".

5. Para dar o init bundle, ir na IDE Liferay, clicar com o botão direito na pasta "Guestbook", ir até a lista "Liferay" e escolher a opção "initialize server bundle".

![passo5](https://media.discordapp.net/attachments/962347020432048188/1135298424024072323/tela2.png)

5. Dar um start no servidor.

![passo6](https://media.discordapp.net/attachments/962347020432048188/1135298423772418190/tela3.png)

6. Configurar o servidor e o banco de dados entrando no localhost:

``` 
http://localhost:8080/pt/web/guest/home
``` 

![passo6](https://media.discordapp.net/attachments/962347020432048188/1135298423545938081/tela4.png)

7. Iniciar deploy dos módulos selecionando todos, dando add e finalizando a seleção.

![passo7](https://media.discordapp.net/attachments/962347020432048188/1135300026655068213/IMG_6806.jpg?width=1237&height=658)
![passo71](https://media.discordapp.net/attachments/962347020432048188/1135298422879043726/IMG_6807.png?width=1224&height=658)

## Conceitos

O tutorial tenta exibir alguns conhecimentos sobre os conceitos de:

- **Permissões**: Usadas para controlar o acesso e as ações que os usuários podem realizar em diferentes recursos do portal, como páginas, portlets, documentos e pastas.
- **Busca**: Os usuários podem realizar pesquisas rápidas e avançadas para encontrar o conteúdo relevante com base em palavras-chave, filtros e critérios de busca personalizados.
- **Assets**: A estrutura de assets transforma as entidades em um formato comum que pode ser publicado em qualquer lugar do site. Artigos de conteúdo da Web, postagens de blog, artigos de wiki e documentos são algumas entidades habilitadas para ativos que vêm prontas para uso.
- **Fluxo de trabalho**: Mecanismo do Liferay que permite a criação de processos estruturados para aprovação e revisão de conteúdo antes que ele seja publicado no portal. Para evitar a postagem de alguns conteúdos, uma entrada do guestbook inicialmente deve ser marcada como rascunho e enviada por meio da estrutura de fluxo de trabalho. Ele volta para o código do aplicativo pronto para atualizar quaisquer campos relevantes no banco de dados com base no status.



