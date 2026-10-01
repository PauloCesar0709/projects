package com.example.RadioBrowserAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Ponto de entrada da aplicação e configuração inicial dos componentes Spring.
// O pacote com.example é escaneado para localizar controllers, serviços e configurações.
@SpringBootApplication(scanBasePackages = {"com.example"})
public class RadioBrowserApiApplication {

	public static void main(String[] args) {
		// Cria o contexto da aplicação e inicia o servidor web embutido.
		SpringApplication.run(RadioBrowserApiApplication.class, args);
	}

}
