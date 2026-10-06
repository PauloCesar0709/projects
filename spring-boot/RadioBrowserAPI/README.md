# RadioBrowserAPI

## Descrição do Projeto

O Radio Browser API é um projeto que consome a [Radio Browser API](https://api.radio-browser.info/) para exibir estações de rádio, permitindo aos usuários a filtragem
por **capitais**, marcar estações como favoritas e ouvi-las diretamente pelo navegador. A aplicação permite que os usuários visualizem informações sobre as estações 
de rádio, incluindo detalhes como nome, URL, tags, votos, cliques, bitrate e codec. A interface é construída utilizando o Thymeleaf, que fornece uma maneira simples e 
eficiente de gerar páginas HTML dinâmicas.

No projeto RadioBrowserAPI, utilizamos HTML5 para criar uma interface web interativa e moderna. Uma das funcionalidades principais da aplicação é a reprodução de 
estações de rádio, que é possibilitada pelo uso da tag `<audio>` do HTML5.

A tag <audio> permite incorporar áudio diretamente nas páginas da web, oferecendo aos usuários a capacidade de ouvir as rádios de forma simples e eficiente. Com 
essa tag, é possível incluir controles de reprodução, como play e pause, proporcionando uma experiência de usuário intuitiva e acessível.

Graças à integração do Thymeleaf, a aplicação é capaz de gerar dinamicamente elementos de áudio para cada uma das estações de rádio disponíveis, permitindo que 
os usuários selecionem e ouçam suas rádios favoritas com facilidade. A combinação do HTML5 e do Thymeleaf garante que a interface não apenas seja funcional, mas 
também responsiva e atraente.

## Funcionalidades

- **Filtro por capitais**: o select de capitais é carregado dinamicamente a partir da API (`/states`), permitindo pesquisar estações de qualquer capital do Brasil.
Por padrão, a busca é feita para `state=Minas Gerais`.
- **Favoritos**: cada estação pode ser marcada/desmarcada como favorita com um clique. As favoritas sobem para o topo da listagem (mantendo a ordenação por votos
dentro de cada grupo). Os favoritos são guardados **em memória**, enquanto a aplicação estiver rodando — ou seja, são zerados a cada reinício e
não exigem banco de dados ou arquivo de persistência.
- **Ordenação**: as estações são ordenadas por número de votos (decrescente); países e estados são ordenados alfabeticamente conforme as regras do português do Brasil
- **Player embutido**: reprodução das rádios diretamente na página via tag `<audio>` do HTML5.

## Capturas de tela

- **Home**: Exibe as rádios recuperadas de acordo com o estado selecionado.

 ![Home](https://github.com/PauloCesar0709/projects/blob/master/spring-boot/RadioBrowserAPI/images/Captura%20de%20Tela%20-%20RadioBrowserAPI1.png) |
|:-------------------------------:|
|         Home          |

| ![Home](https://github.com/PauloCesar0709/projects/blob/master/spring-boot/RadioBrowserAPI/images/Captura%20de%20Tela%20-%20RadioBrowserAPI2.png) |
|:---------------------------------:|
|         Home          |

## Dependências

O projeto utiliza a seguinte dependência em seu `pom.xml`:

```
    <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>
```
