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

### Thymeleaf

Thymeleaf é um motor de templates para Java que permite a criação de páginas HTML dinâmicas de forma simples e eficiente. Ele é frequentemente utilizado em aplicações Spring, proporcionando uma maneira intuitiva de gerar conteúdo HTML e manipular dados diretamente nas páginas.

**Principais Características**

- **Natural Templating**: Os templates Thymeleaf são válidos como documentos HTML, permitindo que sejam visualizados em navegadores sem processamento.
- **Integração com Spring**: Thymeleaf se integra perfeitamente com o Spring Framework, facilitando a injeção de dependências e o acesso a beans do Spring.
- **Expressões de Template**: Utiliza uma sintaxe simples e expressiva para manipular dados, permitindo a criação de lógicas condicionais e loops diretamente nas páginas.


## Estrutura do Projeto

/RadioBrowserAPI
```
│
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 com
│   │   │       └── 📁 example
│   │   │           └── 📁 RadioBrowserAPI
│   │   │               ├── 📁 application
│   │   │               │   └── ☕ RadioBrowserApiApplication.java     # Classe main, sobe a aplicação Spring Boot
│   │   │               ├── 📁 config
│   │   │               │   └── ⚙️ ApiConfig.java                      # Monta as URLs da Radio Browser API a partir do properties
│   │   │               ├── 📁 controller
│   │   │               │   └── 🌐 RadioBrowserApiController.java      # Endpoints: home (filtros) e toggle de favoritos
│   │   │               ├── 📁 model
│   │   │               │   └── 📻 RadioStation.java                   # Representa uma estação de rádio
│   │   │               └── 📁 service
│   │   │                   ├── 🔎 RadioBrowserApiService.java         # Busca/ordena estações, estados na API
│   │   │                   └── ⭐ FavoriteService.java                # Guarda os favoritos em memória (sem persistência)
│   │   ├── 📁 resources
│   │   │   ├── 🔧 application.properties                              # Configurações da aplicação (ex: URL base da API)
│   │   │   ├── 📁 static
│   │   │   │   └── 📁 css
│   │   │   │       └── 🎨 style.css                                   # Estilos da interface
│   │   │   │   └── 📁 images
│   │   │   │		└── 🖼️ aradio.webp                                 # Favicon padrão quando a estação não tem um
|	|	|	|		└── 🖼️ radio-browser-image.avif					   # Imagem principal do site
│   │   │   └── 📁 templates
│   │   │       └── 🖥️ home.html                                       # Página Home (Thymeleaf): lista, filtros, player e favoritos
│   └── 📁 test
│       └── 📁 java
│           └── 📁 com
│               └── 📁 example
│                   └── 📁 RadioBrowserAPI
│                       └── ✅ RadioBrowserApiApplicationTests.java     # Testes da aplicação
│
├── 📦 pom.xml                                                          # Dependências e build do Maven
├── 📄 README.md                                                        # Este arquivo
```

## Endpoints

### `GET /`

Lista as estações de rádio de acordo com a capital selecionada, já marcando quais são favoritas e ordenando-as (favoritas primeiro, depois por votos).

```java
@GetMapping("/")
	public String listRadioStations(
        @RequestParam(name = "state", defaultValue = "Minas Gerais") String state, 
        @RequestParam(name = "city", defaultValue = "Belo Horizonte") String city, 
        Model model) {
        
        List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(state, city); 

        radioStations.forEach(station -> station.setFavorite(favoriteService.isFavorite(station.getStationuuid())));

        radioStations.sort(Comparator.comparing(RadioStation::isFavorite).reversed());

		model.addAttribute("stations", radioStations);
        model.addAttribute("city", city);

        // Retorna o nome da view (home.html) que será renderizada.
        return "home";
}
```

Acesse a página inicial em: [http://localhost:8080/](http://localhost:8080/)

### `POST /favorites/toggle`

Adiciona ou remove uma estação da lista de favoritos (em memória) e redireciona de volta para a Home, preservando o filtro da capital atualmente selecionado.

```java
@PostMapping("/favorites/toggle")
	public String toggleFavorite(
        @RequestParam String stationuuid,
        @RequestParam(defaultValue = "Belo Horizonte") String city,
        RedirectAttributes redirectAttributes) {

        // Alterna o status de favorito da estação usando o serviço FavoriteService.    
        favoriteService.toggle(stationuuid);

        // Redireciona de volta para a página inicial, mantendo o filtro de cidade.
        redirectAttributes.addAttribute("city", city);
        return "redirect:/home";
	}
```

## Configuração

O arquivo `application.properties` contém as seguintes configurações:

```properties
spring.application.name=RadioBrowserAPI
radio.api.base.url=https://de1.api.radio-browser.info/json/stations/search
```

A partir da `radio.api.base.url`, a classe `ApiConfig` monta a URL base e a URL consumida pela aplicação:

```java
@Configuration
public class ApiConfig {

    @Value("${radio.api.base.url}")
    private String baseUrl;

    public String getSearchUrl() {
        return baseUrl;
    }

}
```

## Licença

Este projeto está licenciado sob a MIT License.
